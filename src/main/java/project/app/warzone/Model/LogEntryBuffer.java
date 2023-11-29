package project.app.warzone.Model;
import java.util.*;

import project.app.warzone.Utilities.LogObject;

/**
 * This class is used as a logger to log the actions in the game.
 */
public class LogEntryBuffer extends Observable{
    
    

    /**
     * @param p_obj Log object
     */
    public void notifyClasses(LogObject p_obj) {
        setChanged();
        try {
        notifyObservers(p_obj);            
        } catch (Exception e) {
            System.out.println("Exception occured while notifying observers");
        }
    }

}

