/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 *  
 *  This state represents a group of states, and defines the behavior 
 *  that is common to all the states in its group. All the states in its 
 *  group need to extend this class. 
 *  
 */
package project.app.warzone.Model;

import project.app.warzone.Commands.MapEditorCommands;
import project.app.warzone.Commands.PlayerCommands;

public abstract class Play extends Phase {

	public MapEditorCommands dMapEditorCommands;
	public PlayerCommands dPlayerCommands;
    Map dMap = new Map();
    GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
    String p_filename = dMap.get_USER_SELECTED_FILE();

	Play(GameEngine p_ge) {
		super(p_ge); 
	}
	
	public void showMap() {
		System.out.println("G **************************************************************** Postload Shomap");

        dMapEditorCommands.showmap();
        System.out.println("Map is Dsiplayed");
	}

	public void showstats() {
		System.out.println("G **************************************************************** Postload showstats");
		dPlayerCommands.showStats();
		System.out.println("Map is Dsiplayed");
	}

	public String editCountry(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
		return null;
		
	}

	public void saveMap() {
		printInvalidCommandMessage(); 
	}
	public void endGame() {
		ge.setPhase(new End(ge));
	}
}
