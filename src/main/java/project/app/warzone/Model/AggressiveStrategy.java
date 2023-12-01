package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AggressiveStrategy extends PlayerStrategy{

     /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
	// GameEngine d_gameEngine;
	// /**
	//  * 
	// */
	// Player d_player; 
    
    int initialGameRound = 0;



    public AggressiveStrategy(Player p_player,GameEngine d_gameEngine) {
		super(p_player,d_gameEngine); 
	}

    
    /** 
     * @return List<Country>
     */
    // This method will return the list of owned territories sorted from most to least territories on it

    public List<Country> toDefend(){

        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        Collections.sort(listOfCountriesOwned, Collections.reverseOrder());

        return listOfCountriesOwned;

    }

    




    
    /** 
     * @return List<Country>
     */
    // This method will return the list of territories to attack sorted from least to most armies on the territories

    public List<Country> toAttack(){


        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        List<Node> nodeList = d_gameEngine.getGameMap().getNodes();

        // List<Country> allBorderCountries = new ArrayList<>(null);
        List<Country> toAttackCountries = new ArrayList<>();

        for (Node n : nodeList) {

            for(Country c : listOfCountriesOwned){

                if (n.getData() == c) {

                    if (n.getBorders() != null && n.getBorders().size() > 0) {

                        List<Node> listOfBorders = n.getBorders();

                        for (Node border : listOfBorders) {

                            // if(border.getData().getNumberOfArmies() >=1){

                            if(border.getData().getOwnerId() != d_player.getL_playerid() && border.getData().getOwnerId() !=0)
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



    
	

	
    /** 
     * @return List<OrderInterface>
     */
    public List<OrderInterface> createOrder(){

        List<OrderInterface> listOfOrders = new ArrayList<OrderInterface>();

        Country targetCountry = toDefend().get(0);

        while(d_player.getReinforcementArmies() > 0){

            int armiesToDeploy = d_player.getReinforcementArmies();
            OrderInterface newDeployOrder = new ConcreteDeploy(armiesToDeploy,targetCountry);

            d_player.setReinforcementMap(d_player.getReinforcementArmies() - armiesToDeploy);
            // l_logObject.setStatus(true, "Armies deployed successfully.");
            // l_logEntryBuffer.notifyClasses(l_logObject);


            listOfOrders.add(newDeployOrder);

            System.out.println("Added Deploy Order for player:"+d_player.getL_playername()+" with strategy:"+d_player.getStrategy().getClass().getSimpleName());

            System.out.println("Armies:"+armiesToDeploy+" OnCountry:"+targetCountry.getCountryName());





        }

        int attackers = targetCountry.getNumberOfArmies() ;

        List<Country> toAttackCountries = toAttack();

        while(toAttackCountries!=null && toAttackCountries.size() > 0 && attackers > 0 ){


            // Get the country with the least army

            Country countryToAttack = toAttackCountries.get(0);

            // Random l_random = new Random();

            // int numberofArmies = (l_random.nextInt() % attackers) + 1;

            int l_countryToOwner = countryToAttack.getOwnerId();

            Player player2 = d_gameEngine.getPlayerFromID(l_countryToOwner);

            int armiesToAttack = attackers;

            OrderInterface newAdvanceOrdr = new ConcreteAdvance(d_player,player2,armiesToAttack,targetCountry,countryToAttack);

            attackers = attackers - armiesToAttack;

            listOfOrders.add(newAdvanceOrdr);

            System.out.println("Added Attack Order for player:"+d_player.getL_playername()+" with "+d_player.getStrategy().getClass().getSimpleName());

            System.out.println("Armies:"+armiesToAttack+" FromCountry:"+targetCountry.getCountryName()+" ToCountry:"+countryToAttack.getCountryName() );






        }

        d_player.pendingOrder=false;
        return listOfOrders;
    }	
	
	
}
