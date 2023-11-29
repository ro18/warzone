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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Utilities.LogObject;

public abstract class Play extends Phase implements Observer {

	public PlayerCommands dPlayerCommands;
	MapFeatures d_mapFeatures = MapFeatures.getInstance();
	PlayerFeatures d_playerFeatures = new PlayerFeatures();
	private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();
	Play(GameEngine p_ge) {
		super(p_ge); 
		l_logEntryBuffer.addObserver(this);
	}
	
	public void showMap(MapFeatures newmapFeatures) {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("showmap");
		
		//print map instead of inserting it in a map
		String p_mapLocation=ge.gameMap.getMapDirectory()+"//"+ge.gameMap.get_USER_SELECTED_FILE()+".map";
        Boolean l_result=false;
        ge.gameMap = d_mapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if(!l_result){
			l_logObject.setStatus(false, "Map is not valid.");
			l_logEntryBuffer.notifyClasses(l_logObject);
			ge.setPhase(new Preload(ge));
            System.out.println("This map is not valid.Please try with some other map");
        }
        else{
			l_logObject.setStatus(true, "Map shown.");
			l_logEntryBuffer.notifyClasses(l_logObject);
			ge.setPhase(new PlaySetup(ge));
            System.out.println("You can now proceed to add gameplayers");
        }
	}

	public void showstats() {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("showstats");
		l_logObject.setStatus(true, "Stats shown.");
		l_logEntryBuffer.notifyClasses(l_logObject);
        System.out.println("========================================");
        System.out.println("STATS:");
        d_playerFeatures.showStats(ge);
        System.out.println("STATS COMPLETE");
	}

	public void showmapstatus() {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("showmapstatus");
		l_logObject.setStatus(true, "Map status shown.");
		l_logEntryBuffer.notifyClasses(l_logObject);
        System.out.println("========================================");
        System.out.println("CURRENT MAP STATUS:");
        d_playerFeatures.showMapStatus(ge);
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
	public void update(java.util.Observable p_obj, Object p_arg) {
        LogObject l_logObject = (LogObject) p_arg;
        if (p_arg instanceof LogObject) {
            try {
                BufferedWriter l_writer = new BufferedWriter(
                        new FileWriter(System.getProperty("logFileLocation"), true));
                l_writer.newLine();
                l_writer.append(LogObject.d_logLevel + " " + l_logObject.d_command + "\n" + "Time: " + l_logObject.d_timestamp + "\n" + "Status: "
                        + l_logObject.d_statusCode + "\n" + "Description: " + l_logObject.d_message);
                l_writer.newLine();
                l_writer.close();
            } catch (IOException e) {
                System.out.println("Error Reading file");
            }
        }
    }
}
