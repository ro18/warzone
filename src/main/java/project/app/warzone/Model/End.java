package project.app.warzone.Model;

import project.app.warzone.Features.MapFeatures;

public class End extends Phase {

	End(GameEngine p_ge) {
		super(p_ge);
	}

	/**
	 * @param p_filename filename
	 */
	public void loadMap(String p_filename) {
		printInvalidCommandMessage();
	}

	public void showMap(MapFeatures newmapFeatures) {
		printInvalidCommandMessage(); 
	}

	/**
	 * @param p_editcmd       edit command
	 * @param p_editremovecmd remove command
	 */
	public void editCountry(String p_editcmd, String p_editremovecmd) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_editcmd       edit command
	 * @param p_editremovecmd remove command
	 */
	public void editNeighbor(String p_editcmd, String p_editremovecmd) {
		printInvalidCommandMessage();
	}

	public void saveMap() {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_attribute  attribute
	 * @param p_playerName player name
	 */
	public void setPlayers(String p_attribute, String p_playerName) {
		printInvalidCommandMessage();
	}

	public void assignCountries() {
		printInvalidCommandMessage();
	}

	public void assignCountriesForDemo() {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryID country ID
	 * @param p_armies    armies
	 */
	public void reinforce(int p_countryID, int p_armies) {
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

	public void showstats() {
		printInvalidCommandMessage();
	}

	public void showmapstatus() {
		printInvalidCommandMessage();
	}

	
	/** 
	 * @param p_fileName	filename
	 */
	public void editMap(String p_fileName) {
		printInvalidCommandMessage();
	}

	
	/** 
	 * @param p_editcmd	edit command
	 * @param p_editremovecmd	remove command
	 */
	public void editContinent(String p_editcmd, String p_editremovecmd) {
		printInvalidCommandMessage();
	}

	public void next() {
		printInvalidCommandMessage();
	}

	
	/** 
	 * @param p_CurrentPlayerId	current player
	 * @param p_countryfrom	source country
	 * @param p_countryTo	target country
	 * @param p_armies	no of army
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
	 * @param p_armiesToAirlift	armies to airlift
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
}
