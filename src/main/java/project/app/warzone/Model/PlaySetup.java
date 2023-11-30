package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;

public class PlaySetup extends Play implements Observer{

	
	public PlayerFeatures d_playerFeatures;
	private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

	PlaySetup(GameEngine p_ge) {
		super(p_ge);
		this.d_playerFeatures = new PlayerFeatures();
		l_logEntryBuffer.addObserver(this);
	}

	/**
	 * @param p_filename filename
	 */
	public void loadMap(String p_filename) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_fileName filename
	 */
	public void validateMap() {
		printInvalidCommandMessage();
	}

	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_attribute  attribute
	 * @param p_playerName playername
	 */
	public void setPlayers(String p_attribute, String p_playerName) {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("gameplayer -"
				+ (p_attribute != null && p_attribute != "" ? "add " + p_attribute : "remove " + p_playerName));
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
			l_logObject.setStatus(true, "Players added successfully");
			l_logEntryBuffer.notifyClasses(l_logObject);
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
			l_logObject.setStatus(true, "Players removed successfully");
			l_logEntryBuffer.notifyClasses(l_logObject);
			System.out.println("Players removed successfully");

		}
	}

	public void assignCountries() {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("assigncountries");

		if (ge.getPlayers().size() < 2) {
			l_logObject.setStatus(false, "You need atleast 2 players to play the game. Please add more players");
			l_logEntryBuffer.notifyClasses(l_logObject);
			System.out.println("You need atleast 2 players to play the game. Please add more players");
		} else {
			Player player = ge.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
			d_playerFeatures.assignCountries(ge);
			System.out.println("Assigned Countries to the players are:");
			// d_playerFeatures.showAllAssignments(ge.getPlayers());
			l_logObject.setStatus(true, "Countries assigned successfully");
			l_logEntryBuffer.notifyClasses(l_logObject);
			System.out.println("Assignment of countries is completed. \nNow its turn of player: "
					+ player.getL_playername() + " to deploy armies");
			ge.setPhase(new Reinforcement(ge));
		}

	}

	public void assignCountriesForDemo() {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("assigncountries");

		if (ge.getPlayers().size() < 2) {
			l_logObject.setStatus(false, "You need atleast 2 players to play the game. Please add more players");
			l_logEntryBuffer.notifyClasses(l_logObject);
			System.out.println("You need atleast 2 players to play the game. Please add more players");
			
		} else {
			Player player = ge.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
			//d_playerFeatures.assignCountriesForDemo(ge);
			d_playerFeatures.assignCountries(ge);
			// System.out.println("Assigned Countries to the players are:");
			// d_playerFeatures.showAllAssignments(ge.getPlayers());
			l_logObject.setStatus(true, "Countries assigned successfully");
			l_logEntryBuffer.notifyClasses(l_logObject);
			System.out.println("Assignment of countries is completed. \nNow its turn of player: "
					+ player.getL_playername() + " to deploy armies");
			ge.setPhase(new Reinforcement(ge));
			System.out.println("Players can now start with deploying armies");
		}

	}

	public void setPlayerStrategy(){
		LogObject l_logObject = new LogObject();
		
		l_logObject.setStatus(false, "Setting Strategy for players");

		d_playerFeatures.setPlayerStrategy(ge);



	}

	
	/** 
	 * @param p_countryID	country ID
	 * @param p_armies	no of armies
	 */
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

	/**
	 * @param p_obj object
	 * @param p_arg argument
	 */
	public void update(java.util.Observable p_obj, Object p_arg) {
		LogObject l_logObject = (LogObject) p_arg;
		if (p_arg instanceof LogObject) {
			try {
				BufferedWriter l_writer = new BufferedWriter(
						new FileWriter(System.getProperty("logFileLocation"), true));
				l_writer.newLine();
				l_writer.append(LogObject.d_logLevel + " " + l_logObject.d_command + "\n" + "Time: "
						+ l_logObject.d_timestamp + "\n" + "Status: "
						+ l_logObject.d_statusCode + "\n" + "Description: " + l_logObject.d_message);
				l_writer.newLine();
				l_writer.close();
			} catch (IOException e) {
				System.out.println("Error Reading file");
			}
		}
	}

	/**
	 * @param p_CurrentPlayerId current player
	 * @param p_countryfrom     source country
	 * @param p_countryTo       target country
	 * @param p_armies          army
	 */
	public void advance(int p_CurrentPlayerId, int p_countryfrom, int p_countryTo, int p_armies) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryID country ID
	 */
	public void bomb(int p_countryID) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryID country ID
	 */
	public void blockade(int p_countryID) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_countryIDFrom   source country
	 * @param p_countryIDTo     target country
	 * @param p_armiesToAirlift army to airlift
	 */
	public void airlift(int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {
		printInvalidCommandMessage();
	}

	/**
	 * @param p_targetPlayerId target player id
	 */
	public void negotiate(int p_targetPlayerId) {
		printInvalidCommandMessage();
	}
}
