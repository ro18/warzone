package project.app.warzone.Model;

public class End extends Phase {

	End(GameEngine p_ge) {
		super(p_ge);
	}

	public String loadMap(String p_filename) {
		printInvalidCommandMessage(); 
		return null;
	}

	public String showMap() {
		printInvalidCommandMessage(); 
		return null;
	}

	public String editCountry(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
		return null;
		
	}
	public void saveMap() {
		printInvalidCommandMessage(); 
	}

	public void setPlayers() {
		printInvalidCommandMessage(); 
	}

	public void assignCountries() {
		printInvalidCommandMessage(); 
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
		printInvalidCommandMessage(); 
	}
}
