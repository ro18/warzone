package project.app.warzone.Model;
public abstract class Edit extends Phase{
    //
    Edit(GameEngine p_ge) {
		super(p_ge);
	}
	abstract public void loadMap(String p_filename);

	public void setPlayers(String p_attribute, String p_playerName) {
		printInvalidCommandMessage(); 
	}

	abstract public void editMap(String p_fileName);

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
	
	public void showMap() {
		printInvalidCommandMessage(); 
	}
}
