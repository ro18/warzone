package project.app.warzone.Model;

public class Fortify extends MainPlay {

	Fortify(GameEngine p_ge) {
		super(p_ge);
	}

	
	/** 
	 * @param p_filename	filename
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

	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
	}

	
	/** 
	 * @param p_countryID	country ID
	 * @param p_armies	no of armies
	 */
	public void reinforce(int p_countryID, int p_armies) {
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
