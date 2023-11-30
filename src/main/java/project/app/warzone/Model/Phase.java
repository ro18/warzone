package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;

import org.jline.utils.Log;

import project.app.warzone.Utilities.LogObject;

// This class is Phase.java state design pattern class with GameEngine.java as the context class.  
// This class will implement the state design pattern. The state pattern will be used to implement the game phases.
// The game play phase must be divided into the following phases: startup, issue order, and order execution phases

public abstract class Phase implements Observer {

	/**
	 * Contains a reference to the State of the GameEngine
	 * so that the state object can change the state of
	 * the GameEngine to transition between states.
	 */
	GameEngine ge;
	private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

	Phase(GameEngine p_ge) {
		ge = p_ge;
		l_logEntryBuffer.addObserver(this);
	}

	/**
	 * @param p_editcmd         edit command
	 * @param p_attribute       attribute
	 * @param p_countryID       country ID
	 * @param p_CurrentPlayerId current player
	 * @param p_countryfrom     source country
	 * @param p_countryTo       target country
	 * @param p_countryIDFrom   source country ID
	 * @param p_countryIDTo     target country ID
	 */
	// common commands
	abstract public void loadMap(String p_filename);

	abstract public void showMap();

	// Edit map commands
	abstract public void editCountry(String p_editcmd, String p_editremovecmd);

	abstract public void saveMap(String p_filename);

	abstract public void editMap(String p_fileName);

	abstract public void editContinent(String p_editcmd, String p_editremovecmd);

	abstract public void editNeighbor(String p_editcmd, String p_editremovecmd);

	// validate map command
	abstract public void validateMap();
	// Play commands
	// common commands
	abstract public void showstats();

	abstract public void showmapstatus();

	// game setup commands
	abstract public void setPlayers(String p_attribute, String p_playerName);

	abstract public void setPlayerStrategy();
	abstract public void assignCountries();

	abstract public void assignCountriesForDemo();

	// reinforcement commands
	abstract public void reinforce(int p_countryID, int p_armies);

	// attack commands
	abstract public void attack();

	abstract public void advance(int p_CurrentPlayerId, int p_countryfrom, int p_countryTo, int p_armies);

	abstract public void bomb(int p_countryID);

	abstract public void blockade(int p_countryID);

	abstract public void airlift(int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift);

	abstract public void negotiate(int p_targetPlayerId);

	// fortify commands
	abstract public void fortify();

	// end command
	abstract public void endGame();

	// go to next phase
	abstract public void next();

	/**
	 * Common method to all States.
	 */
	public void printInvalidCommandMessage() {
		LogObject l_logObject = new LogObject();
		l_logObject.d_command = "";
		l_logObject.setStatus(false, "Invalid command in state " + this.getClass().getSimpleName());
		l_logEntryBuffer.notifyClasses(l_logObject);
		System.out.println("Invalid command in state " + this.getClass().getSimpleName());
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

	public void saveMap() {
		ge.setPhase(new PlaySetup(ge));

	}
}