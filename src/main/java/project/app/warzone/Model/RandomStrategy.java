package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.stringtemplate.v4.compiler.STParser.listElement_return;

public class RandomStrategy extends PlayerStrategy {


    /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
	GameEngine d_gameEngine;
	/**
	 * 
	 */
	Player d_player; 



    public RandomStrategy(Player p_player,GameEngine p_gameEngine) {
		super(p_player,p_gameEngine); 
	}



    public List<Country> toDefend(){

        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        return listOfCountriesOwned;

    }

   





    // This method will return the list of territories to attack 

    public List<Country> toAttack(){


        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        List<Node> nodeList = d_gameEngine.getGameMap().getNodes();

        List<Country> allBorderCountries = new ArrayList<>(null);
        List<Country> toAttackCountries = new ArrayList<>(null);

        for (Node n : nodeList) {

            for(Country c : listOfCountriesOwned){

                if (n.getData() == c) {

                    if (n.getBorders() != null && n.getBorders().size() > 0) {

                        List<Node> listOfBorders = n.getBorders();

                        for (Node border : listOfBorders) {

                            if(border.getData().getOwnerId() != d_player.getL_playerid() && border.getData().getOwnerId()!=0 ){
                                toAttackCountries.add(border.getData());

                            }
                           
                            
                           

                        }

                    }

                    
                }
            }
          
        }

       
        return toAttackCountries;
         
    
    }

    
	

	public List<OrderInterface> createOrder(){

        List<OrderInterface> listOfOrders = new ArrayList<>(null);

        Random l_random = new Random();

        Country randomCountryToDefend = toDefend().get(l_random.nextInt(toDefend().size()));

        // Country targetCountry = toDefend().get(r);

        while(d_player.getReinforcementArmies() > 0){


            OrderInterface newDeployOrder = new ConcreteDeploy(d_player.getReinforcementArmies(),randomCountryToDefend);

            d_player.setReinforcementMap(d_player.getReinforcementArmies() - d_player.getReinforcementArmies());
            // l_logObject.setStatus(true, "Armies deployed successfully.");
            // l_logEntryBuffer.notifyClasses(l_logObject);


            listOfOrders.add(newDeployOrder);





        }


        Country randomCountryToAttack = toAttack().get(l_random.nextInt(toAttack().size()));
        Country randomCountryToFrom = toDefend().get(l_random.nextInt(toDefend().size()));

        int attackers = randomCountryToFrom.getNumberOfArmies() ;

        while(toAttack().size() > 0 && attackers > 0 ){


            // Get the country with the least army

            Country countryToAttack = toAttack().get(0);

            // Random l_random = new Random();

            // int numberofArmies = (l_random.nextInt() % attackers) + 1;

            int l_countryToOwner = countryToAttack.getOwnerId();

            Player player2 = d_gameEngine.getPlayers().get(l_countryToOwner-1);

            OrderInterface newAdvanceOrdr = new ConcreteAdvance(d_player,player2,attackers,randomCountryToFrom,randomCountryToAttack);

            listOfOrders.add(newAdvanceOrdr);





        }


        Country randomCountryToMoveFrom = toDefend().get(l_random.nextInt(toAttack().size()));
        Country randomCountryToMoveTo = toDefend().get(l_random.nextInt(toDefend().size()));

        OrderInterface newMoveOrder = new ConcreteAdvance(d_player,null,attackers,randomCountryToMoveFrom,randomCountryToMoveTo);


        listOfOrders.add(newMoveOrder);


        return listOfOrders;
    }	
	
    
}
