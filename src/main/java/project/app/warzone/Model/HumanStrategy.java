package project.app.warzone.Model;

import java.util.Collections;
import java.util.List;

public class HumanStrategy extends PlayerStrategy {


  
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

    
	 
     /** 
      * @return List<Country>
      */
     public List<Country> toDefend(){

        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        Collections.sort(listOfCountriesOwned);

        return listOfCountriesOwned;

    }


    
    /** 
     * @return List<Country>
     */
    public List<Country> toAttack(){


        return null;
    }

	
    /** 
     * @return List<OrderInterface>
     */
    public List<OrderInterface> createOrder(){

        System.out.println("Enter your order for deploying armies");
        return null;

    }	
    



	
    
}
