import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MasterServer extends UnicastRemoteObject implements MasterServerInterface {

	private SubServerInterface service1;
	private SubServerInterface service2;
	private SubServerInterface service3;
	
    protected MasterServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    /*public void setup(String folder) throws RemoteException, MalformedURLException, NotBoundException {

        service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            try {
                service1.readData1("./CSVnoIntersect/table1.csv");
                //System.out.println(1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                service2.readData2("./CSVnoIntersect/table2.csv");
                //System.out.println(2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                service3.readData2("./CSVnoIntersect/table3.csv");
                //System.out.println(3);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }*/

    @Override
    public void lookupSubServers() throws RemoteException, MalformedURLException, NotBoundException {
    
    	service1 = (SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1");
        service2 = (SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2");
        service3 = (SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3");

//        service1.readData1("./" + folder + "/table1.csv");
//        service2.readData2("./" + folder + "/table2.csv");
//        service3.readData2("./" + folder + "/table3.csv");
    }

    private ArrayList<DataTuple3> join(ArrayList<DataTuple1> data1, ArrayList<DataTuple2> data2) {
        Collections.sort(data1);
        Collections.sort(data2);

        ArrayList<DataTuple3> result = new ArrayList<>();
        int a = 0;
        int b = 0;

        while (a < data1.size() && b < data2.size()) {
            if (data1.get(a).getA1() > data2.get(b).getA1()){
                b += 1;
            }
            else if (data1.get(a).getA1() < data2.get(b).getA1()) {
                a += 1;
            }
            else {

                for (int i = b; i < data2.size(); i++) {
                    if ((data1.get(a).getA1() != data2.get(i).getA1())) {break;}
                    result.add(new DataTuple3(data2.get(i).getA1(), data1.get(a).getA2(), data1.get(a).getA3(), data2.get(i).getA2(), data2.get(i).getA3()));
                }

                for (int i = a+1; i < data1.size(); i++) {
                    if ((data1.get(i).getA1() != data2.get(b).getA1())) {break;}
                    result.add(new DataTuple3(data1.get(i).getA1(), data1.get(i).getA2(), data1.get(i).getA3(), data2.get(b).getA2(), data2.get(b).getA3()));
                }
                a+= 1;
                b += 1;
            }
        }
        return result;
    }


    @Override
    public ArrayList<DataTuple3> doNaiveJoin()  throws MalformedURLException, RemoteException, NotBoundException {
        
        
        long a = System.currentTimeMillis();
        
        ArrayList<DataTuple1> dt1 = service1.getData();
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple2> dt3 = service3.getData();
        dt2.addAll(dt3);

        System.out.println("Naive Sizes: " + dt1.size() + " and " + dt2.size());

        ArrayList<DataTuple3> j = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        System.out.println("Naive time to get Data and join: " + time);
        
        return j;
    }

    @Override
    public ArrayList<DataTuple3> do12Join()  throws MalformedURLException, RemoteException, NotBoundException {

      
        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf = service2.getBF();
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getData();
        
        ArrayList<DataTuple3> q = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        //System.out.println(time);
        
        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }
    
    @Override
    public ArrayList<DataTuple3> doIntersectionJoin()  throws MalformedURLException, RemoteException, NotBoundException {

        
        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize() + service3.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service3.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf2 = service2.getBF();
        boolean[] bf3 = service3.getBF();
        boolean[] bf = new boolean[bf2.length];
        for (int i=0; i<bf2.length; i++) {
        	bf[i] = bf2[i] && bf3[i];
        }
        
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple2> dt3 = service3.getData();
        dt2.addAll(dt3);
        
        ArrayList<DataTuple3> q = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        //System.out.println(time);
        
        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }
    
    @Override
    public ArrayList<DataTuple3> doUnionJoin()  throws MalformedURLException, RemoteException, NotBoundException {


        long a = System.currentTimeMillis();
        
        BloomFilter filter = new BloomFilter(3500, service2.getDataSize() + service3.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service3.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf2 = service2.getBF();
        boolean[] bf3 = service3.getBF();
        boolean[] bf = new boolean[bf2.length];
        for (int i=0; i<bf2.length; i++) {
        	bf[i] = bf2[i] || bf3[i];
        }
        
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple2> dt3 = service3.getData();
        dt2.addAll(dt3);
        
        ArrayList<DataTuple3> q = join(dt1, dt2);
        
        long time = System.currentTimeMillis()-a;
        //System.out.println(time);
        
        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }

    @Override
    public ArrayList<DataTuple3> doBFJoin() throws MalformedURLException, RemoteException, NotBoundException {

        long a = System.currentTimeMillis();

        System.out.println(service1.getDataSize());
        System.out.println(service2.getDataSize());
        System.out.println(service3.getDataSize());

        BloomFilter filter = new BloomFilter(20000000, service1.getDataSize());
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service3.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        boolean[] bf1 = service1.getBF();
        boolean[] bf2 = service2.getBF();
        boolean[] bf3 = service3.getBF();
        boolean[] bf = new boolean[bf2.length];

        for (int i=0; i<bf1.length; i++) {
            bf[i] = bf1[i] && (bf2[i] || bf3[i]);
        }
        long time = System.currentTimeMillis()-a;
        System.out.println("Real Time to get BFs: " + time);
        long b = System.currentTimeMillis();


        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf);
        ArrayList<DataTuple2> dt2 = service2.getFilteredData(bf);
        ArrayList<DataTuple2> dt3 = service3.getFilteredData(bf);
        dt2.addAll(dt3);

        System.out.println("Real Sizes: " + dt1.size() + " and " + dt2.size());
        ArrayList<DataTuple3> q = join(dt1, dt2);

        long time2 = System.currentTimeMillis()-b;
        System.out.println("Real Time to get Data and join: " + time2);
        //System.out.println(time);

        for (DataTuple z : dt2) {
            //z.print();
        }
        return q;
    }

}