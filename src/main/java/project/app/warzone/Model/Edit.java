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
	
	public void showMap() {
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
