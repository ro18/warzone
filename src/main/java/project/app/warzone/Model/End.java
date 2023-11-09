package project.app.warzone.Model;

public class End extends Phase {

	End(GameEngine p_ge) {
		super(p_ge);
	}

	public void loadMap(String p_filename) {
		printInvalidCommandMessage("loadMap " + p_filename); // "Invalid command in this phase
	}

	public void showMap() {
		printInvalidCommandMessage("showMap");
	}

	public void editCountry(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage("editCountry " + p_editcmd + " " + p_editremovecmd);
	}
	public void editNeighbor(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage("editNeighbor " + p_editcmd + " " + p_editremovecmd);
	}
	public void saveMap() {
		printInvalidCommandMessage("saveMap");
	}

	public void setPlayers(String p_attribute, String p_playerName) {
		printInvalidCommandMessage("setPlayers" + p_attribute + " " + p_playerName);
	}

	public void assignCountries() {
		printInvalidCommandMessage("assignCountries");
	}

	public void assignCountriesFnullorDemo() {
		printInvalidCommandMessage("assignCountries");
	}



	public void reinforce(int p_countryID, int p_armies) {
		printInvalidCommandMessage("reinforce " + p_countryID + " " + p_armies);
	}

	public void attack() {
		printInvalidCommandMessage("attack");
	}

	public void fortify() {
		printInvalidCommandMessage("fortify");
	}

	public void endGame() {
		printInvalidCommandMessage("endGame");
	}

	public void showstats() {
		printInvalidCommandMessage("showstats"); // "Invalid command in this phase
	}

	public void showmapstatus() {
		printInvalidCommandMessage("showmapstatus"); // "Invalid command in this phase
	}
	public void editMap(String p_fileName){
		printInvalidCommandMessage("editMap " +p_fileName);
	}
	public void editContinent(String p_editcmd,String p_editremovecmd){
		printInvalidCommandMessage("editContinent " +p_editcmd + " " + p_editremovecmd);
	}
	public void next() {
		printInvalidCommandMessage("next"); // "Invalid command in this phase
	}

	public void advance(int p_CurrentPlayerId,int p_countryfrom,int p_countryTo, int p_armies) {
		printInvalidCommandMessage("advance " + p_countryfrom + " " + p_countryTo + " " + p_armies);
	}

	public void bomb(int p_countryID) {
		printInvalidCommandMessage("bomb " + p_countryID);
	}

	public void blockade( int p_countryID) {
		printInvalidCommandMessage("blockade " + p_countryID);
	}

	public void airlift( int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		printInvalidCommandMessage("airlift " + p_countryIDFrom + " " + p_countryIDTo + " " + p_armiesToAirlift);
	}

	public void negotiate(int p_targetPlayerId) {
		printInvalidCommandMessage("negotiate " + p_targetPlayerId);
	}

	public void assignCountriesForDemo() {
		printInvalidCommandMessage("assignCountries");
	}
}
