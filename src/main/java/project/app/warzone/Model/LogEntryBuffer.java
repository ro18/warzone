package project.app.warzone.Model;
import java.util.*;

/**
 * This class is used as a logger to log the actions in the game.
 */
public class LogEntryBuffer extends Observable{
    
    /**
     * This method is used to add set the object changed to true so hasTrue() returns false. Also it nnotifies all the observer of the change
     */

    public void notifyClass() {
        setChanged();
        notifyObservers();
    }

}

