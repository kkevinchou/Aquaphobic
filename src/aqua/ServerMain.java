package aqua;
import knetwork.server.ServerNetworkManager;

public class ServerMain {
	public static void main(String[] args) {
		System.out.println("Starting...");
		ServerNetworkManager serverNetworkManager = new ServerNetworkManager();
		
		if (serverNetworkManager.waitForRegistrations(8087, 1)) {
//			for (int i = 0; i < 4; i++) {
			while (true) {
				serverNetworkManager.recv();
//				serverNetworkManager.broadcast(new TestMessage());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
//			
//			serverNetworkManager.disconnect();
		}
	}
}