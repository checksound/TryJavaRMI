import java.rmi.RemoteException;

public interface Topic extends MessageRecipient {
	void addSubscriber(MessageRecipient subscriber) throws RemoteException;
}
