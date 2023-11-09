package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.Commands;

/**
 * This class stores all the player-related commands allowed in gameplay
 */
@ShellComponent
public class PlayerCommands {

    public GameEngine d_gameEngine;
    public PlayerFeatures d_playerFeatures;
    public String d_prevUserCommand;
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

        d_gameEngine.getGamePhase().setPlayers(p_playerNameOne, p_playerNameTwo);
        return null;

    }

    /**
     * @return String returns status of assigncountries
     */
    @ShellMethod(key = "assigncountries", value = "This is used to assign countries to players randomly")
    public void assigncountries() {
        d_gameEngine.getGamePhase().assignCountries();
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
    public String deployArmies(@ShellOption int p_countryID, @ShellOption int p_armies) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot deploy armies at this stage. Please follow the sequence of commands in the game.";
        }
        return d_playerFeatures.deployArmies(d_gameEngine, p_countryID, p_armies);
    }



    /**
     * @param p_countryID storing country ID
     * @param p_armies    storing number of armies to deploy
     * @return returns status of deploying army
     */
    @ShellMethod(key = "advance", value = "This is used to deploy armies")
    public String advancearmies(@ShellOption int p_countryfrom,@ShellOption int p_countryTo, @ShellOption int p_armies) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot deploy armies at this stage. Please follow the sequence of commands in the game.";
        }
        return d_playerFeatures.advanceArmies(d_CurrentPlayerId,d_gameEngine, p_countryfrom,p_countryTo, p_armies);
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
                return d_playerFeatures.bombCountry(d_gameEngine,p_countryId);

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
        return d_playerFeatures.blockadeCountry(d_gameEngine,p_countryId);
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
        return d_playerFeatures.airlift(d_gameEngine,p_countryfrom,p_countryTo, p_airliftArmies);
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
        return d_playerFeatures.negotiate(d_gameEngine,p_targetPlayerId);
            }
            else{
                return "The Player does not have Negotiate card";
            }
        }
        return "Diplomacy executed successfully";
    }


}
