import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SubServer3Application {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        // TODO Auto-generated method stub

        String folder = "CSV";

        System.out.println("\nSetting up SubServer3...");
        Registry registry5099 =LocateRegistry.createRegistry(5097);
        registry5099.rebind("sub3", new SubServer());

        System.out.println("Reading in Data...");

        ((SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3")).readData2("./" + folder + "/table3.csv");

        System.out.println("\nSubServer3 ready!\n");

    }

}