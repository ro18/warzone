package project.app.warzone.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.MapResources;

/**
 * This class stores all the map-related commands allowed in gameplay
 */
@ShellComponent
public class MapEditorCommands {

    private final MapFeatures d_mapFeatures;
    public GameEngine d_gameEngine;
    public PlayerCommands d_playerCommands;
    public PlayerFeatures d_playerFeatures;
    private final MapResources d_mapResources;
    public MapFeatures dMapFeatures;
    public project.app.warzone.Model.Map dMap;

    /**
     * Constructor for MapEditorCommands
     * 
     * @param p_mapFeatures           MapFeatures object
     * @param p_gameEngine            GameEngine object
     * @param p_playerFeatures        PlayerFeatures object
     * @param p_mapResources          MapResources object
     */
    public MapEditorCommands(MapFeatures p_mapFeatures, GameEngine p_gameEngine, PlayerFeatures p_playerFeatures, MapResources p_mapResources){
        this.d_mapFeatures = p_mapFeatures;
        this.d_gameEngine = p_gameEngine;
        d_playerCommands = new PlayerCommands(p_gameEngine, p_playerFeatures);
        this.d_mapResources = p_mapResources;
    }


    
    /** 
     * command for loading map for gameplay
     * 
     * @param p_filename        filename of the mao
     * @return String           returns message if the map is loaded
     */
    @ShellMethod(key= "loadmap", value="Player can create or open an existing map")
    public String loadMap(@ShellOption String p_filename){
        
        d_gameEngine.prevUserCommand=Commands.LOADMAP;
        d_gameEngine.start();
        if(d_gameEngine.gameMap.fileExists(p_filename)){
            System.out.println("One file found.");
            d_gameEngine.gameMap.set_USER_SELECTED_FILE(p_filename);
            //System.out.println(d_gameEngine.gameMap.get_USER_SELECTED_FILE());
            //d_gameEngine.start(d_gameEngine.gameMap.get_USER_SELECTED_FILE());
            System.out.println("Game started successfully..");
            return "Choose one of the below commands to proceed:\n 1. showmap 2.editmap";
        } else {
            return "Map not found.";
        }
    }



    
    /** 
     * Display the game map
     * 
     * @return String           returns message about the map validation
     */
    @ShellMethod(key= "showmap", value="Used to display map continents with terriotories and boundaries")
    public String showmap(){
        String p_mapLocation=d_gameEngine.gameMap.getMapDirectory()+"/"+d_gameEngine.gameMap.get_USER_SELECTED_FILE()+".map";

        d_gameEngine.gameMap = d_mapFeatures.readMap(p_mapLocation);
        Boolean l_result = d_mapFeatures.validateEntireGraph(d_gameEngine);
        if(!l_result){
            return("This map is not valid.Please try with some other map");
        }
        else{
            return("You can now proceed to add gameplayers");
        }

    }

