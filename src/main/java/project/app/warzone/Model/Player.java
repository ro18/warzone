package project.app.warzone.Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * This class represents a player in the Warzone game.
 */
public class Player 
{
	
		public int d_playerid;
		public String d_playername;
		public int d_reinforcementPool;
		
        public List<Territory> d_listOfCountriesOwned;
		public Queue<Order> d_listOfOrders = new ArrayDeque<> ();
	

	/**
     * Creates a new player with the given ID.
     * @param p_playerid The ID of the player.
	 * @param p_playername The name of the player.
     */
        public Player(int p_playerid ,String p_playername) {
            this.d_playerid = p_playerid;
            this.d_playername = p_playername;
            this.d_listOfCountriesOwned = new ArrayList<>();
			this.d_reinforcementPool=3;

        }

		
		public int getL_playerid() {
			return d_playerid;
		}
		public void setL_playerid(int p_playerid) {
			d_playerid = p_playerid;
		}
		public String getL_playername() {
			return d_playername;
		}
		public void initReinforcementArmies(int noOfArmies){
			d_reinforcementPool= noOfArmies;
		}
		public void addReinforcementArmies(int noOfArmies){
			d_reinforcementPool+= noOfArmies;
		}
		public int getReinforcementArmies(){
			return d_reinforcementPool;
		}
		public void setL_playername(String l_playername) {
			d_playername = l_playername;
		}

		public void setReinforcementMap(int noOfArmies){
            this.d_reinforcementPool= noOfArmies;
        }

		public int getReinforcementMap(){
			return d_reinforcementPool;
		}

		public List<Territory> getlistOfCountriesOwned() {
			return d_listOfCountriesOwned;
		}

		public void setTerritories(Territory territory){
			d_listOfCountriesOwned.add(territory);
		}

		public List<Territory> getListOfTerritories(){
			return d_listOfCountriesOwned;
		}
		
		/**
		 * This method adds the order to the list of orders of the player
		 */

		public void issue_order(Order order) {
			d_listOfOrders.add(order);
			this.setReinforcementMap(this.getReinforcementArmies() - order.getL_numberOfArmies());
		}

		/**
		 * This method pops and returns the first order from the list of orders
		 * @return The latest Order Object
		 */
		
		public Order next_order() {
			if(d_listOfOrders.size()>0)
				return d_listOfOrders.remove();
			return null;
		}
		

        
		 

		
}