import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SubServer1Application {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        String folder = "CSV";

        System.out.println("\nSetting up SubServer1...");
        Registry registry5099 =LocateRegistry.createRegistry(5099);
        registry5099.rebind("sub1", new SubServer());

        System.out.println("Reading in Data...");

        ((SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1")).readData1("./" + folder + "/table1.csv");

        System.out.println("\nSubServer1 ready!\n");

    }

}