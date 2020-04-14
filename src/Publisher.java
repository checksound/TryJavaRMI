import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Publisher {
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
			System.err.println("Usage: java Publisher message [host]");
			System.exit(1);
		}
		String message = args[0];
		String hostname = args.length > 1 ? args[1] : null;
		try {
			Registry registry = LocateRegistry.getRegistry(hostname);
			Topic topic = (Topic) registry.lookup("topic.1");
			topic.receive(message);
		} catch (Exception e) {
			System.err.println("caught an exception: " + e);
			e.printStackTrace();
		}
	}
}
