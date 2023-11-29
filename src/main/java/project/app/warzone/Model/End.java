package project.app.warzone.Model;

import project.app.warzone.Features.MapFeatures;

public class End extends Phase {

	End(GameEngine p_ge) {
		super(p_ge);
	}

	public void loadMap(String p_filename) {
		printInvalidCommandMessage(); 
	}

	public void showMap(MapFeatures newmapFeatures) {
		printInvalidCommandMessage(); 
	}

	public void editCountry(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}
	public void editNeighbor(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}
	public void saveMap() {
		printInvalidCommandMessage(); 
	}

	public void setPlayers(String p_attribute, String p_playerName) {
		printInvalidCommandMessage(); 
	}

	public void assignCountries() {
		printInvalidCommandMessage(); 
	}

	public void assignCountriesForDemo() {
		printInvalidCommandMessage(); 
	}



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
	public void editMap(String p_fileName){
		printInvalidCommandMessage();
	}
	public void editContinent(String p_editcmd,String p_editremovecmd){
		printInvalidCommandMessage();
	}
	public void next() {
		printInvalidCommandMessage(); 
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
