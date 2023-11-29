package project.app.warzone.Model;

import java.util.List;

public abstract class HumanStrategy extends PlayerStrategy {


  
     /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
	GameEngine d_gameEngine;
	/**
	 * 
	 */
	Player d_player; 



    public HumanStrategy(Player p_player, GameEngine p_GameEngine) {
		super(p_player,p_GameEngine); 
	}

    
	

	public List<OrderInterface> createOrder(){


        return null;

    }	
    



	
    
}
