package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.component.StringInput;
import org.springframework.stereotype.Component;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;
/**
 * This class represents the main instance of the Warzone game.
 */
@Component
public class GameEngine implements Observer {

    private Phase gamePhase; // current state of the GameEngine object
    private LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

	
    
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
        d_LogEntryBuffer.addObserver(this);
    }
    
    /**
	 * Method that allows the GameEngine object to change its state.  
	 * @param p_phase new state to be set for the GameEngine object.
	 */
	public void setPhase(Phase p_phase) {
		this.gamePhase = p_phase;
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());

        LogObject l_logObject = new LogObject();
        l_logObject.d_command = "*New Phase*";
        l_logObject.setStatus(true, p_phase.getClass().getSimpleName() + " phase started");
        d_LogEntryBuffer.notifyClasses(l_logObject);
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
            
            String value ="";

            while(true){
                 value = this.lineReader.readLine("Do you want to add more orders? Y or N:\n");
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
