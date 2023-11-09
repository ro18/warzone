/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 *  
 *  This state represents a group of states, and defines the behavior 
 *  that is common to all the states in its group. All the states in its 
 *  group need to extend this class. 
 *  
 */
package project.app.warzone.Model;

public abstract class MainPlay extends Play {

	MainPlay(GameEngine p_ge) {
		super(p_ge);
	}

	public void loadMap() {
		this.printInvalidCommandMessage();
	}

	public void setPlayers(String p_attribute, String p_playerName) {
		this.printInvalidCommandMessage();	
	}

	public void assignCountries() {
		this.printInvalidCommandMessage();
	}


	public void advance(int p_CurrentPlayerId,int p_countryfrom,int p_countryTo, int p_armies) {
		printInvalidCommandMessage(); 
	}

	public void bomb(int p_countryID) {
		printInvalidCommandMessage(); 
	}

	public void blockade( int p_countryID) {
		printInvalidCommandMessage(); 
	}

	public void airlift( int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		printInvalidCommandMessage(); 
	}

	public void negotiate(int p_targetPlayerId) {
		printInvalidCommandMessage(); 
	}
}
