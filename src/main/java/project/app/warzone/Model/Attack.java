/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
package project.app.warzone.Model;

import project.app.warzone.Utilities.LogObject;

public class Attack extends MainPlay {





	private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();


	/**
	 * @param p_ge	gameengine
	 */
	
	public Attack(GameEngine p_ge) {
		super(p_ge);
		l_logEntryBuffer.addObserver(this);

	}
	
	
	/** 
	 * @param p_filename
	 */
	public void loadMap(String p_filename) {
		printInvalidCommandMessage(); 
	}

	/** 
	 * @param p_fileName	filename
	 */
	public void validateMap() {
		printInvalidCommandMessage();
	}

	
	/** 
	 * @param p_filename 	map filename
	 */
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
	}

	

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		ge.setPhase(new Fortify(ge));
	}
	
	
	/** 
	 * @param p_countryID
	 * @param p_armies
	 */
	public void reinforce(int p_countryID, int p_armies) {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("deploy " + p_countryID + " " + p_armies);
       	String l_reString = d_playerFeatures.deployArmies(ge, p_countryID, p_armies);
	   	l_logObject.setStatus(true, l_reString);
	   	l_logEntryBuffer.notifyClasses(l_logObject);

	
	}
	
	public void attack() {
		// ge.setPhase(new Attack(ge));

		// System.out.println("attack done");
		// ge.setPhase(new Fortify(ge));
	}

	
	/** 
	 * @param p_CurrentPlayerId
	 * @param p_countryfrom
	 * @param p_countryTo
	 * @param p_armies
	 */
	public void advance(int p_CurrentPlayerId,int p_countryfrom,int p_countryTo, int p_armies) {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("advance " + p_countryfrom + " " + p_countryTo +" "+p_armies);
		String l_reString = d_playerFeatures.advanceArmies(ge, p_CurrentPlayerId,p_countryfrom,p_countryTo, p_armies);
		l_logObject.setStatus(l_reString == "", l_reString);
	   	l_logEntryBuffer.notifyClasses(l_logObject);


	}



	
	/** 
	 * @param p_countryId	country ID
	 */
	public void bomb(int p_countryId) {
		String l_reString = d_playerFeatures.bombCountry(ge, p_countryId);

	}

	
	/** 
	 * @param p_countryID		country ID
	 */
	public void blockade( int p_countryID) {
		String l_reString = d_playerFeatures.blockadeCountry(ge, p_countryID);

	}

	
	/** 
	 * @param p_countryIDFrom	source country
	 * @param p_countryIDTo		target country
	 * @param p_armiesToAirlift	armies to airlift
	 */
	public void airlift( int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		String l_reString = d_playerFeatures.airlift(ge,p_countryIDFrom, p_countryIDTo,  p_armiesToAirlift);

	}

	
	/** 
	 * @param p_targetPlayerId	target player ID
	 */
	public void negotiate(int p_targetPlayerId) {
		String l_reString = d_playerFeatures.negotiate(ge,p_targetPlayerId);

	}


	

	public void fortify() {
		printInvalidCommandMessage(); 
	}


	public void setPlayerStrategy(){
		printInvalidCommandMessage();
	}


}
