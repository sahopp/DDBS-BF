import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException{
        // TODO Auto-generated method stub
        Registry registry5099 =LocateRegistry.createRegistry(5099);
        registry5099.rebind("sub1", new SubServer());

        Registry registry5098 =LocateRegistry.createRegistry(5098);
        registry5098.rebind("sub2", new SubServer());
        
        Registry registry5097 =LocateRegistry.createRegistry(5097);
        registry5097.rebind("sub3", new SubServer());

        Registry registry5096 =LocateRegistry.createRegistry(5096);
        registry5096.rebind("master", new MasterServer());
    }

}
