package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AggressiveStrategy extends PlayerStrategy{

     /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
	GameEngine d_gameEngine;
	/**
	 * 
	*/
	Player d_player; 
    
    int initialGameRound = 0;



    public AggressiveStrategy(Player p_player,GameEngine d_gameEngine) {
		super(p_player,d_gameEngine); 
	}

    // This method will return the list of owned territories sorted from most to least territories on it

    public List<Country> toDefend(){

        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        Collections.sort(listOfCountriesOwned, Collections.reverseOrder());

        return listOfCountriesOwned;

    }

    




    // This method will return the list of territories to attack sorted from least to most armies on the territories

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

                            // if(border.getData().getNumberOfArmies() >=1){

                            if(border.getData().getOwnerId() != d_player.getL_playerid())
                                toAttackCountries.add(border.getData());

                            }
                            // else{
                            //     allBorderCountries.add(border.getData());
                            // }

                        }

                    }

                    
                }
            }
          
        


        // if(toAttackCountries.size() == 0){
        //     toAttackCountries = allBorderCountries;
        // }

        // Collections.sort(toAttackCountries);

        return toAttackCountries;

         
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





        }



        int attackers = targetCountry.getNumberOfArmies() ;

        while(toAttack().size() > 0 && attackers > 0 ){


            // Get the country with the least army

            Country countryToAttack = toAttack().get(0);

            // Random l_random = new Random();

            // int numberofArmies = (l_random.nextInt() % attackers) + 1;

            int l_countryToOwner = countryToAttack.getOwnerId();

            Player player2 = d_gameEngine.getPlayers().get(l_countryToOwner-1);

            OrderInterface newAdvanceOrdr = new ConcreteAdvance(d_player,player2,attackers,targetCountry,countryToAttack);

            listOfOrders.add(newAdvanceOrdr);





        }


        return listOfOrders;
    }	
	
	
}
