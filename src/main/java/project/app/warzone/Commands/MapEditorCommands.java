package project.app.warzone.Commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.LogEntryBuffer;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;
import project.app.warzone.Utilities.MapResources;

/**
 * This class stores all the map-related commands allowed in gameplay
 */
@ShellComponent
public class MapEditorCommands implements java.util.Observer {
    public GameEngine d_gameEngine;
    public PlayerCommands d_playerCommands;
    public PlayerFeatures d_playerFeatures;
    public MapFeatures dMapFeatures;
    public project.app.warzone.Model.Map dMap;
    private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

    /**
     * Constructor for MapEditorCommands
     * 
     * @param p_mapFeatures    MapFeatures object
     * @param p_gameEngine     GameEngine object
     * @param p_playerFeatures PlayerFeatures object
     * @param p_mapResources   MapResources object
     */
    public MapEditorCommands(MapFeatures p_mapFeatures, GameEngine p_gameEngine, PlayerFeatures p_playerFeatures,
            MapResources p_mapResources) {
        // this.d_mapFeatures = p_mapFeatures;
        this.d_gameEngine = p_gameEngine;
        d_playerCommands = new PlayerCommands(p_gameEngine, p_playerFeatures);
    }

    /**
     * command for loading map for gameplay
     * 
     * @param p_filename filename of the mao
     * @return String returns message if the map is loaded
     */
    @ShellMethod(key = "loadmap", value = "Player can create or open an existing map")
    public void loadMap(@ShellOption String p_filename) {
        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("loadmap " + p_filename);
        l_logEntryBuffer.addObserver(this);

        d_gameEngine.prevUserCommand = Commands.LOADMAP;
        d_gameEngine.getGamePhase().loadMap(p_filename);
    }

    /**
     * Display the game map
     * 
     * @return String returns message about the map validation
     */
    @ShellMethod(key = "showmap", value = "Used to display map continents with terriotories and boundaries")
    public void showmap() {
        d_gameEngine.getGamePhase().showMap();
    }

    public project.app.warzone.Model.Map returnMap() {
        String p_mapLocation = d_gameEngine.gameMap.getMapDirectory() + "/"
                + d_gameEngine.gameMap.get_USER_SELECTED_FILE() + ".map";
        dMap = dMapFeatures.readMap(p_mapLocation);
        return dMap;
    }

    /**
     * command for editing continent
     * 
     * @param p_editremovecmd for remove command
     * @param p_editcmd       for edit command
     * @return returns the message about status of command
     */
    @ShellMethod(key = "editcontinent", prefix = "-", value = "This is used to add or update continents")
    public void editcontinent(@ShellOption(value = "a", defaultValue = ShellOption.NULL, arity = 20) String p_editcmd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL, arity = 20) String p_editremovecmd) {
        d_gameEngine.getGamePhase().editContinent(p_editcmd, p_editremovecmd);
    }

    /**
     * @param p_editcmd       edit country command
     * @param p_editremovecmd remove country command
     * @return returns the status of editing country
     * @throws IOException throwing statement incase of any Exception
     */
    @ShellMethod(key = "editcountry", prefix = "-", value = "This is used to add continents")
    public void editcountry(@ShellOption(value = "a", defaultValue = ShellOption.NULL) String p_editcmd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL) String p_editremovecmd) throws IOException {
        d_gameEngine.getGamePhase().editCountry(p_editcmd, p_editremovecmd);
    }

    /**
     * @param p_editcmd       edit country command
     * @param p_editremovecmd remove country command
     * @return returns the status of editing neighbor country
     * @throws IOException throwing statement incase of any Exception
     */
    @ShellMethod(key = "editneighbor", prefix = "-", value = "This is used to add or update neighbor")
    public void editNeighbor(@ShellOption(value = "a", defaultValue = ShellOption.NULL, arity = 20) String p_editcmd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL, arity = 20) String p_editremovecmd)
            throws IOException {
            d_gameEngine.getGamePhase().editNeighbor(p_editcmd, p_editremovecmd);
        }

    /**
     * @param p_filename storing user selected file
     * @return returns status of editing map
     */
    @ShellMethod(key = "editmap", value = "This is used to add or create map")
    public void editmap(@ShellOption String p_filename) {
        d_gameEngine.prevUserCommand = Commands.EDITMAP;
        d_gameEngine.getGamePhase().editMap(p_filename);
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
