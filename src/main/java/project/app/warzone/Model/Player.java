package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player 
{
	
		public int d_playerid;
		public String d_playername;
		public int d_reinforcementPool;
		
        public List<Territory> d_listOfCountriesOwned;
		public Stack<Order> d_listOfOrders;
	


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

		public void issue_order(Order order) {
			d_listOfOrders.push(order);
		}

		public void clear_orderList(){
			d_listOfOrders.clear();
		}
		
		public Order next_order() {
			return d_listOfOrders.pop();
			
			
		}
		

        
		 

		
}