package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CheaterStrategy extends PlayerStrategy{


    public CheaterStrategy(Player p_player,GameEngine p_gameEngine) {
		super(p_player,p_gameEngine); 
	}



    public List<Country> toAttack(){


        List<Country> listOfCountriesOwned = d_player.getlistOfCountriesOwned();

        List<Node> nodeList = d_gameEngine.getGameMap().getNodes();

        List<Country> toAttackCountries = new ArrayList<>();

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

        List<Node> nodeList = d_gameEngine.getGameMap().getNodes();

        List<Country> countriesWithEnemyNeighbours = new ArrayList<>();

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

        d_player.pendingOrder=false;
        return countriesWithEnemyNeighbours;

         
    }



	public List<OrderInterface> createOrder(){

        List<Country> countriesToAttack = toAttack();


        d_player.setReinforcementMap(0);


        // Cheater takes in all the immediate enemy territories 

        for(Country c : countriesToAttack){

            int l_countryToOwner = c.getOwnerId();

            Player player2 = d_gameEngine.getPlayerFromID(l_countryToOwner);

            player2.removeTerritory(c);

            c.setOwnerId(d_player.getL_playerid());

            d_player.setTerritories(c);

            
        }


        // Cheater takes in all the immediate enemy territories 

        List<Country> getCountriesWithEnemyNeighbours = getCountriesWithEnemyNeighbour();

        for(Country c : getCountriesWithEnemyNeighbours){
            
            c.setNumberOfArmies(c.getNumberOfArmies()*2);
        }





        d_player.pendingOrder=false;

        return null;

    }



    @Override
    protected List<Country> toDefend() {

        return null;
        
    }	
    
}
