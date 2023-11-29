package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CheaterStrategy extends PlayerStrategy{
    
          /**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
    GameEngine d_gameEngine;
	/**
	 * gameEngine
	 */
	Player d_player; 



    public CheaterStrategy(Player p_player,GameEngine p_gameEngine) {
		super(p_player,p_gameEngine); 
	}



    public List<Country> toAttack(){


        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        List<Country> allBorderCountries = new ArrayList<>(null);


        List<Node> nodeList = d_gameEngine.getGameMap().getNodes();

        List<Country> toAttackCountries = new ArrayList<>(null);

        for (Node n : nodeList) {

            for(Country c : listOfCountriesOwned){

                if (n.getData() == c) {

                    if (n.getBorders() != null && n.getBorders().size() > 0) {

                        List<Node> listOfBorders = n.getBorders();

                        for (Node border : listOfBorders) {

                            if(border.getData().getOwnerId() != d_player.getL_playerid() && border.getData().getOwnerId()!=0){ // only get countries that have enemy owners 
                                toAttackCountries.add(border.getData());

                            }

                            
                        }

                    }

                    
                }
            }
          
        }


        return toAttackCountries;

         
    }


    public List<Country> getCountriesWithEnemyNeighbour(){


        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        List<Country> allBorderCountries = new ArrayList<>(null);


        List<Node> nodeList = d_gameEngine.getGameMap().getNodes();

        List<Country> countriesWithEnemyNeighbours = new ArrayList<>(null);

        for (Node n : nodeList) {

            for(Country c : listOfCountriesOwned){

                if (n.getData() == c) {

                    if (n.getBorders() != null && n.getBorders().size() > 0) {

                        List<Node> listOfBorders = n.getBorders();

                        for (Node border : listOfBorders) {

                            if(border.getData().getOwnerId() != d_player.getL_playerid() && border.getData().getOwnerId()!=0){ // only get countries that have enemy owners 
                                countriesWithEnemyNeighbours.add(c );

                            }

                            
                        }

                    }

                    
                }
            }
          
        }


        return countriesWithEnemyNeighbours;

         
    }



	public List<OrderInterface> createOrder(){


        for(Country c : toAttack()){

            int l_countryToOwner = c.getOwnerId();

            Player player2 = d_gameEngine.getPlayers().get(l_countryToOwner-1);

            player2.removeTerritory(c);

            c.setOwnerId(d_player.getL_playerid());

            d_player.setTerritories(c);

            
        }


        for(Country c : getCountriesWithEnemyNeighbour()){

            
            c.setNumberOfArmies(c.getNumberOfArmies()*2);
        }






        return null;

    }



    @Override
    protected List<Country> toDefend() {

        return null;
        
    }	
    
}
