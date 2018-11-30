import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {


    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");

        int m;
        ArrayList<DataTuple3> DataBF;
        for (int i = 0; i < 5; i++) {

            m = 1000000;
            System.out.println("\nDo BloomFilter Join... " + m + "  " + i);
            long a = System.currentTimeMillis();
            DataBF = master.doBFJoin(m);
            long time = System.currentTimeMillis() - a;
            System.out.println("BloomFilter Join Time: " + time / 1000. + " seconds");
            System.out.println("BloomFilter Join Size: " + DataBF.size());


            m = 5000000;
            System.out.println("\nDo BloomFilter Join... " + m + "  " + i);
            a = System.currentTimeMillis();
            DataBF = master.doBFJoin(m);
            time = System.currentTimeMillis() - a;
            System.out.println("BloomFilter Join Time: " + time / 1000. + " seconds");
            System.out.println("BloomFilter Join Size: " + DataBF.size());


            m = 130000000;
            System.out.println("\nDo BloomFilter Join... " + m + "  " + i);
            a = System.currentTimeMillis();
            DataBF = master.doBFJoin(m);
            time = System.currentTimeMillis() - a;
            System.out.println("BloomFilter Join Time: " + time / 1000. + " seconds");
            System.out.println("BloomFilter Join Size: " + DataBF.size());


            System.out.println("\nDo naive Join...");
            long b = System.currentTimeMillis();
            ArrayList<DataTuple3> DataNaive = master.doNaiveJoin();
            long time1 = System.currentTimeMillis() - b;
            System.out.println("Naive Join Time: " + time1 / 1000. + " seconds");
            System.out.println("Naive Join Size: " + DataNaive.size());
        }
    }
}
