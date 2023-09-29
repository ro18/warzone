package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

public class Player 
{
	
		public int l_playerid;
		public String l_playername;
		public int d_reinforcementPool;
        public List<Territory> listOfCountriesOwned;

        public Player(int l_playerid ,String l_playername,List<Territory> listOfCountriesOwned) {
            this.l_playerid = l_playerid;
            this.l_playername = l_playername;
            this.listOfCountriesOwned = new ArrayList<>();
            this.d_reinforcementPool=0;
        }

		
		public int getL_playerid() {
			return l_playerid;
		}
		public void setL_playerid(int l_playerid) {
			this.l_playerid = l_playerid;
		}
		public String getL_playername() {
			return l_playername;
		}
		public void setL_playername(String l_playername) {
			this.l_playername = l_playername;
		}

		public void setReinforcementMap(int noOfArmies){
            this.d_reinforcementPool= noOfArmies;
        }

		public int getReinforcementMap(){
			return d_reinforcementPool;
		}

		public List<Territory> getlistOfCountriesOwned() {
			return listOfCountriesOwned;
		}

		void issue_order() {
			
		}
		
		void next_order() {
			
		}
		

        
		 

		
}