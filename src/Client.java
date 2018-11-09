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
        master.setup("CSVklein");
        System.out.println("Set up done.\n");


        /*
        long a = System.currentTimeMillis();
        ArrayList<DataTuple3> data1 = master.doUnionJoin(); 
        long time = System.currentTimeMillis()-a;
        System.out.println("Union Time: " + time);
        System.out.println("\nUnion Size: " + data1.size());
        */
        
        long b = System.currentTimeMillis();
        ArrayList<DataTuple3> data2 = master.doRealJoin();
        long time2 = System.currentTimeMillis()-b;
        System.out.println("Real Time: " + time2);
        System.out.println("Real Size: " + data2.size());


        long c = System.currentTimeMillis();
        ArrayList<DataTuple3> data3 = master.doNaiveJoin();
        long time3 = System.currentTimeMillis()-c;
        System.out.println("Naive Time: " + time3);
        System.out.println("Naive Size: " + data3.size());

        


    }
}
