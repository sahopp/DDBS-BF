import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SubServer extends UnicastRemoteObject implements SubServerInterface {

    protected SubServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    private ArrayList<DataTuple> data;
    private BloomFilter filter;


    public void putData(ArrayList<DataTuple> a) throws RemoteException{
        this.data = a;
    }

    public void setFilterConfig(int m, int p, int[] a, int[] b) throws RemoteException{
        this.filter = new BloomFilter(m, p, a, b);
    }

    public boolean[] getBF() throws RemoteException{
        System.out.println("Hashing Data into BloomFilter...");
        for (DataTuple z:data) {
            this.filter.add(z.getA1());
        }
        System.out.println("Hashing complete!\n");
        return this.filter.getFilter();
    }

    public int getDataSize() throws RemoteException{
        return this.data.size();
    }

    public ArrayList<DataTuple> getFilteredData(boolean[] bf){
        System.out.println("Filtering Data...");
        ArrayList<DataTuple> result =  new ArrayList<>();

        this.filter.setFilter(bf);
        for (DataTuple z:data) {
            if (this.filter.check(z.getA1())) {result.add(z);}
        }
        System.out.println("Filtering complete!\n");
        return result;
    }
    
    public ArrayList<DataTuple> getData() throws RemoteException{
        System.out.println("Return Data\n");
    	return this.data;	
    }
    
    public void readData1(String path) throws RemoteException{
    	
    	ArrayList<DataTuple> data = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
                String[] table = line.split(cvsSplitBy);
                DataTuple1 tuple = new DataTuple1(Integer.parseInt(table[0]),table[1], table[2]);
                data.add(tuple);
                //System.out.println(table1[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.data = data;
    }
    
public void readData2(String path) throws RemoteException{
    	
    	ArrayList<DataTuple> data = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
                String[] table = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table[0]), Integer.parseInt(table[1]), table[2]);
                data.add(tuple);
                //System.out.println(table1[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.data = data;
    }
}
