package project.app.warzone.Commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.LogEntryBuffer;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;

/**
 * This class stores all the player-related commands allowed in gameplay
 */
@ShellComponent
public class PlayerCommands implements Observer {

    public GameEngine d_gameEngine;
    public PlayerFeatures d_playerFeatures;
    public String d_prevUserCommand;
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
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            System.out.println("You cannot deploy armies at this stage. Please follow the sequence of commands in the game.");
        }

        d_gameEngine.getGamePhase().reinforce(p_countryID, p_armies);
        // return d_playerFeatures.deployArmies(d_gameEngine, p_countryID, p_armies);
    }



    /**
     * @param p_countryID storing country ID
     * @param p_armies    storing number of armies to deploy
     * @return returns status of deploying army
     */
    @ShellMethod(key = "advance", value = "This is used to deploy armies")
    public void advancearmies(@ShellOption int p_countryfrom,@ShellOption int p_countryTo, @ShellOption int p_armies) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            System.out.println("You cannot deploy armies at this stage. Please follow the sequence of commands in the game.");


        }
        d_gameEngine.getGamePhase().advance(d_CurrentPlayerId,p_countryfrom,p_countryTo, p_armies);
        // return d_playerFeatures.advanceArmies(d_CurrentPlayerId,d_gameEngine, p_countryfrom,p_countryTo, p_armies);
    }

    /**
     * @param p_countryfrom             storing  target country ID to bomb
     *
     * @return                          returns status 
     */
    @ShellMethod(key = "bomb", value = "This is used to play Bomb card")
    public String bombCountry(@ShellOption int p_countryId) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot deploy armies at this stage. Please follow the sequence of commands in the game.";
        }
        // @Prashant please add here check to see if player has bomb card ****
        
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("bomb")){

                d_gameEngine.getGamePhase().bomb(p_countryId);

                // return d_playerFeatures.bombCountry(d_gameEngine,p_countryId);

            }
            else{
                return "The Player does not have BOMB card";
            }
        }
        return "Bomb attack executed successfully";

    }


    /**
     * @param p_countryfrom             storing  target country ID to blockade

     * @return                          returns status 
     */
    @ShellMethod(key = "blockade", value = "This is used to play Blockade card")
    public String blockade(@ShellOption int p_countryId) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot deploy armies at this stage. Please follow the sequence of commands in the game.";
        }
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("blockade")){
                d_gameEngine.getGamePhase().blockade(p_countryId);

        // return d_playerFeatures.blockadeCountry(d_gameEngine,p_countryId);
            }
            else{
                return "The Player does not have Blockade card";
            }
        }
        return "Blockade attack executed successfully";
    }


     /**
     * @param p_countryfrom             storing  target country ID to blockade

     * @return                          returns status 
     */
    @ShellMethod(key = "airlift", value = "This is used to play Airlift card")
    public String airlift(@ShellOption int p_countryfrom,@ShellOption int p_countryTo, @ShellOption int p_airliftArmies) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot airlift armies at this stage. Please follow the sequence of commands in the game.";
        }
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("airlift")){
                d_gameEngine.getGamePhase().airlift(p_countryfrom,p_countryTo, p_airliftArmies);

        // return d_playerFeatures.airlift(d_gameEngine,p_countryfrom,p_countryTo, p_airliftArmies);
        }
            else{
                return "The Player does not have Airlift card";
            }
        }
        return "Airlift Card executed successfully";
    }

    
    //negotiate function....need to check

    @ShellMethod(key = "negotiate", value = "This is used to play Negotiate card")
    public String negotiate(@ShellOption int p_targetPlayerId) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot negotiate armies at this stage. Please follow the sequence of commands in the game.";
        }
        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        for (Cards card : l_player.d_cardsInCollection) {
            if (card.getCardType().equalsIgnoreCase("negotiate")){

                d_gameEngine.getGamePhase().negotiate(p_targetPlayerId);

            }
            else{
                return "The Player does not have Negotiate card";
            }
        }
        return "Diplomacy executed successfully";
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
