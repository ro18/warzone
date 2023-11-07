package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;

/**
 * This class stores all the player-related commands allowed in gameplay
 */
@ShellComponent
public class PlayerCommands {

    public GameEngine d_gameEngine;
    public PlayerFeatures d_playerFeatures;
    public String d_prevUserCommand;
    public static int d_CurrentPlayerId = 1;

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
    }

    /**
     * @return String returns status of showstats
     */
    @ShellMethod(key = "showstats", value = "Displays players armies and other details")
    public void showStats() {
        d_gameEngine.getGamePhase().showstats();
    }

    /**
     * @param p_countryID storing country ID
     * @param p_armies    storing number of armies to deploy
     * @return returns status of deploying army
     */
    @ShellMethod(key = "deploy", value = "This is used to deploy armies")
    public void deployArmies(@ShellOption int p_countryID, @ShellOption int p_armies) {
        d_gameEngine.getGamePhase().reinforce(p_countryID, p_armies);
    }
}
