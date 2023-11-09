package project.app.warzone.Model;
import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Utilities.Commands;

public class PlaySetup extends Play {
	public PlayerFeatures d_playerFeatures;

	PlaySetup(GameEngine p_ge) {
		super(p_ge);
		this.d_playerFeatures = new PlayerFeatures();
	}

	public void loadMap(String p_filename) {
		printInvalidCommandMessage();
    }

	public void setPlayers(String p_attribute, String p_playerName) {
		if (p_attribute != null && p_attribute != "") {
			String l_players[] = p_attribute.split(",");
			int l_i = 0;
			while (l_i < l_players.length) {

				if (l_players[l_i].toString().equals("-add") == false) {

					d_playerFeatures.addPlayers(l_players[l_i], ge);

				}
				l_i++;
			}

			d_playerFeatures.printAllPlayers(ge);
			ge.prevUserCommand = Commands.ADDPLAYER;
			System.out.println("Players added successfully");

		} else {

			String l_players[] = p_playerName.split(",");
			int l_i = 0;
			while (l_i < l_players.length) {
				if (l_players[l_i].toString().equals("-remove") == false) {

					d_playerFeatures.removePlayers(l_players[l_i], ge);

				}
				l_i++;
			}
			d_playerFeatures.printAllPlayers(ge);
			ge.prevUserCommand = Commands.REMOVEPLAYER;
			System.out.println("Players removed successfully");

		}
	}

	public void assignCountries() {
		if(ge.getPlayers().size() < 2){
            System.out.println("You need atleast 2 players to play the game. Please add more players");
        } else {
			Player player = ge.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
			d_playerFeatures.assignCountries(ge);
			System.out.println("Assigned Countries to the players are:");
			d_playerFeatures.showAllAssignments(ge.getPlayers());
			ge.prevUserCommand = Commands.ASSIGNCOUNTRIES;
			System.out.println("Assignment of countries is completed. \nNow its turn of player: "+player.getL_playername()+" to deploy armies");
			ge.setPhase(new Reinforcement(ge));
		}
        
	}

	public void reinforce(int p_countryID, int p_armies) {
		printInvalidCommandMessage(); 
	}

	public void attack() {
		printInvalidCommandMessage(); 
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

	public void endGame() {
		printInvalidCommandMessage(); 
	}

	public void next() {
		ge.setPhase(new Reinforcement(ge));
	}

	public void advance(int p_CurrentPlayerId,int p_countryfrom,int p_countryTo, int p_armies) {
		printInvalidCommandMessage(); 
	}

	public void bomb(int p_countryID) {
		printInvalidCommandMessage(); 
	}

	public void blockade( int p_countryID) {
		printInvalidCommandMessage(); 
	}

	public void airlift(int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		printInvalidCommandMessage(); 
	}

	public void negotiate(int p_targetPlayerId) {
		printInvalidCommandMessage(); 
	}
}
