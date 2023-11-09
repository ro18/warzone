package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.component.StringInput;
import org.springframework.stereotype.Component;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Utilities.Commands;
/**
 * This class represents the main instance of the Warzone game.
 */
@Component
public class GameEngine {

    private Phase gamePhase; // current state of the GameEngine object

	
    
    @Autowired
    @Lazy
    private LineReader lineReader;
    public List<Player> d_playersList;              //storing player list  
    public Map gameMap;                             //storing gameMap
    public Commands prevUserCommand;                //storing user's previous command
    /**
     * Initializing gameMap and d_playersList
     * 
     * @param gameMap           gameMap instance
     */
    public GameEngine(Map gameMap ){
        this.gamePhase = new Preload(this);
        this.gameMap = gameMap; // this is required
        this.d_playersList = new ArrayList<>();
    }
    
    /**
	 * Method that allows the GameEngine object to change its state.  
	 * @param p_phase new state to be set for the GameEngine object.
	 */
	public void setPhase(Phase p_phase) {
		this.gamePhase = p_phase;
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());
	}
    /**
     * used for getting game phase
     * @return
     */
    public Phase getGamePhase() {
        return this.gamePhase;
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

    
    public void checkPlayersReinforcements(){


        Boolean l_flag = false;
        int l_i = PlayerCommands.d_CurrentPlayerId+1;

        List<Player> l_players = getPlayers();

         while (l_i != PlayerCommands.d_CurrentPlayerId) {
            if (l_i == l_players.size()) {
                l_i = 0;
                continue;
            }

            if (l_players.get(l_i).getReinforcementArmies() > 0 || l_players.get(l_i).pendingOrder == true ) {
                l_flag = true;
                break;
            }
            l_i++;
        }
        

        if (l_flag) {
            PlayerCommands.d_CurrentPlayerId = l_i;
            System.out.println("Turn of " + l_players.get(l_i).getL_playername());

            if(l_players.get(l_i).pendingOrder == true && l_players.get(l_i).getReinforcementArmies() == 0 ){

            String value = this.lineReader.readLine("Do you want to add more orders? Y or N:\n");
            if(value.equals("N")){
                l_players.get(l_i).pendingOrder = false;
                checkPlayersReinforcements();

            }
            else{
                System.out.println("Please proceed with your orders");
            }

            }
           



        } else {

               execute_orders();
               System.out.println("Orders successfully executed");

               System.out.println("Run showstats to see the results");


                PlayerCommands.d_CurrentPlayerId = l_i;
                System.out.println("Turn of " + l_players.get(l_i).getL_playername());
                

                for( Player p : l_players){
                    p.pendingOrder = true;

                    p.addReinforcementArmies(2); // add reinforcement armies of 2 after every level
                }

        }

      


       
        

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
                


                var obj = l_players.get(i).next_order();
                if (obj!= null) {
                    l_playersOrdersProcessed++;
                    // System.out.print("before execute");
                    obj.execute();
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
        Preload d_Preload = new Preload(this);
        setPhase(d_Preload);
        // d_Preload.loadMap();
        // setPhase(new Postload(this));

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
