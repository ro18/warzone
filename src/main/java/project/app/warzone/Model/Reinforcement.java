package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;

import project.app.warzone.Utilities.LogObject;

/**
 *	
 */
public class Reinforcement extends MainPlay implements Observer {

	private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();
	Reinforcement(GameEngine p_ge) {
		super(p_ge);
		l_logEntryBuffer.addObserver(this);
	}

	public void loadMap(String p_filename) {
		printInvalidCommandMessage(); 
	}

	public void reinforce(int p_countryID, int p_armies) {
		LogObject l_logObject = new LogObject();
		l_logObject.setD_command("reinforce " + p_countryID + " " + p_armies);
       	String l_reString = d_playerFeatures.deployArmies(ge, p_countryID, p_armies);
	   	l_logObject.setStatus(true, l_reString);
	   	l_logEntryBuffer.notifyClasses(l_logObject);

		   
		   
	  

	}

	public void attack() {
		printInvalidCommandMessage(); 
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

	public void next() {
		ge.setPhase(new Attack(ge));
	}

	public void update(java.util.Observable p_obj, Object p_arg) {
        LogObject l_logObject = (LogObject) p_arg;
        if (p_arg instanceof LogObject) {
            try {
                BufferedWriter l_writer = new BufferedWriter(
                        new FileWriter(System.getProperty("logFileLocation"), true));
                l_writer.newLine();
                l_writer.append(LogObject.d_logLevel + " " + l_logObject.d_command + "\n" + "Time: " + l_logObject.d_timestamp + "\n" + "Status: "
                        + l_logObject.d_statusCode + "\n" + "Description: " + l_logObject.d_message);
                l_writer.newLine();
                l_writer.close();
            } catch (IOException e) {
                System.out.println("Error Reading file");
            }
        }
    }
}
