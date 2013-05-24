package aqua.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Utility;

import knetwork.managers.ServerNetworkManager;
import knetwork.message.messages.Message;

import aqua.entity.BaseEntity;
import aqua.entity.PhysEntity;
import aqua.entity.Platform;
import aqua.entity.Player;
import aqua.message.AquaMessageFactory;
import aqua.message.PlayerActionMessage;
import aqua.message.ServerUpdateMessage;
import aqua.physics.BasicForce;
import aqua.physics.PhysicsEngine;

public class ServerGame {
	private Map<Integer, ClientProfile> clientProfiles;
	private int numPlayers = 1;
	private ServerNetworkManager serverNetworkManager;

	private PhysicsEngine physicsEngine = PhysicsEngine.getInstance();
	private Map<Integer, Player> playerMap;
	
	private void initWalls() {
		int thickness = 25;
		
		PhysEntity bottomWall = new Platform(1, 600 - thickness - 1, 799, thickness);
		PhysEntity topWall = new Platform(1, 1, 799, thickness);
		PhysEntity leftWall = new Platform(1, thickness + 1, thickness, 600 - (2 * thickness) - 2);
		PhysEntity rightWall = new Platform(800 - thickness, thickness + 1, thickness, 600 - (2 * thickness) - 2);
		
		physicsEngine.queueAddition(bottomWall);
		physicsEngine.queueAddition(topWall);
		physicsEngine.queueAddition(leftWall);
		physicsEngine.queueAddition(rightWall);
		
		playerMap = new HashMap<Integer, Player>();
	}
	
	private void registerPlayer(Integer clientId) {
		Player player = new Player(100 * (playerMap.size() + 1), 100, 50, 50);
		player.addForce(new BasicForce(player, 0, 1200));
		physicsEngine.queueAddition(player);
		playerMap.put(clientId, player);

		ClientProfile profile = new ClientProfile();
		profile.clientId = clientId;
		profile.player = player;
		
		clientProfiles.put(clientId, profile);
	}
	
	public ServerGame() {
		clientProfiles = new HashMap<Integer, ClientProfile>();
		serverNetworkManager = new ServerNetworkManager();
		serverNetworkManager.setMessageFactory(new AquaMessageFactory());
	}
	
	public void handlePlayerAction(int playerId, PlayerActionMessage actionMessage) {
		System.out.println(playerId);
		System.out.println("SIZE " + playerMap.size());
		Player player = playerMap.get(playerId);
		
		if (actionMessage.isLeft()) {
			player.moveLeft();
		} else {
			player.stopMoveLeft();
		}
		
		if (actionMessage.isRight()) {
			player.moveRight();
		} else {
			player.stopMoveRight();
		}
		
		if (actionMessage.isJump()) {
			player.jump();
		} else {
			player.stopJump();
		}
		
		if (actionMessage.isLaunch()) {
			player.launchCord(actionMessage.getMouseX(), actionMessage.getMouseY());
		}
	}

	public void update(int delta) {
		delta = Math.min(delta, 17);
		float deltaSeconds = (float)delta / 1000;
		
		physicsEngine.performTimeStep(deltaSeconds);
	}
	
	private void init() {
		if (!serverNetworkManager.waitForRegistrations(8087, numPlayers)) {
			System.out.println("REGISTRATION FAILED!");
			return;
		}
		
		initWalls();
		
		for (Integer clientId : serverNetworkManager.getClientIds()) {
			System.out.println("REGISTER " + clientId);
			registerPlayer(clientId);
		}
	}
	
	private void gameLoop() {
		boolean gameActive = true;
		
		long currentTime = Utility.getTick();
		int accumulatedTime = 0;
		int dumpTime = 1000 / 60;
		
		while (gameActive) {
			long newTime = Utility.getTick();
			int delta = (int)(newTime - currentTime);
			
			for (int i = 0; i < numPlayers * 2; i++) {
				Message message = serverNetworkManager.recv();
				if (message != null) {
					if (message instanceof PlayerActionMessage) {
						handlePlayerAction(message.getSenderId(), (PlayerActionMessage)message);
					}
				}
			}
			
			update(delta);

			accumulatedTime += delta;
			if (accumulatedTime > dumpTime) {
				accumulatedTime -= dumpTime;
				Message updateMessage = new ServerUpdateMessage(physicsEngine.getEntities());
				serverNetworkManager.broadcast(updateMessage);
			}
			
			currentTime = newTime;
		}
	}
	
	public static void main(String[] args) {
		ServerGame game = new ServerGame();
		game.init();
		game.gameLoop();
	}
}
