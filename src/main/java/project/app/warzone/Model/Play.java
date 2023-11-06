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
import project.app.warzone.Features.MapFeatures;

public abstract class Play extends Phase {

	public MapEditorCommands dMapEditorCommands;
	public PlayerCommands dPlayerCommands;
	MapFeatures d_mapFeatures = MapFeatures.getInstance();
    Map dMap = new Map();
    GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
    String p_filename = dMap.get_USER_SELECTED_FILE();

	Play(GameEngine p_ge) {
		super(p_ge); 
	}
	
	public String showMap() {
		String p_mapLocation=ge.gameMap.getMapDirectory()+"//"+ge.gameMap.get_USER_SELECTED_FILE()+".map";
        Boolean l_result=false;
        ge.gameMap = d_mapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if(!l_result){
			ge.setPhase(new Preload(ge));
            return("This map is not valid.Please try with some other map");
        }
        else{
            return("You can now proceed to add gameplayers");
        }
	}

	public void showstats() {}

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
