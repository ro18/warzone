package project.app.warzone.Model;

import project.app.warzone.Features.MapFeatures;

public abstract class Edit extends Phase{
    //
    Edit(GameEngine p_ge) {
		super(p_ge);
	}

	abstract public void loadMap(String p_filename);

	
	/** 
	 * @param p_attribute	attribute
	 * @param p_playerName	player name
	 */
	public void setPlayers(String p_attribute, String p_playerName) {
		printInvalidCommandMessage();
	}

	abstract public void editMap(String p_fileName);

	public void assignCountries() {
		printInvalidCommandMessage();
	}

	public void assignCountriesForDemo() {
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
	
	public void showMap(MapFeatures newmapFeatures) {
		printInvalidCommandMessage(); 
	}

	
	/** 
	 * @param p_CurrentPlayerId	current player id
	 * @param p_countryfrom	source country
	 * @param p_countryTo	target country
	 * @param p_armies	no of armies
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
	 * @param p_countryIDFrom	source country
	 * @param p_countryIDTo	target country
	 * @param p_armiesToAirlift	army to airlift
	 */
	public void airlift(int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		printInvalidCommandMessage();
	}

	
	/** 
	 * @param p_targetPlayerId	target player ID
	 */
	public void negotiate(int p_targetPlayerId) {
		printInvalidCommandMessage();
	}

}
