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
		printInvalidCommandMessage(); 
	}

	public void attack() {
		System.out.println("attack done");
		ge.setPhase(new Fortify(ge));
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

}
