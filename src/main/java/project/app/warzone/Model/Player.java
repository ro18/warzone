package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

public class Player 
{
	
		public int l_playerid;
		public String l_playername;
		
        public List<Territory> listOfCountries;

        public Player(int l_playerid ,String l_playername,List<Territory> listOfCountries) {
            this.l_playerid = l_playerid;
            this.l_playername = l_playername;
            this.listOfCountries = new ArrayList<>();
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

		void issue_order() {
			
		}
		
		void next_order() {
			
		}
		

        
		 

		
}