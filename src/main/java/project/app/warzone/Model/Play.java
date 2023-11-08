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

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;

public abstract class Play extends Phase {

	public PlayerCommands dPlayerCommands;
	MapFeatures d_mapFeatures = MapFeatures.getInstance();
	PlayerFeatures d_playerFeatures = new PlayerFeatures();
	Play(GameEngine p_ge) {
		super(p_ge); 
	}
	
	public void showMap() {
		//print map instead of inserting it in a map
		String p_mapLocation=ge.gameMap.getMapDirectory()+"//"+ge.gameMap.get_USER_SELECTED_FILE()+".map";
        Boolean l_result=false;
        ge.gameMap = d_mapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if(!l_result){
			ge.setPhase(new Preload(ge));
            System.out.println("This map is not valid.Please try with some other map");
        }
        else{
			ge.setPhase(new PlaySetup(ge));
            System.out.println("You can now proceed to add gameplayers");
        }
	}

	public void showstats() {
        System.out.println("========================================");
        System.out.println("STATS:");
        d_playerFeatures.showStats(ge);
        System.out.println("STATS COMPLETE");
	}

	public void editCountry(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 		
	}

	public void saveMap() {
		printInvalidCommandMessage(); 
	}

	public void editMap(String p_fileName){
		printInvalidCommandMessage();
	}

	public void editContinent(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}
	public void editNeighbor(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}

	public void endGame() {
		ge.setPhase(new End(ge));
	}
}
