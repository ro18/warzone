package project.app.warzone.Model;
public abstract class Edit extends Phase{
    //
    Edit(GameEngine p_ge) {
		super(p_ge);
	}
	abstract public void loadMap(String p_filename);

	public void setPlayers(String p_attribute, String p_playerName) {
		printInvalidCommandMessage("setPlayers" + p_attribute + " " + p_playerName); 
	}

	abstract public void editMap(String p_fileName);

	public void assignCountries() {
		printInvalidCommandMessage("assignCountries"); 
	}

	public void assignCountriesForDemo() {
		printInvalidCommandMessage("assignCountries"); 
	}


	public void reinforce() {
		printInvalidCommandMessage("reinforce");
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
	
	public void showMap() {
		printInvalidCommandMessage("showMap"); 
	}



	public void advance(int p_CurrentPlayerId,int p_countryfrom,int p_countryTo, int p_armies) {
		printInvalidCommandMessage("advance " + p_countryTo + " " + p_armies); 
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


}
