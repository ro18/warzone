package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.PlayerFeatures;
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
    public PlayerCommands playerCommands;          //playerCommands object

    public int gameRound=1;
    /**
     * Initializing gameMap and d_playersList
     * 
     * @param gameMap           gameMap instance
     */
    public GameEngine(Map gameMap){
        this.gamePhase = new Preload(this);
        this.gameMap = gameMap; // this is required
        this.d_playersList = new ArrayList<>();
        this.playerCommands = new PlayerCommands(this, new PlayerFeatures());
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

    // public void checkPlayers(){

    //     if(PlayerCommands.d_CurrentPlayerId)
    // }

    public boolean checkPlayerStrategy(){

        List<Player> l_players = getPlayers();
        
        for(Player p : l_players){
            if(!p.getStrategy().getClass().getSimpleName().equalsIgnoreCase("HumanStrategy")){
                p.issue_order(p.getStrategy());
                return false;
                
            }
            else{
                return true;
                
            }
        }

        return false;

    }
    
    public String checkPlayersReinforcements(){


      



        Boolean l_flag = false;
        int l_i = PlayerCommands.d_CurrentPlayerId;

        List<Player> l_players = getPlayers();
        // List<Player> l_playersToRemove = new ArrayList<>();

        // for(Player p : l_players ){
        //     if(p.getListOfTerritories().size() == 0 ){
        //         System.out.println("Player: "+p.getL_playername()+" has lost the game");
        //         l_playersToRemove.add(p);
             
        //     }
        // }


        // l_players.removeAll(l_playersToRemove);

        int l_iterateThroughAllPlayers =  0;
        if( getGamePhase().getClass().getSimpleName() != new End(this).getClass().getSimpleName()){

           // while (l_i != PlayerCommands.d_CurrentPlayerId) {
            while (true) {

                if(l_iterateThroughAllPlayers == 2){
                    break;
                }

                if (l_i == l_players.size()) {
                    l_iterateThroughAllPlayers++;
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
                System.out.println("Turn of " + l_players.get(l_i).getL_playername()+" to place orders");

                // boolean humanPlayer = checkPlayerStrategy();


                    if(l_players.get(l_i).pendingOrder == true && l_players.get(l_i).getReinforcementArmies() == 0 ){
                    
                    String test = new HumanStrategy(null, null).getClass().getSimpleName();
                    // Human Behavior to ask for user commands
                    if(l_players.get(l_i).getStrategy().getClass().getSimpleName().equalsIgnoreCase(new HumanStrategy(null, null).getClass().getSimpleName())){

                        String value ="";

                        while(true){
                            value = this.lineReader.readLine("Your reinforcement is complete. Do you want to add more orders? Y or N:\n");
                            if(value.equalsIgnoreCase("N") ||  value.equalsIgnoreCase("Y")){
                                break;
                            }

                        }
                        if(value.equalsIgnoreCase("N")){
                            l_players.get(l_i).pendingOrder = false;
                            checkPlayersReinforcements();

                        }
                        else{
                            System.out.println("Please proceed with your orders");

                        }

                    
                    }
                }
                else{

                    if(l_players.get(l_i).getStrategy().getClass().getSimpleName().equalsIgnoreCase(new HumanStrategy(null, null).getClass().getSimpleName())){
                        System.out.println("Deploy your reinforcements");

                    }
                    else{

                        // Directly place orders through strategy pattern if not human
                        l_players.get(l_i).issue_order(l_players.get(l_i).getStrategy());
                        checkPlayersReinforcements();
                    }

                }
                    
                   


            }
            else{


                System.out.println(" ------- GAME ROUND: "+gameRound++ +" - Executing Orders from players - ");

                execute_orders();

                if(l_players.size() == 1 ){

                System.out.println("Player:"+ l_players.get(0).getL_playername()+" is the winner of the game");
                setPhase(new End(this));
                checkPlayersReinforcements();

                }
                else{

                    System.out.println("Orders successfully executed");

                    System.out.println("Run showstats to see the results");
        

                    for( Player p : l_players){
                        p.pendingOrder = true;
                        if(p.getL_playerid() == 1){
                        p.addReinforcementArmies(10); // add reinforcement armies of 2 after every level

                        }
                        p.addReinforcementArmies(2); // add reinforcement armies of 2 after every level
                    }

                    PlayerCommands.d_CurrentPlayerId = l_i;
                    System.out.println("Turn of " + l_players.get(l_i).getL_playername());  

                    if(!l_players.get(l_i).getStrategy().getClass().getSimpleName().equalsIgnoreCase("HumanStrategy")){
                        
                        playerCommands.showStats();
                        
                        checkPlayersReinforcements();
                    }


                }
                
    

            }

            

        }
        else{
            System.out.println(" GAME OVER");
        }
                
               
              
        return "";

       
        

    }
            // if(l_players.get(l_i).getStrategy().getClass().getSimpleName().equalsIgnoreCase("HumanStrategy")){



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


            List<Player> l_playersToRemove = new ArrayList<>();

            for(Player p : l_players ){
                if(p.getListOfTerritories().size() == 0 ){
                    System.out.println("Player:"+p.getL_playername()+" has lost the game");
                    l_playersToRemove.add(p);
                    
                }
            }


            l_players.removeAll(l_playersToRemove);

            
            // for(Player p : l_players ){
            //     if(p.getListOfTerritories().size() == 0 ){
            //         System.out.println("Player: "+p.getL_playername()+"has lost the game");
            //         l_players.remove(p);
                    
            //     }
            // }

            if (l_playersOrdersProcessed == 0) {
                break;
            }
        }



    }

    public void start(){
        
        System.out.println("G **************************************************************** Inside Start");
        Preload d_Preload = new Preload(this);
        setPhase(d_Preload);
       
    };

}
