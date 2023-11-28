package project.app.warzone.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.Attack;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.LogEntryBuffer;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.Reinforcement;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;
import project.app.warzone.Utilities.UserCommands;

/**
 * This class stores all the player-related commands allowed in gameplay
 */
@ShellComponent
public class PlayerCommands implements Observer {

    public GameEngine d_gameEngine;
    public PlayerFeatures d_playerFeatures;
    private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();
    public static int d_CurrentPlayerId = 0;

    /**
     * Constructor for Playercommands
     * 
     * @param p_gameEngine     storing gameEngine
     * @param p_playerFeatures storing playerFeatures
     */
    public PlayerCommands(GameEngine p_gameEngine, PlayerFeatures p_playerFeatures) {
        this.d_gameEngine = p_gameEngine;
        this.d_playerFeatures = p_playerFeatures;
    }

    /**
     * command for add player
     * 
     * @param p_playerNameOne storing player 1 name
     * @param p_playerNameTwo storing player 2 name
     * @return returns status of adding player
     */
    @ShellMethod(key = "gameplayer", prefix = "-", value = "Player can create or remove a player")
    public String gamePlayerAdd(
            @ShellOption(value = "a", defaultValue = ShellOption.NULL, arity = 10) String p_playerNameOne,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL, arity = 10) String p_playerNameTwo) {

        LogObject l_logObject = new LogObject();
        l_logEntryBuffer.addObserver(this);
        boolean isAdd = (p_playerNameOne != null && p_playerNameOne != "");
        l_logObject.d_command = "gameplayer -" +  (isAdd ? "add " + p_playerNameOne : "remove " + p_playerNameTwo);
        d_gameEngine.getGamePhase().setPlayers(p_playerNameOne, p_playerNameTwo);
        return null;

    }

    /**
     * @return String returns status of assigncountries
     */
    @ShellMethod(key = "assigncountries", value = "This is used to assign countries to players randomly")
    public void assigncountries() {
        // d_gameEngine.getGamePhase().assignCountries();
        d_gameEngine.getGamePhase().assignCountriesForDemo(); // Added to demonstrate different attacks during presentation

        showStats();
    }

    /**
     * @return String returns status of showstats
     */
    @ShellMethod(key = "showstats", value = "Displays players armies and other details")
    public void showStats() {
        d_gameEngine.getGamePhase().showstats();
    }

    /**
     * @return String returns status of showstats
     */
    @ShellMethod(key = "showmapstatus", value = "Displays map armies and other details")
    public void showMapStatus() {
        d_gameEngine.getGamePhase().showmapstatus();
    }

     /**
     * @param p_countryID               storing country ID
     * @param p_armies                  storing number of armies to deploy
     * @return                          returns status of deploying army
     */
    @ShellMethod(key = "deploy", value = "This is used to deploy armies")
    public void deployArmies(@ShellOption int p_countryID, @ShellOption int p_armies) {
        d_gameEngine.getGamePhase().reinforce(p_countryID, p_armies);
    }



    /**
     * @param p_countryID storing country ID
     * @param p_armies    storing number of armies to deploy
     * @return returns status of deploying army
     */
    @ShellMethod(key = "advance", value = "This is used to deploy armies")
    public void advancearmies(@ShellOption int p_countryfrom,@ShellOption int p_countryTo, @ShellOption int p_armies) {
        d_gameEngine.setPhase(new Attack(d_gameEngine));

        d_gameEngine.getGamePhase().advance(d_CurrentPlayerId,p_countryfrom,p_countryTo, p_armies);
        // return d_playerFeatures.advanceArmies(d_CurrentPlayerId,d_gameEngine, p_countryfrom,p_countryTo, p_armies);
        return "Advance order added successfully";

    }

    /**
     * @param p_countryfrom             storing  target country ID to bomb
     *
     * @return                          returns status 
     */
    @ShellMethod(key = "bomb", value = "This is used to play Bomb card")
    public String bombCountry(@ShellOption int p_countryId) {
        // @Prashant please add here check to see if player has bomb card ****
        
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("bomb")){

                l_player.d_cardsInCollection.remove(card);
                d_gameEngine.getGamePhase().bomb(p_countryId);

                // return d_playerFeatures.bombCountry(d_gameEngine,p_countryId);

            }
            else{
                return "The Player does not have BOMB card";
            }
        }
        return "Bomb attack order added successfully";

    }


    /**
     * @param p_countryfrom             storing  target country ID to blockade

     * @return                          returns status 
     */
    @ShellMethod(key = "blockade", value = "This is used to play Blockade card")
    public String blockade(@ShellOption int p_countryId) {
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);


        boolean found = false;
        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("blockade")){
                
               found = true;

                l_player.d_cardsInCollection.remove(card);

                d_gameEngine.getGamePhase().blockade(p_countryId);

        // return d_playerFeatures.blockadeCountry(d_gameEngine,p_countryId);
            }
           
        }
        if(found = false){
            return "Player does not have airlift card";
        }
        return "Blockade attack order added successfully";
    }


     
    /**
     * @param p_countryfrom     storing  source country ID to blockade
     * @param p_countryTo       storing  target country ID to blockade
     * @param p_airliftArmies   storing  armies to airlift
     * @return
     */
    @ShellMethod(key = "airlift", value = "This is used to play Airlift card")
    public String airlift(@ShellOption int p_countryfrom,@ShellOption int p_countryTo, @ShellOption int p_airliftArmies) {
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        boolean found = true;
        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("airlift")){
                l_player.d_cardsInCollection.remove(card);

                d_gameEngine.getGamePhase().airlift(p_countryfrom,p_countryTo, p_airliftArmies);
                found = true;
        // return d_playerFeatures.airlift(d_gameEngine,p_countryfrom,p_countryTo, p_airliftArmies);
        }
           
        }

        if(found = false){
            return "Player does not have airlift card";
        }
        return "Airlift Card executed successfully";
    }

    
    

    /**
     * @param p_targetPlayerId      storing  target player
     * @return
     */
    @ShellMethod(key = "negotiate", value = "This is used to play Negotiate card")
    public String negotiate(@ShellOption int p_targetPlayerId) {
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        boolean found = false;


        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("negotiate")){

                l_player.d_cardsInCollection.remove(card);


                d_gameEngine.getGamePhase().negotiate(p_targetPlayerId);
                found = true;

            }
            
        }

        if(found == false)
        {
           return "The Player does not have Negotiate card";
        }
        return "Negotiated Order Added successfully";
    }

    @ShellMethod(key = "savegame", value = "This is used to save the game to a file")
    public void saveGame(@ShellOption String p_fileName) {
        // Take log file and save it with the name p_fileName
        String l_logFileLocation = System.getProperty("logFileLocation");
        // String l_logFileLocationNew = l_logFileLocation.substring(0, l_logFileLocation.lastIndexOf("/")) + "/" + p_fileName + ".log";
        // save it in SavedGames folder
        String l_logFileLocationNew = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/SavedGames/" + p_fileName + ".log";

        try{
            File l_file = new File(l_logFileLocationNew);
            l_file.createNewFile();
            BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_logFileLocationNew, false));
            BufferedReader l_reader = new BufferedReader(new FileReader(l_logFileLocation));
            String l_line = l_reader.readLine();
            String l_command = "";
            while(l_line != null){
                if(l_line.matches("^\\d+.*")){
                    String[] l_split = l_line.split(" ");
                    // Get the last index of the split array
                    for(int i = 1; i < l_split.length; i++){
                        l_command += l_split[i] + " ";
                    }
                    System.out.println(l_command);
                    
                }
                if (l_line.matches("^Status.*")){
                    String[] l_split = l_line.split(" ");
                    if(l_split[1].equals("SUCCESS")){
                        l_writer.append(l_command);
                        l_writer.newLine();
                    }
                    l_command = "";
                }
                l_line = l_reader.readLine();
            }
            l_writer.close();
            l_reader.close();
        } catch (IOException e) {
            System.out.println("Error Reading file");
        }
    }

    @ShellMethod(key = "loadgame", value = "This is used to load the game from a file")
    public void loadGame(@ShellOption String p_fileName) {
        String l_logFileLocationNew = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/SavedGames/" + p_fileName + ".log";
        MapEditorCommands l_mapEditorCommands = new MapEditorCommands(null, d_gameEngine, d_playerFeatures, null);
        try{
            BufferedReader l_reader = new BufferedReader(new FileReader(l_logFileLocationNew));
            BufferedReader l_BufferedReader = new BufferedReader(new FileReader(l_logFileLocationNew));
            String l_line = l_reader.readLine();
            String l_command = l_BufferedReader.readLine();
            //check if the file is empty
            if(l_line == null){
                l_reader.close();
                l_BufferedReader.close();
                throw new IOException("File is empty");
            }
            while (l_line!= null) {
                String[] l_split = l_line.split(" ");
                switch(l_split[0]) {
                    case "N":
                        UserCommands.pushCommand("N");
                        break;
                    case "Y":
                        UserCommands.pushCommand("Y");
                        break;
                    default:
                        break;
                }
                l_line = l_reader.readLine();
                
            }
            l_reader.close();

            while(l_command != null){
                String[] l_split = l_command.split(" ");
                switch (l_split[0]) {
                    case "loadmap":
                        l_mapEditorCommands.loadMap(l_split[1]);
                        break;
                    case "showmap":
                        l_mapEditorCommands.showmap();
                        break;
                    case "editmap":
                        l_mapEditorCommands.editmap(l_split[1]);
                        break;
                    case "editcontinent":
                        l_mapEditorCommands.editcontinent(l_split[1], l_split[2]);
                        break;
                    case "editcountry":
                        l_mapEditorCommands.editcountry(l_split[1], l_split[2]);
                        break;
                    case "editneighbor":
                        l_mapEditorCommands.editNeighbor(l_split[1], l_split[2]);
                        break;
                    
                    case "showstats":
                        break;
                    case "showmapstatus":
                        break;
                    case "assigncountries":
                        assigncountries();
                        break;
                    case "gameplayer":
                        if(l_split[1].equals("-add")){
                            gamePlayerAdd(l_split[2], null);
                        } else {
                            gamePlayerAdd(null, l_split[2]);
                        }
                        break;
                    case "deploy":
                        deployArmies(Integer.parseInt(l_split[1]), Integer.parseInt(l_split[2]));
                        break;
                    case "advance":
                        advancearmies(Integer.parseInt(l_split[1]), Integer.parseInt(l_split[2]), Integer.parseInt(l_split[3]));
                        break;
                    case "bomb":
                        bombCountry(Integer.parseInt(l_split[1]));
                        break;
                    case "blockade":
                        blockade(Integer.parseInt(l_split[1]));
                        break;
                    case "airlift":
                        airlift(Integer.parseInt(l_split[1]), Integer.parseInt(l_split[2]), Integer.parseInt(l_split[3]));
                        break;
                    case "negotiate":
                        negotiate(Integer.parseInt(l_split[1]));
                        break;
                    default:
                        break;
                }


                l_command = l_BufferedReader.readLine();
            }
            l_BufferedReader.close();
        } catch (IOException e) {
            System.out.println("File error: " + e);
        }
    }

    /**
     * This method is used to update the log file
     * @param o is the observable object
     * @param arg is the object to be updated
     */
    public void update(Observable o, Object arg) {
        if(arg instanceof LogObject){
            LogObject l_logObject = (LogObject) arg;
            try {
                BufferedWriter l_writer = new BufferedWriter(new FileWriter(System.getProperty("logFileLocation"), true));
                l_writer.newLine();
                l_writer.append(LogObject.d_logLevel + " " + l_logObject.d_command + "\n" + "Time: " + l_logObject.d_timestamp + "\n" + "Status: " + l_logObject.d_statusCode + "\n" + "Description: " + l_logObject.d_message);
                // System.out.println( "Inside update method of MapEditorCommands");
                l_writer.newLine();
                l_writer.close();
            } catch (IOException e) {
                System.out.println("Error Reading file");
            }
        }
    }


}