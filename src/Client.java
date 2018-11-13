import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {


    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");

        System.out.println("\nDo new BloomFilter Join...");
        long c = System.currentTimeMillis();
        ArrayList<DataTuple3> DataBF1 = master.doBF1Join();
        long time3 = System.currentTimeMillis()-c;
        System.out.println("New BloomFilter Join Time: " + time3/1000. + " seconds");
        System.out.println("New BloomFilter Join Size: " + DataBF1.size());

            System.out.println("\nDo BloomFilter Join...");
            long a = System.currentTimeMillis();
        ArrayList<DataTuple3> DataBF = master.doBFJoin();
            long time2 = System.currentTimeMillis()-a;
            System.out.println("BloomFilter Join Time: " + time2/1000. + " seconds");
            System.out.println("BloomFilter Join Size: " + DataBF.size());



            System.out.println("\nDo naive Join...");
            long b = System.currentTimeMillis();
        ArrayList<DataTuple3> DataNaive = master.doNaiveJoin();
            long time1 = System.currentTimeMillis()-b;
            System.out.println("Naive Join Time: " + time1/1000. + " seconds");
            System.out.println("Naive Join Size: " + DataNaive.size());

        


    }
}
