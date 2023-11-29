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

	/**
	 * @param p_attribute	attribute
	 * @param p_playerName	player names
	 */
	public void setPlayers(String p_attribute, String p_playerName) {
		this.printInvalidCommandMessage();
	}

	public void assignCountries() {
		this.printInvalidCommandMessage();
	}

	public void assignCountriesForDemo() {
		this.printInvalidCommandMessage();
	}

	/**
	 * @param p_CurrentPlayerId	current player
	 * @param p_countryfrom	source country
	 * @param p_countryTo	target country
	 * @param p_armies	no  of armies
	 */
	public void advance(int p_CurrentPlayerId, int p_countryfrom, int p_countryTo, int p_armies) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryID	country ID
	 */
	public void bomb(int p_countryID) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryID	country ID
	 */
	public void blockade(int p_countryID) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryIDFrom	source cuontry
	 * @param p_countryIDTo	target country
	 * @param p_armiesToAirlift	army to airlift
	 */
	public void airlift(int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_targetPlayerId	target player
	 */
	public void negotiate(int p_targetPlayerId) {
		printInvalidCommandMessage();
	}

	public void setPlayerStrategy(){
		printInvalidCommandMessage();
	}
}
