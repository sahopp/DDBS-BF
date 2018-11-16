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

        System.out.println("Process Naive Join");
        System.out.println("Receive Data from SubServers...");
        ArrayList<DataTuple1> dt1 = service1.getData();
        ArrayList<DataTuple2> dt2 = service2.getData();
        ArrayList<DataTuple2> dt3 = service3.getData();
        dt2.addAll(dt3);

        System.out.println("Perform Join...");
        ArrayList<DataTuple3> j = join(dt1, dt2);
        System.out.println("Join complete!\nReturn result\n");
        return j;
    }

    @Override
    public ArrayList<DataTuple3> doBFJoin(int m) throws MalformedURLException, RemoteException, NotBoundException {

        System.out.println("Process BloomFilter Join");
        BloomFilter filter = new BloomFilter(m, Math.max(service1.getDataSize(), service2.getDataSize() + service3.getDataSize()));
        service1.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service2.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());
        service3.setFilterConfig(filter.getM(), filter.getP(), filter.getA(), filter.getB());

        System.out.println("Wait for BloomFilters...");
        boolean[] bf1 = service1.getBF();
        boolean[] bf2 = service2.getBF();
        boolean[] bf3 = service3.getBF();
        boolean[] bf23 = new boolean[bf2.length];

        for (int i=0; i<bf1.length; i++) {
            bf23[i] = bf2[i] || bf3[i];
        }

        System.out.println("Receive filtered Data from SubServers...");
        ArrayList<DataTuple1> dt1 = service1.getFilteredData(bf23);
        ArrayList<DataTuple2> dt2 = service2.getFilteredData(bf1);
        ArrayList<DataTuple2> dt3 = service3.getFilteredData(bf1);
        dt2.addAll(dt3);

        System.out.println("Perform join...");
        ArrayList<DataTuple3> j = join(dt1, dt2);
        System.out.println("Join complete!\nReturn result\n");
        return j;
    }
}