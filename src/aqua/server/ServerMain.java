package aqua.server;
import java.util.HashMap;
import java.util.Map;

import aqua.entity.Player;
import aqua.message.AquaMessageFactory;
import aqua.message.ServerUpdateMessage;

import common.Utility;

import knetwork.managers.ServerNetworkManager;
import knetwork.message.messages.Message;

public class ServerMain {
	private Map<Integer, ClientProfile> clientProfiles;
	private int numPlayers = 1;
	private ServerGame game;
	private ServerNetworkManager serverNetworkManager;
	
	private ServerMain() {
		game = new ServerGame();
		clientProfiles = new HashMap<Integer, ClientProfile>();
		serverNetworkManager = new ServerNetworkManager();
		serverNetworkManager.setMessageFactory(new AquaMessageFactory());
	}
	
	private void registerPlayer(Integer clientId) {
		ClientProfile profile = new ClientProfile();
		profile.clientId = clientId;
		profile.player = new Player(100, 100, 50, 50);
		clientProfiles.put(clientId, profile);
	}
	
	private void run() {
		if (!serverNetworkManager.waitForRegistrations(8087, numPlayers)) {
			System.out.println("REGISTRATION FAILED!");
			return;
		}
		
		boolean gameActive = true;
		
//		for (Integer clientId : serverNetworkManager.getClientIds()) {
//			registerPlayer(clientId);
//		}
		
		long currentTime = Utility.getTick();
		
		boolean sent = false;
		
		while (gameActive) {
			long newTime = Utility.getTick();
			int delta = (int)((newTime - currentTime) / 1000);
			
			game.update(delta);
			
			
			if (!sent) {
				sent = true;
				Message updateMessage = new ServerUpdateMessage(game.getEntities());
				serverNetworkManager.send(1, updateMessage);
			}
			
			currentTime = newTime;
		}
		
	}
	
	public static void main(String[] args) {
		ServerMain main = new ServerMain();
		main.run();
	}
}