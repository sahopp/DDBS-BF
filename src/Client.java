import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class Client {


    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        System.out.println("Setting up Servers and Data...");
        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");
        master.setup("CSV");
        System.out.println("Set up done.\n");


        /*
        long a = System.currentTimeMillis();
        ArrayList<DataTuple3> data1 = master.doUnionJoin(); 
        long time = System.currentTimeMillis()-a;
        System.out.println("Union Time: " + time);
        System.out.println("\nUnion Size: " + data1.size());
        */

        System.out.println("Do BloomFilter Join...");
        long b = System.currentTimeMillis();
        ArrayList<DataTuple3> data2 = master.doRealJoin();
        long time2 = System.currentTimeMillis()-b;
        System.out.println("BloomFilter Join Time: " + time2/1000. + " seconds");
        System.out.println("BloomFilter Join Size: " + data2.size());
        System.out.println("BloomFilter Join done!\n");


        System.out.println("Do naive Join...");
        long c = System.currentTimeMillis();
        ArrayList<DataTuple3> data3 = master.doNaiveJoin();
        long time3 = System.currentTimeMillis()-c;
        System.out.println("Naive Join Time: " + time3/1000. + " seconds");
        System.out.println("Naive Join Size: " + data3.size());
        System.out.println("Naive Join done!\n");

        


    }
}
