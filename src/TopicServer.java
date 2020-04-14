import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

public class TopicServer implements Topic {
	private List<MessageRecipient> subscribers;

	public TopicServer() {
		subscribers = new ArrayList<MessageRecipient>();
	}

	public synchronized void receive(String message) throws RemoteException {
		List<MessageRecipient> successes = new ArrayList<MessageRecipient>();
		for (MessageRecipient subscriber : subscribers) {
			try {
				subscriber.receive(message);
				successes.add(subscriber);
			} catch (Exception e) {
// silently drop any subscriber that fails
			}
		}
		subscribers = successes;
	}

	public synchronized void addSubscriber(MessageRecipient subscriber) throws RemoteException {
		subscribers.add(subscriber);
	}

	public static void main(String args[]) {
		try {
			TopicServer obj = new TopicServer();
			Topic stub = (Topic) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("topic.1", stub);
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
