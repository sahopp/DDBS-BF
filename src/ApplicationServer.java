import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        String folder = "CSV";

        System.out.println("\nSetting up Servers...");
        Registry registry5099 =LocateRegistry.createRegistry(5099);
        registry5099.rebind("sub1", new SubServer());

        Registry registry5098 =LocateRegistry.createRegistry(5098);
        registry5098.rebind("sub2", new SubServer());
        
        Registry registry5097 =LocateRegistry.createRegistry(5097);
        registry5097.rebind("sub3", new SubServer());

        Registry registry5096 =LocateRegistry.createRegistry(5096);
        registry5096.rebind("master", new MasterServer());

        System.out.println("Reading in Data on SubServers...");

        ((SubServerInterface) Naming.lookup("rmi://localhost:5099/sub1")).readData1("./" + folder + "/table1.csv");
        ((SubServerInterface) Naming.lookup("rmi://localhost:5098/sub2")).readData2("./" + folder + "/table2.csv");
        ((SubServerInterface) Naming.lookup("rmi://localhost:5097/sub3")).readData2("./" + folder + "/table3.csv");
        ((MasterServerInterface) Naming.lookup("rmi://localhost:5096/master")).lookupSubServers();

        System.out.println("\nApplication started!");
    }

}
