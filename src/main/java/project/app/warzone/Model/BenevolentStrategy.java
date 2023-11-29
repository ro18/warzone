package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy extends PlayerStrategy{


      /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
    GameEngine d_gameEngine;
	/**
	 * 
	 */
	Player d_player; 


    // Returns a collection of territories that the player can defend from least reinforced to most reinforced
    public BenevolentStrategy(Player p_player,GameEngine p_gameEngine) {
		super(p_player,p_gameEngine); 
	}

    
	 public List<Country> toDefend(){

        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        Collections.sort(listOfCountriesOwned);

        return listOfCountriesOwned;

    }


    public List<Country> toAttack(){


        return null;
    }






    

	public List<OrderInterface> createOrder(){


        List<OrderInterface> listOfOrders = new ArrayList<>(null);

        Country targetCountry = toDefend().get(0);

        while(d_player.getReinforcementArmies() > 0){


            OrderInterface newDeployOrder = new ConcreteDeploy(d_player.getReinforcementArmies(),targetCountry);

            d_player.setReinforcementMap(d_player.getReinforcementArmies() - d_player.getReinforcementArmies());
            // l_logObject.setStatus(true, "Armies deployed successfully.");
            // l_logEntryBuffer.notifyClasses(l_logObject);


            listOfOrders.add(newDeployOrder);


            // This will advance armies from the players strongest territory over to their weakest one

            Random l_random = new Random();

            int numberofArmies = (l_random.nextInt() % d_player.getReinforcementArmies()) + 1;

            int l_countryToAdvance = toDefend().get(toDefend().size()).getOwnerId();

            Player player2 = d_gameEngine.getPlayers().get(l_countryToAdvance-1);


            OrderInterface newAdvanceOrdr = new ConcreteAdvance(d_player,player2,numberofArmies,targetCountry,toDefend().get(toDefend().size()));


            listOfOrders.add(newAdvanceOrdr);




        }

        return listOfOrders;

    }	
    
}
