package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.stringtemplate.v4.compiler.STParser.listElement_return;

public class RandomStrategy extends PlayerStrategy {


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

        List<Country> allBorderCountries = new ArrayList<>();
        List<Country> toAttackCountries = new ArrayList<>();

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

        List<OrderInterface> listOfOrders = new ArrayList<>();

        Random l_random = new Random();

        Country randomCountryToDefend = toDefend().get(l_random.nextInt(toDefend().size()));

        // Country targetCountry = toDefend().get(r);

        while(d_player.getReinforcementArmies() > 0){

            int armiesToDeploy = d_player.getReinforcementArmies();


            OrderInterface newDeployOrder = new ConcreteDeploy(armiesToDeploy,randomCountryToDefend);

            d_player.setReinforcementMap(d_player.getReinforcementArmies() - d_player.getReinforcementArmies());
            // l_logObject.setStatus(true, "Armies deployed successfully.");
            // l_logEntryBuffer.notifyClasses(l_logObject);


            listOfOrders.add(newDeployOrder);

            System.out.println("Added Deploy Order for player:"+d_player.getL_playername()+" with strategy:"+d_player.getStrategy().getClass().getSimpleName());

            System.out.println("Armies:"+armiesToDeploy+" OnCountry:"+randomCountryToDefend.getCountryName() );



        }


        List<Country> countriesToAttack = toAttack();
        List<Country> countriesToAttackFrom = toDefend();

        if(toAttack().size() > 0 && toDefend().size()>0){

            Country randomCountryToAttack = countriesToAttack.get(l_random.nextInt(toAttack().size()));
            Country randomCountryToFrom =countriesToAttackFrom.get(l_random.nextInt(toDefend().size()));

            int attackers = randomCountryToFrom.getNumberOfArmies() ;

            if(countriesToAttack.size() > 0 && attackers > 0 ){


                int numberofArmies = (l_random.nextInt() % attackers) + 1;

                int l_countryToOwner = randomCountryToAttack.getOwnerId();

                Player player2 = d_gameEngine.getPlayerFromID(l_countryToOwner);

                OrderInterface newAdvanceOrdr = new ConcreteAdvance(d_player,player2,numberofArmies,randomCountryToFrom,randomCountryToAttack);

                listOfOrders.add(newAdvanceOrdr);

                System.out.println("Added Attack Order for player:"+d_player.getL_playername()+" with strategy:"+d_player.getStrategy().getClass().getSimpleName());

                System.out.println("Armies:"+attackers+" FromCountry:"+randomCountryToFrom.getCountryName()+" ToCountry:"+randomCountryToAttack.getCountryName() );





            }

        }

        


        // Advance randomly to a different country

         if(toAttack().size() > 0 && toDefend().size()>0){

            Country randomCountryToMoveFrom = toDefend().get(l_random.nextInt(toAttack().size()));
            Country randomCountryToMoveTo = toDefend().get(l_random.nextInt(toDefend().size()));


            if(randomCountryToMoveFrom!=null && randomCountryToMoveTo !=null && randomCountryToMoveFrom.getNumberOfArmies()>0 ){

                int numberofArmies = (l_random.nextInt() % randomCountryToMoveFrom.getNumberOfArmies()) + 1;

                    
                OrderInterface newMoveOrder = new ConcreteAdvance(d_player,null,numberofArmies,randomCountryToMoveFrom,randomCountryToMoveTo);


                listOfOrders.add(newMoveOrder);

                System.out.println("Added Advance Order for player:"+d_player.getL_playername()+" with strategy:"+d_player.getStrategy().getClass().getSimpleName());

                System.out.println("Armies:"+numberofArmies+" FromCountry:"+randomCountryToMoveFrom.getCountryName()+" ToCountry:"+randomCountryToMoveTo.getCountryName() );



            }
        
        }
    




        d_player.pendingOrder=false;
        return listOfOrders;
    }	
	
    
}
