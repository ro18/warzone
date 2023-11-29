package project.app.warzone.Model;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import project.app.warzone.Commands.MapEditorCommands;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;
import project.app.warzone.Utilities.MapResources;


public class Preload extends Edit implements java.util.Observer {

    private MapFeatures d_mapFeatures = MapFeatures.getInstance();    
    public MapEditorCommands dMapEditorCommands;
    public MapResources d_mapResources;
    private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

    Preload(GameEngine p_ge) {
		super(p_ge);
        d_mapResources = new MapResources();
        l_logEntryBuffer.addObserver(this);
	} 

    
    /** 
     * @return boolean
     */
    private boolean validateMap() {
        String p_mapLocation = ge.gameMap.getMapDirectory() + "//" + ge.gameMap.get_USER_SELECTED_FILE() + ".map";
        Boolean l_result = false;
        ge.gameMap = d_mapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if (!l_result) {
            System.out.println("This map is not valid.Please try with some other map");
            return false;
        } else {
            System.out.println("Map is validated. You can now proceed to add gameplayers");
            return true;
        }
    }
    
    
    /** 
     * @param p_filename
     */
    public void loadMap(String p_filename) {
        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("loadmap " + p_filename);
        if(ge.gameMap.fileExists(p_filename)){
            System.out.println("Current Phase: Preload");
            System.out.println("One file found");
            ge.gameMap.set_USER_SELECTED_FILE(p_filename);
            System.out.println("Choose one of the below commands to proceed:\n 1. showmap 2.editmap");
            l_logObject.setStatus(true, "Map loaded successfully " + p_filename);
            l_logEntryBuffer.notifyClasses(l_logObject);
            next();
        } else {
            l_logObject.setStatus(false, "Map not found");
            l_logEntryBuffer.notifyClasses(l_logObject);
            System.out.println(p_filename +"Map not found.");
            System.out.println("To create new Map use command editmap filename");
    
    }
}

    
    /** 
     * @param p_fileName
     */
    public void editMap(String p_fileName){
        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("editmap " + p_fileName);
        if (ge.gameMap.fileExists(p_fileName)) {
            System.out.println("One file found.");
            ge.gameMap.set_USER_SELECTED_FILE(p_fileName);
            if(validateMap()){ // add check to see if file is proper
                l_logObject.setStatus(true, "Map loaded successfully " + p_fileName);
                l_logEntryBuffer.notifyClasses(l_logObject);
                System.out.println("Please use gameplayer -add command to add players in the game");
            } else{
                l_logObject.setStatus(false, "Map is invalid " + p_fileName);
                l_logEntryBuffer.notifyClasses(l_logObject);
                System.out.println("Map cannot be used to play the game.");
                return;
            }
        } else {
            ge.prevUserCommand = Commands.EDITMAP;
            ge.gameMap.set_USER_SELECTED_FILE(p_fileName);

            ge.gameMap.createNewMapFile(p_fileName);
            System.out.println("\n");
            System.out.println(
                    "Choose one of the below commands to proceed:\n 1.editcontinent 2.editcountry 3.editneighbor");
            System.out.println("\n");
            System.out.println("Continet list:::: Select the continents you need to add");
            System.out.println("----------------------------------------------------------");
            d_mapResources.printMapDetails(d_mapResources.getAllContinents());
            System.out.println("\n");
            System.out.println("Countries list:::: Select the Countries you need to add");
            System.out.println("----------------------------------------------------------");

            d_mapResources.printMapDetails(d_mapResources.getAllCountries());

            System.out.println("\n");
            System.out.println("Please select the add or remove command");

            l_logObject.setStatus(true, "Map created successfully " + p_fileName);
            l_logEntryBuffer.notifyClasses(l_logObject);
        }
        next();
	}
    
    /** 
     * @param p_editcmd
     * @param p_editremovecmd
     */
    public void editNeighbor(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}

    
    /** 
     * @param p_editcmd
     * @param p_editremovecmd
     */
    public void editCountry(String p_editcmd,String p_editremovecmd) {
        printInvalidCommandMessage(); 
    }	

	public void saveMap() {
		printInvalidCommandMessage(); 
	}

    
    /** 
     * @param p_editcmd
     * @param p_editremovecmd
     */
    public void editContinent(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}

    public void showstats() {
        printInvalidCommandMessage(); 
    }
    public void showmapstatus() {
        printInvalidCommandMessage(); 
    }
    
    /** 
     * @param p_countryID
     * @param p_armies
     */
    public void reinforce(int p_countryID, int p_armies) {
		printInvalidCommandMessage(); 
	}

	public void next() {
        ge.setPhase(new Postload(ge));
	}

    
    /** 
     * @param p_obj
     * @param p_arg
     */
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
