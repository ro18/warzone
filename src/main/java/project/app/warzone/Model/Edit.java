package project.app.warzone.Model;
public abstract class Edit extends Phase{
    //
    Edit(GameEngine p_ge) {
		super(p_ge);
	}
	abstract public String loadMap(String p_filename);

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
	
	public String showMap() {
		printInvalidCommandMessage(); 
		return null;
	}
}
