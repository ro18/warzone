package project.app.warzone.Model;

// This class is Phase.java state design pattern class with GameEngine.java as the context class.  
// This class will implement the state design pattern. The state pattern will be used to implement the game phases.
// The game play phase must be divided into the following phases: startup, issue order, and order execution phases

public abstract class Phase {

	/**
	 *  Contains a reference to the State of the GameEngine 
	 *  so that the state object can change the state of 
	 *  the GameEngine to transition between states. 
	 */
	GameEngine ge;

	Phase(GameEngine p_ge) {
		ge = p_ge;
	}

	// common commands
	abstract public String loadMap(String p_filename);
	abstract public String showMap();

	// Edit map commands
	abstract public String editCountry(String p_editcmd,String p_editremovecmd);
	abstract public void saveMap();

	// Play commands
	// game setup commands
	abstract public void setPlayers();
	abstract public void assignCountries();

	// reinforcement commands
	abstract public void reinforce();

	// attack commands
	abstract public void attack();

	// fortify commands
	abstract public void fortify();

	// end command
	abstract public void endGame();

	// go to next phase
	abstract public void next();
	
	/**
	 *  Common method to all States. 
	 */
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName() );
	}
}