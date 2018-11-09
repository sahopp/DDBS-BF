import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MasterServerInterface extends Remote {

	void setup(String folder) throws MalformedURLException, RemoteException, NotBoundException;

    ArrayList<DataTuple3> do12Join() throws MalformedURLException, RemoteException, NotBoundException;
    
    ArrayList<DataTuple3> doNaiveJoin() throws MalformedURLException, RemoteException, NotBoundException;
    
    ArrayList<DataTuple3> doIntersectionJoin() throws MalformedURLException, RemoteException, NotBoundException;
    
    ArrayList<DataTuple3> doUnionJoin() throws MalformedURLException, RemoteException, NotBoundException;

    ArrayList<DataTuple3> doRealJoin() throws MalformedURLException, RemoteException, NotBoundException;
}