    public project.app.warzone.Model.Map returnMap(){
        String p_mapLocation=d_gameEngine.gameMap.getMapDirectory()+"/"+d_gameEngine.gameMap.get_USER_SELECTED_FILE()+".map";
        dMap = dMapFeatures.readMap(p_mapLocation);
        return dMap;
    }

    
    /**
     * command for editing continent
     * 
     * @param p_editremovecmd       for remove command
     * @param p_editcmd             for edit command
     * @return                      returns the message about status of command
    */
     @ShellMethod(key = "editcontinent", prefix = "-", value = "This is used to add or update continents")
    public String editcontinent(@ShellOption(value = "a", defaultValue = ShellOption.NULL,arity=10) String p_editcmd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL, arity=10) String p_editremovecmd) {
        Map<String, String> listofContinents = new HashMap<String, String>();
        java.util.Map<Integer, String> listOfContinentsResource = d_mapResources.getAllContinents();

        if (d_gameEngine.prevUserCommand == Commands.EDITMAP || d_gameEngine.prevUserCommand == Commands.ADDCONTINENT
                || d_gameEngine.prevUserCommand == Commands.REMOVECONTINENT
                || d_gameEngine.prevUserCommand == Commands.ADDCOUNTRY
                || d_gameEngine.prevUserCommand == Commands.REMOVECOUNTRY
                || d_gameEngine.prevUserCommand == Commands.ADDNEIGHBOUR
                || d_gameEngine.prevUserCommand == Commands.REMOVENEIGHBOUR) {

            if (p_editcmd != null && p_editcmd != "") {
                d_gameEngine.prevUserCommand = Commands.ADDCONTINENT;
                String[] l_editCmd = p_editcmd.split(",");

                int l_i = 0;

                String l_commandToCheck = "-add";
                while (l_i < l_editCmd.length) {

                    System.out.println(l_editCmd[l_i] + ":" + l_commandToCheck);
                    if (l_editCmd[l_i].toString().equals(l_commandToCheck)) {
                        l_i++;
                        continue;

                    } else {
                        listofContinents.put(l_editCmd[l_i], l_editCmd[l_i + 1]);
                        l_i += 2;

                    }

                }

                try {
                    d_mapFeatures.writeContinentsToFile(listofContinents, d_gameEngine);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "Continents addded succesfully";
            } else {

                d_gameEngine.prevUserCommand = Commands.REMOVECONTINENT;

                List<String> l_listOfContinentsToRemove = new ArrayList<>();
                String[] l_editremovecmd = p_editremovecmd.split(",");
                int l_i = 0;

                String commandToCheck = "-remove";

                while (l_i < l_editremovecmd.length) {

                    System.out.println(l_editremovecmd[l_i] + ":" + commandToCheck);
                    if (l_editremovecmd[l_i].toString().equals(commandToCheck)) {

                        l_i++;
                        continue;

                    } else {
                        l_listOfContinentsToRemove
                                .add(listOfContinentsResource.get(Integer.parseInt(l_editremovecmd[l_i])));
                        System.out.println("listofContinents" + l_listOfContinentsToRemove);
                        l_i++;
                    }

                }
                try {
                    d_mapFeatures.removeContinentFromFile(l_listOfContinentsToRemove, d_gameEngine);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "Continent removed succesfully";
            }
        }

        else {
            return "You cannnot add continent.";

        }

    }

    /**
     * @param p_editcmd                edit country command
     * @param p_editremovecmd          remove country command
     * @return                         returns the status of editing country
     * @throws IOException             throwing statement incase of any Exception 
     */
    @ShellMethod(key = "editcountry", prefix = "-", value = "This is used to add continents")
    public String editcountry(@ShellOption(value = "a", defaultValue = ShellOption.NULL) String p_editcmd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL) String p_editremovecmd) throws IOException {


        if (d_gameEngine.prevUserCommand == Commands.ADDCONTINENT
                || d_gameEngine.prevUserCommand == Commands.REMOVECONTINENT
                || d_gameEngine.prevUserCommand == Commands.ADDCOUNTRY
                || d_gameEngine.prevUserCommand == Commands.REMOVECOUNTRY) {

            if (p_editcmd != null && p_editcmd != "") {
                Map<String, String> listofCountries = new HashMap<String, String>();
                d_gameEngine.prevUserCommand = Commands.ADDCOUNTRY;

                String[] editCmd = p_editcmd.split(",");
                int l_i = 0;

                String commandToCheck = "-add";
                while (l_i < editCmd.length) {

                    if (editCmd[l_i].toString().equals(commandToCheck)) {
                        l_i++;
                        continue;

                    } else {
                        listofCountries.put(editCmd[l_i], editCmd[l_i + 1]);
                        l_i += 2;

                    }

                }
                try {
                    d_mapFeatures.writeCountriesToFile(listofCountries, d_gameEngine);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "Countries addded succesfully";
            } else {

                List<String> l_listofCountries = new ArrayList<>();
                String[] l_editremovecmd = p_editremovecmd.split(",");

                int l_i = 0;

                String commandToCheck = "-remove";

                while (l_i < l_editremovecmd.length) {

                    System.out.println(l_editremovecmd[l_i] + ":" + commandToCheck);
                    if (l_editremovecmd[l_i].toString().equals(commandToCheck)) {

                        l_i++;
                        continue;

                    } else {
                        l_listofCountries.add(l_editremovecmd[l_i]);

                        System.out.println("listofCountries" + l_listofCountries);
                        l_i++;
                    }

                }
                try {
                    d_mapFeatures.removeCountriesFromFile(l_listofCountries, d_gameEngine);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "Countries removed succesfully";
            }
        } else {
            return "You cannnot add country";

        }

    }

    /**
     * @param p_editcmd                     edit country command
     * @param p_editremovecmd               remove country command
     * @return                              returns the status of editing neighbor country
     * @throws IOException                  throwing statement incase of any Exception 
     */
    @ShellMethod(key = "editneighbor", prefix = "-", value = "This is used to add or update neighbor")
    public String editNeighbor(@ShellOption(value = "a", defaultValue = ShellOption.NULL) String p_editcmd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL, arity = 10) String p_editremovecmd)
            throws IOException {
        if (d_gameEngine.prevUserCommand == Commands.ADDCOUNTRY || d_gameEngine.prevUserCommand == Commands.REMOVECOUNTRY
                || d_gameEngine.prevUserCommand == Commands.ADDNEIGHBOUR
                || d_gameEngine.prevUserCommand == Commands.REMOVENEIGHBOUR) {
            if (p_editcmd != null && p_editcmd != "") {
                Map<String, String> listofBorders = new HashMap<String, String>();
                String[] editCmd = p_editcmd.split(",");

                int l_i = 0;

                String l_commandToCheck = "-add";
                while (l_i < editCmd.length) {

                    if (editCmd[l_i].toString().equals(l_commandToCheck)) {
                        l_i++;
                        continue;

                    } else {
                        listofBorders.put(editCmd[l_i],
                                listofBorders.get(editCmd[l_i]) != null
                                        ? listofBorders.get(editCmd[l_i]).toString() + " " + editCmd[l_i + 1].toString()
                                        : editCmd[l_i + 1]);
                        l_i += 2;

                    }

                }
                try {
                    d_mapFeatures.writeCountriesNeighborToFile(listofBorders, d_gameEngine);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "Country borders addded succesfully";
            } else {

                d_gameEngine.prevUserCommand = Commands.REMOVENEIGHBOUR;

                String[] l_editremovecmd = p_editremovecmd.split(",");
                int l_i = 0;

                String commandToCheck = "-remove";
                Map<String, String> listofBorders = new HashMap<String, String>();

                while (l_i < l_editremovecmd.length) {

                    if (l_editremovecmd[l_i].toString().equals(commandToCheck)) {

                        l_i++;
                        continue;

                    } else {
                        listofBorders.put(l_editremovecmd[l_i],
                                listofBorders.get(l_editremovecmd[l_i]) != null
                                        ? listofBorders.get(l_editremovecmd[l_i]).toString() + " "
                                                + l_editremovecmd[l_i + 1].toString()
                                        : l_editremovecmd[l_i + 1]);
                        l_i += 2;
                    }

                }
                try {
                    d_mapFeatures.removeborderFromFile(listofBorders, d_gameEngine);

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "Borders removed succesfully";
            }

        }
        // }

        return "You cannot use edit neighbor command now.";

    }


    /**
     * @param p_filename           storing user selected file
     * @return                     returns status of editing map
     */
    @ShellMethod(key = "editmap", value = "This is used to add or create map")
    public String editmap(@ShellOption String p_filename) {
        if (d_gameEngine.gameMap.fileExists(p_filename)) {
            System.out.println("One file found.");
            d_gameEngine.gameMap.set_USER_SELECTED_FILE(p_filename);
            showmap(); // add check to see if file is proper
            return ("Please use gameplayer -add command to add players in the game");
        } else {
            d_gameEngine.prevUserCommand = Commands.EDITMAP;
            // System.out.println("File not found.");
            d_gameEngine.gameMap.set_USER_SELECTED_FILE(p_filename);

            d_gameEngine.gameMap.createNewMapFile(p_filename);
            // System.out.println("New File created successfully..");
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
            return "Please select the add or remove command";

        }

    }

}
