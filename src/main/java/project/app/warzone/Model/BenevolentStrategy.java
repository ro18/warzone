package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy extends PlayerStrategy{


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


        List<OrderInterface> listOfOrders = new ArrayList<>();

        if(toDefend().size()>0){

            Country targetCountry = toDefend().get(0);

            while(d_player.getReinforcementArmies() > 0){

            int armiesToDeploy = d_player.getReinforcementArmies();

            OrderInterface newDeployOrder = new ConcreteDeploy(d_player.getReinforcementArmies(),targetCountry);

            d_player.setReinforcementMap(d_player.getReinforcementArmies() - armiesToDeploy);
            // l_logObject.setStatus(true, "Armies deployed successfully.");
            // l_logEntryBuffer.notifyClasses(l_logObject);


            listOfOrders.add(newDeployOrder);
            System.out.println("Added Deploy Order for player:"+d_player.getL_playername()+" with strategy:"+d_player.getStrategy().getClass().getSimpleName());

            System.out.println("Armies:"+armiesToDeploy+" OnCountry:"+targetCountry.getCountryName());



        }

        // This will advance armies from the players strongest territory over to their weakest one


        int movers = toDefend().get(toDefend().size()-1).getNumberOfArmies() ;


        while(movers > 0 && toDefend()!= null && toDefend().size() > 0  && toDefend().size()>1){


                
            // Random l_random = new Random();

            // int numberofArmies = (l_random.nextInt() % movers) + 1;

            int numberofArmies = movers;

            Country l_countryToAdvance = toDefend().get(toDefend().size()-1);
            // int l_countryToAdvanceId = l_countryToAdvance.getOwnerId();

            Player player2 = null;


            OrderInterface newAdvanceOrdr = new ConcreteAdvance(d_player,player2,numberofArmies,targetCountry,l_countryToAdvance);


            listOfOrders.add(newAdvanceOrdr);

            System.out.println("Move Order for player:"+d_player.getL_playername()+" with strategy:"+d_player.getStrategy().getClass().getSimpleName());

            System.out.println("Armies:"+numberofArmies+" FromCountry:"+targetCountry.getCountryName()+" ToCountry:"+l_countryToAdvance.getCountryName() );

            movers -= numberofArmies;


        }



        
        d_player.pendingOrder=false;
        return listOfOrders;
            
        }

        return null;
        

    }	
    
}
