import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SubServer extends UnicastRemoteObject implements SubServerInterface {

    protected SubServer() throws RemoteException {
        super();
    }

    private ArrayList<DataTuple> data;
    private BloomFilter filter;

    public void setFilterConfig(int m, int p, int[] a, int[] b) {
        this.filter = new BloomFilter(m, p, a, b);
    }

    public boolean[] getBF() {
        System.out.println("Hashing Data into BloomFilter...");
        for (DataTuple z:data) {
            this.filter.add(z.getA1());
        }
        System.out.println("Hashing complete!\n");
        return this.filter.getFilter();
    }

    public int getDataSize() {
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
    
    public ArrayList<DataTuple> getData() {
        System.out.println("Return Data\n");
    	return this.data;	
    }
    
    public void readData1(String path) {
    	
    	ArrayList<DataTuple> data = new ArrayList<>();
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
            	
                String[] table = line.split(cvsSplitBy);
                DataTuple1 tuple = new DataTuple1(Integer.parseInt(table[0]),table[1], table[2]);
                data.add(tuple);
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
    
public void readData2(String path) {
    	
    	ArrayList<DataTuple> data = new ArrayList<>();
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
            	
                String[] table = line.split(cvsSplitBy);
                DataTuple2 tuple = new DataTuple2(Integer.parseInt(table[0]), Integer.parseInt(table[1]), table[2]);
                data.add(tuple);
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
