package project.app.warzone.Commands;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.ConquestFileReader;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.MapFeatureAdapter;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.MapResources;

/**
 * This class stores all the map-related commands allowed in gameplay
 */
@ShellComponent
public class MapEditorCommands {
    public GameEngine d_gameEngine;
    public PlayerCommands d_playerCommands;
    public PlayerFeatures d_playerFeatures;
    public MapFeatures dMapFeatures;
    public project.app.warzone.Model.Map dMap;

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

        d_gameEngine.prevUserCommand = Commands.LOADMAP;
        d_gameEngine.getGamePhase().loadMap(p_filename);
    }

    /**
     * Display the game map
     * 
     * @return String returns message about the map validation
     * @throws IOException
     */
    @ShellMethod(key = "showmap", value = "Used to display map continents with terriotories and boundaries")
    public void showmap() {
        String p_mapLocation = d_gameEngine.gameMap.getMapDirectory() + "/"
              + d_gameEngine.gameMap.get_USER_SELECTED_FILE() + ".map";
        String l_line="";
             
        try{
    BufferedReader reader = new BufferedReader(new FileReader(p_mapLocation));
    MapFeatures mapFeatures= MapFeatures.getInstance();
    l_line=reader.readLine();
                if(l_line.equals("conquest")){
                   mapFeatures = new MapFeatureAdapter(new ConquestFileReader());
                    // newmMapFeatures.readMap(p_mapLocation);
                }  
            else{
                 mapFeatures = MapFeatures.getInstance();
                // mapFeatures.readMap(p_mapLocation);
            }




        d_gameEngine.getGamePhase().showMap(mapFeatures);
    
}
    catch(FileNotFoundException e)
    {
   System.out.println("File Not Found----");
    }
    catch (IOException e) {
        System.out.println("IOException----");
    }
    }

    // public project.app.warzone.Model.Map returnMap() {
    //     String p_mapLocation = d_gameEngine.gameMap.getMapDirectory() + "/"
    //             + d_gameEngine.gameMap.get_USER_SELECTED_FILE() + ".map";



    //     dMap = dMapFeatures.readMap(p_mapLocation);
    //     return dMap;
    // }

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
}
