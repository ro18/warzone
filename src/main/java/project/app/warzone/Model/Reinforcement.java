package project.app.warzone.Model;

/**
 *	
 */
public class Reinforcement extends MainPlay {

	Reinforcement(GameEngine p_ge) {
		super(p_ge);
	}

	public void loadMap(String p_filename) {
		printInvalidCommandMessage(); 
	}

	public void reinforce(int p_countryID, int p_armies) {
       String l_reString = d_playerFeatures.deployArmies(ge, p_countryID, p_armies);
	   
	   System.out.println(l_reString);
	   next();


	}

	public void attack() {
		printInvalidCommandMessage(); 
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

	public void next() {
		ge.setPhase(new Attack(ge));
	}
}
