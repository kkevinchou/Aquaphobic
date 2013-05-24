package aqua.client;

import aqua.server.ServerGame;

public class SinglePlayerServerThread extends Thread {
	@Override
	public void run() {
		Integer numPlayers = 1;
		String[] args = new String[1];
		args[0] = numPlayers.toString();
		
		ServerGame.main(args);
	}
}
