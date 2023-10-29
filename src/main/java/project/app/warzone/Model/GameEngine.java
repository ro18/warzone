package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.Commands;
import project.app.warzone.Features.MapFeatures;

/**
 * This class represents the main instance of the Warzone game.
 */
@Component
public class GameEngine {

    private Phase gamePhase; // current state of the GameEngine object

	
    
    public List<Player> d_playersList;              //storing player list  
    public Map gameMap;                             //storing gameMap
    public Commands prevUserCommand;                //storing user's previous command
    public MapFeatures mapFeatures;                 //storing mapFeatures
    public String d_mapFileName;                    //storing map file name
    /**
     * Initializing gameMap and d_playersList
     * 
     * @param gameMap           gameMap instance
     */
    public GameEngine(Map gameMap ){
        this.gameMap = gameMap; // this is required
        this.d_playersList = new ArrayList<>();
    } 
    public GameEngine(){
    }
    /*spring boot error 
    public GameEngine(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        this.gameMap= mapFeatures.readMap(l_mapFileName);
        this.d_playersList = new ArrayList<>();
    }
     */
    
    /**
	 * Method that allows the GameEngine object to change its state.  
	 * @param p_phase new state to be set for the GameEngine object.
	 */
	public void setPhase(Phase p_phase) {
		gamePhase = p_phase;
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());
	}
    /**
     * used for getting gamePhase
     * 
     * @return              returns gamePhase
     */ 
    public void getPhase(Phase p_phase) {
        this.gamePhase = p_phase;
        }


    /**
     * used for returning player list
     * 
     * @return              returns player list
     */
    public List<Player> getPlayers() {
        return d_playersList;
    }

    /**
     * used for returning game map
     * 
     * @return      returns gameMap
     */
    public Map getGameMap() {
        return gameMap;
    }

    /**
     * This method is used to execute orders all at once.
     * It does this by calling the next_order() method of each player and executing it in a loop until all orders are processed.
     */
    public void execute_orders() {
        List<Player> l_players = this.getPlayers();

        while (true) {
            int l_playersOrdersProcessed = 0;

            for (int i = 0; i < l_players.size(); i++) {
                Order order = l_players.get(i).next_order();
                if (order != null) {
                    l_playersOrdersProcessed++;
                    order.execute();
                }
            }

            if (l_playersOrdersProcessed == 0) {
                break;
            }
        }

    }

    public void start(){
        //include this function inside the main caller function that starts the application execution
        // Set the initial state, call all state methods in order
        System.out.println("G **************************************************************** Inside Start");
        setPhase(new Preload(this));
        setPhase(new Postload(this));

        //setPhase(new Play(this));
        //setPhase(new Playsetup(this));
        //setPhase(new Reinforcement(this));
        //setPhase(new Attack(this));
        //setPhase(new Fortification(this));

        // Can trigger State-dependent behavior by using
        // The methods defined in the State (Phase) object, e.g.
        //gamePhase.loadMap(p_filename);

        // Player states
        //gamePhase.attack();
        //gamePhase.fortify();
        //gamePhase.reinforce();
        //gamePhase.next();
    };

}
