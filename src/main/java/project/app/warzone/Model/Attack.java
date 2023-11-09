/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
package project.app.warzone.Model;

public class Attack extends MainPlay {

	public Attack(GameEngine p_ge) {
		super(p_ge);
	}
	public void loadMap(String p_filename) {
		printInvalidCommandMessage(); 
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		ge.setPhase(new Fortify(ge));
	}
	
	public void reinforce(int p_countryID, int p_armies) {
		String l_reString = d_playerFeatures.deployArmies(ge, p_countryID, p_armies);
		
		System.out.println(l_reString);
	
	}
	
	public void attack() {
		ge.setPhase(new Attack(ge));

		System.out.println("attack done");
		// ge.setPhase(new Fortify(ge));
	}

	public void advance(int p_CurrentPlayerId,int p_countryfrom,int p_countryTo, int p_armies) {
		String l_reString = d_playerFeatures.advanceArmies(ge, p_CurrentPlayerId,p_countryfrom,p_countryTo, p_armies);

	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

}
