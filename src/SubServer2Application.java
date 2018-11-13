import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SubServer2Application {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        // TODO Auto-generated method stub

        String folder = "CSV";

        System.out.println("\nSetting up SubServer2...");
        Registry registry5099 =LocateRegistry.createRegistry(5098);
        registry5099.rebind("sub2", new SubServer());

        System.out.println("Reading in Data...");

        ((SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2")).readData2("./" + folder + "/table2.csv");

        System.out.println("\nSubServer2 ready!\n");

    }

}