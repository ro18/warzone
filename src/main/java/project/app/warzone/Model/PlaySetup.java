package project.app.warzone.Model;
import project.app.warzone.Features.PlayerFeatures;

public class PlaySetup extends Play {
	public PlayerFeatures d_playerFeatures;
	GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
	String p_playerName = "player1";//add player import feature here

	PlaySetup(GameEngine p_ge) {
		super(p_ge);
	}

	public void loadMap() {
		System.out.println("map has been loaded");
	}

	public void setPlayers() {
		d_playerFeatures.addPlayers( p_playerName, ge);
		System.out.println("players have been set");
	}

	public void assignCountries() {
		d_playerFeatures.assignCountries(ge);
		System.out.println("countries have been assigned");
	}

	public void reinforce() {
		printInvalidCommandMessage(); 
	}

	public void attack() {
		printInvalidCommandMessage(); 
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

	public void endGame() {
		printInvalidCommandMessage(); 
	}

	public void next() {
		ge.setPhase(new Reinforcement(ge));
	}
}
