package project.app.warzone.Model;

/**
 *	
 */
public class Reinforcement extends MainPlay {

	Reinforcement(GameEngine p_ge) {
		super(p_ge);
	}

	public String loadMap(String p_filename) {
		printInvalidCommandMessage(); 
		return null;
	}

	public void reinforce() {
		System.out.println("reinforcement done");
		ge.setPhase(new Attack(ge));
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
