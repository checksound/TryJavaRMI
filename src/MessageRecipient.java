import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageRecipient extends Remote {
	void receive(String message) throws RemoteException;
}