package project.app.warzone.Model;
import project.app.warzone.Features.PlayerFeatures;

public class PlaySetup extends Play {
	public PlayerFeatures d_playerFeatures;
	GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
	String p_playerName = "player1";//add player import feature here

	PlaySetup(GameEngine p_ge) {
		super(p_ge);
	}

	public String loadMap(String p_filename) {
        if(ge.gameMap.fileExists(p_filename)){
            System.out.println("One file found.");
            ge.gameMap.set_USER_SELECTED_FILE(p_filename);
            ge.setPhase(new Postload(ge));
            return "Choose one of the below commands to proceed:\n 1. showmap 2.editmap";
        } else {
            return "Map not found.";
        }
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
