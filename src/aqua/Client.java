package aqua;

import knetwork.client.ClientNetworkManager;
import knetwork.message.BigMessage;

public class Client {
	//	String serverIp = "129.97.167.52";
	//	String serverIp = "192.168.226.128";
	public void start() {
		String serverIp = "192.168.1.8";
		
		int serverPort = 8087;
		
		ClientNetworkManager clientNetworkManager = new ClientNetworkManager();
		
		if (clientNetworkManager.register(serverIp, serverPort)) {
			int sendCounter = 0;
			
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				
				if (sendCounter++ < 4) {
			        BigMessage b1 = new BigMessage(1);
					clientNetworkManager.send_reliable(b1);
				}
			}
			
//			clientNetworkManager.disconnect();
		}
	}
}
