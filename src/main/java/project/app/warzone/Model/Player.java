package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class is used to store the territories for the map
 */
public class Player 
{
	
		public int d_playerid; //Player id of the player to decide the round robin order to deploy
		public String d_playername; //Name of the player assigned by the  user
		public int d_reinforcementPool; //Total number of players available to deploy in each round
		
        public List<Country> d_listOfCountriesOwned; //List of countries owned by the player 
		public Stack<Order> d_listOfOrders; //stores orders issued by the player 
	


        public Player(int p_playerid ,String p_playername) {
            this.d_playerid = p_playerid;
            this.d_playername = p_playername;
            this.d_listOfCountriesOwned = new ArrayList<>();
			this.d_reinforcementPool=3;

        }

		
		
		/** 
		 * @return int
		 */
		public int getL_playerid() {
			return d_playerid;
		}

		
		/** 
		 * @param p_playerid
		 */
		public void setL_playerid(int p_playerid) {
			d_playerid = p_playerid;
		}
		
		/** 
		 * @return String
		 */
		public String getL_playername() {
			return d_playername;
		}


		
		/** 
		 * @param noOfArmies
		 */
		public void initReinforcementArmies(int noOfArmies){
			d_reinforcementPool= noOfArmies;
		}


		
		/** 
		 * @param noOfArmies
		 */
		public void addReinforcementArmies(int noOfArmies){
			d_reinforcementPool+= noOfArmies;
		}


		
		/** 
		 * @return int
		 */
		public int getReinforcementArmies(){
			return d_reinforcementPool;
		}


		
		/** 
		 * @param l_playername
		 */
		public void setL_playername(String l_playername) {
			d_playername = l_playername;
		}


		
		/** 
		 * @param country
		 */
		public void setTerritories(Country country){
			d_listOfCountriesOwned.add(country);
		}


		
		/** 
		 * @return List<Territory>
		 */
		public List<Country> getListOfTerritories(){
			return d_listOfCountriesOwned;
		}



		
		/** 
		 * @param order
		 */
		public void issue_order(Order order) {
			d_listOfOrders.push(order);
		}


		public void clear_orderList(){
			d_listOfOrders.clear();
		}
		
		
		/** 
		 * @return Order
		 */
		public Order next_order() {
			return d_listOfOrders.pop();
			
			
		}
		

        
		 

		
}