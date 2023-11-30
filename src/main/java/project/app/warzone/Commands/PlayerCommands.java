package project.app.warzone.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.Attack;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.LogEntryBuffer;
import project.app.warzone.Model.Map;
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


    @Autowired
    @Lazy
    private LineReader lineReader;

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
     * @param p_playerToAdd storing player to add
     * @param p_playerToRemove player to remove
     * @return returns status of adding player
     */
    @ShellMethod(key = "gameplayer", prefix = "-", value = "Player can create or remove a player")
    public String gamePlayerAdd(
            @ShellOption(value = "a", defaultValue = ShellOption.NULL, arity = 10) String p_playerToAdd,
            @ShellOption(value = "r", defaultValue = ShellOption.NULL, arity = 10) String p_playerToRemove) {
        LogObject l_logObject = new LogObject();
        l_logEntryBuffer.addObserver(this);
        boolean isAdd = (p_playerToAdd != null && p_playerToAdd != "");
        l_logObject.d_command = "gameplayer -" +  (isAdd ? "add " + p_playerToAdd : "remove " + p_playerToRemove);
        d_gameEngine.getGamePhase().setPlayers(p_playerToAdd, p_playerToRemove);

        if(d_gameEngine.d_playersList.size() >= 2){
            String value = "";
            if(UserCommands.checkSize("playerAdd") > 0){
                value = UserCommands.popCommand("playerAdd");

                l_logObject.d_command = value + "_players";
                l_logObject.setStatus(true, "Command " + value + " was executed");
                l_logEntryBuffer.notifyClasses(l_logObject);
            } else {
                while(true){
                    value = this.lineReader.readLine("Do you want to add more players?:\n");

                    if(value.equalsIgnoreCase("N") ||  value.equalsIgnoreCase("Y")){
                        l_logObject.setD_command(value + "_players");
                        l_logObject.setStatus(true, "User chose the command " + value);
                        l_logEntryBuffer.notifyClasses(l_logObject);
                        break;
                    }

                }
            }
        
            if(value.equalsIgnoreCase("Y")){

                System.out.println("Please proceed to add more players");

            }
            else{
                System.out.println("Please choose strategy for players:");
                d_gameEngine.getGamePhase().setPlayerStrategy();
            }
        }

        
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

        d_gameEngine.checkPlayersReinforcements();
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
    public String advancearmies(@ShellOption int p_countryfrom,@ShellOption int p_countryTo, @ShellOption int p_armies) {
        d_gameEngine.setPhase(new Attack(d_gameEngine));

        d_gameEngine.getGamePhase().advance(d_CurrentPlayerId,p_countryfrom,p_countryTo, p_armies);
        // return d_playerFeatures.advanceArmies(d_CurrentPlayerId,d_gameEngine, p_countryfrom,p_countryTo, p_armies);
        // return "Advance order added successfully";
        return "";

    }

    /**
     * @param p_countryfrom             storing  target country ID to bomb
     
     * @return                          returns status 
     */
    @ShellMethod(key = "bomb", value = "This is used to play Bomb card")
    public String bombCountry(@ShellOption int p_countryId) {
        // @Prashant please add here check to see if player has bomb card ****
        
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        List<Cards> cardsToRemove = new ArrayList<Cards>();

        boolean found = false;


        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("bomb") && found == false){

                found = true;
                cardsToRemove.add(card);
                d_gameEngine.getGamePhase().bomb(p_countryId);
                break;

            }
            
        }
        if(found == false){
                return "The Player does not have BOMB card";
        }
        else{
            l_player.d_cardsInCollection.removeAll(cardsToRemove);

        }
        return "";

    }


    /**
     * @param p_countryfrom             storing  target country ID to blockade

     * @return                          returns status 
     */
    @ShellMethod(key = "blockade", value = "This is used to play Blockade card")
    public String blockade(@ShellOption int p_countryId) {
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);


        boolean found = false;

        List<Cards> cardsToRemove = new ArrayList<>();

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("blockade") && found == false){
                
                found = true;

                cardsToRemove.add(card);

                d_gameEngine.getGamePhase().blockade(p_countryId);

                break;

            }
           
        }
        if(found == false){
            return "Player does not have airlift card";
        }
        else{
            l_player.d_cardsInCollection.removeAll(cardsToRemove);

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

        boolean found = false;
        List<Cards> cardsToRemove = new ArrayList<>();

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("airlift") && found == false){

                cardsToRemove.add(card);
                d_gameEngine.getGamePhase().airlift(p_countryfrom,p_countryTo, p_airliftArmies);
                found = true;
                break;
            }
           
        }

        if(found = false){
            return "Player does not have airlift card";
        }
        else{
            l_player.d_cardsInCollection.removeAll(cardsToRemove);

        }
        return "";
    }

    
    

    /**
     * @param p_targetPlayerId      storing  target player
     * @return
     */
    @ShellMethod(key = "negotiate", value = "This is used to play Negotiate card")
    public String negotiate(@ShellOption int p_targetPlayerId) {
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        boolean found = false;
        List<Cards> cardsToRemove = new ArrayList<>();


        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("negotiate") && found == false){


                cardsToRemove.add(card);

                d_gameEngine.getGamePhase().negotiate(p_targetPlayerId);
                found = true;
                break;

            }
            
        }

        if(found == false)
        {
           return "The Player does not have Negotiate card";
        }
        else{
            l_player.d_cardsInCollection.removeAll(cardsToRemove);

        }
        return "Negotiated Order Added successfully";
    }

    /**
     * @param p_fileName                storing file name
     */
    @ShellMethod(key = "savegame", value = "This is used to save the game to a file")
    public void saveGame(@ShellOption String p_fileName) {
        
        if(GameEngine.isTournament){
            // Make a tournament folder in SavedGames folder and save the game there
            String l_logFileLocation = System.getProperty("logFileLocation");
            String l_logFileLocationNew = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/SavedGames/Tournament/" + p_fileName + ".log";
            try{
                String[] l_temp = p_fileName.split("_");
                File l_file = new File(l_logFileLocationNew);
                l_file.createNewFile();
                BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_logFileLocationNew, false));
                l_writer.append("Tournament Mode Game").append("\n");
                l_writer.append("Map File: " + l_temp[1] + ".map").append("\n");
                l_writer.append("Game number: " + l_temp[2]).append("\n\n");

                
                BufferedReader l_reader = new BufferedReader(new FileReader(l_logFileLocation));
                String l_line = l_reader.readLine();
                while(l_line != null){
                    l_writer.append(l_line);
                    l_writer.newLine();
                    l_line = l_reader.readLine();
                }
                l_writer.append("\n\n-------------------Game Over-----------------\n");
                l_writer.append("Results:\n");
                if(d_gameEngine.getPlayers().size() == 1){
                    l_writer.append("Winner: " + d_gameEngine.getPlayers().get(0).getL_playername());
                } else {
                    l_writer.append("Draw");
                }
                l_writer.close();
                l_reader.close();
            } catch (IOException e) {
                System.out.println("Error Reading file" + e.getMessage());
            }
            return;
        }
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

    /**
     * @param p_fileName                storing file name
     * @return                          returns status of loading game
     */

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
                        UserCommands.pushCommand("N", "orders");
                        break;
                    case "Y":
                        UserCommands.pushCommand("Y", "orders");
                        break;
                    case "Y_players":
                        UserCommands.pushCommand("Y", "playerAdd");
                        break;
                    case "N_players":
                        UserCommands.pushCommand("N", "playerAdd");
                        break;
                    case "1_strategy":
                        UserCommands.pushCommand("1", "strategy");
                        break;
                    case "2_strategy":
                        UserCommands.pushCommand("2", "strategy");
                        break;
                    case "3_strategy":
                        UserCommands.pushCommand("3", "strategy");
                        break;
                    case "4_strategy":
                        UserCommands.pushCommand("4", "strategy");
                        break;
                    case "5_strategy":
                        UserCommands.pushCommand("5", "strategy");
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
     * @param p_countryID               storing country ID
     * @param p_armies                  storing number of armies to deploy
     * @return                          returns status of deploying army
     */
    @ShellMethod(key = "tournament", prefix = "-", value = "This is used to play tournament")
    public void tournament(@ShellOption(value = "M", defaultValue = ShellOption.NULL, arity = 5) String p_mapFiles,
            @ShellOption(value = "P", defaultValue = ShellOption.NULL, arity = 4) String p_playerStrategies,
            @ShellOption(value = "G", defaultValue = ShellOption.NULL, arity = 1) String p_numberOfGames,
            @ShellOption(value = "D", defaultValue = ShellOption.NULL, arity = 1) String p_maxNumberOfTurns) {
            
                System.out.println("Tournament started");
                System.out.println("Map Files: " + p_mapFiles);
                System.out.println("Player Strategies: " + p_playerStrategies);
                System.out.println("Number of Games: " + p_numberOfGames);
                System.out.println("Max Number of Turns: " + p_maxNumberOfTurns);

                d_gameEngine.setTournament(true);
                d_gameEngine.setMaxTurns(Integer.parseInt(p_maxNumberOfTurns));

                for (int i = 0; i < p_mapFiles.split(",").length; i++) {
                    for (int j = 0; j < Integer.parseInt(p_numberOfGames); j++) {

                        MapEditorCommands l_mapEditorCommands = new MapEditorCommands(null, d_gameEngine, d_playerFeatures, null);
                        l_mapEditorCommands.loadMap(p_mapFiles.split(",")[i]);
                        l_mapEditorCommands.showmap();
                        for(int k = 0; k < (p_playerStrategies.split(",").length); k++){
                            UserCommands.pushCommand(p_playerStrategies.split(",")[k], "strategy");
                            if(k != 0) {
                                if(k <= p_playerStrategies.split(",").length - 1){
                                    UserCommands.pushCommand("N", "playerAdd");
                                } else {
                                    UserCommands.pushCommand("Y", "playerAdd");
                                }
                            }
                        }
                        for(int k = 1; k <= p_playerStrategies.split(",").length; k++){
                            gamePlayerAdd(("Player_" + k), null);
                        }
                        assigncountries();

                        //save the game state
                        String l_fileName = "tournament_" + p_mapFiles.split(",")[i] + "_" + (j + 1);
                        saveGame(l_fileName);
                        flush();
                    }
                }

                System.out.println("Tournament ended");
            }
    
    private void flush(){
        GameEngine l_gameEngine = new GameEngine(new Map());
        d_gameEngine = l_gameEngine;
        PlayerCommands.d_CurrentPlayerId = 0;


        // // Reset every object and log file used:
        // d_gameEngine.getPlayers().clear();
        

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