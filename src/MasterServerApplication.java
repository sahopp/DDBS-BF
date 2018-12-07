import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MasterServerApplication {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        System.out.println("\nSetting up MasterServer...");
        Registry registry5096 =LocateRegistry.createRegistry(5096);
        registry5096.rebind("master", new MasterServer());

        ((MasterServerInterface) Naming.lookup("rmi://localhost:5096/master")).lookupSubServers();

        System.out.println("\nMasterServer ready!\n");

    }

}
