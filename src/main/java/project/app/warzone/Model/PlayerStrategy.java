package project.app.warzone.Model;

import java.util.List;

public abstract class PlayerStrategy {


    /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
	GameEngine d_gameEngine;
	/**
	 * 
	 */
	Player d_player; 
	
	protected abstract List<Country> toAttack(); 
	//protected abstract Country toAttackFrom(); 
	// protected abstract List<Country> toMoveFrom(); 
	protected abstract List<Country> toDefend();
	public abstract List<OrderInterface> createOrder();	
	/**
	 * 
	 * @param p_player	player
	 * @param p_gameEngine	gameengine
	 */
	PlayerStrategy(Player p_player,GameEngine p_gameEngine){
		d_player = p_player; 
		d_gameEngine = p_gameEngine; 
	}
    
}
