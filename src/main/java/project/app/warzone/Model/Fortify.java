package project.app.warzone.Model;

public class Fortify extends MainPlay {

	Fortify(GameEngine p_ge) {
		super(p_ge);
	}

	public String loadMap(String p_filename) {
		printInvalidCommandMessage(); 
		return null;
	}
	public void reinforce() {
		printInvalidCommandMessage(); 
	}

	public void attack() {
		printInvalidCommandMessage(); 
	}

	public void fortify() {
		System.out.println("fortification done");
		ge.setPhase(new Reinforcement(ge));
	}

	public void next() {
		ge.setPhase(new Reinforcement(ge));
	}
}
