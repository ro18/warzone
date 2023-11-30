package project.app.warzone.Utilities;

import java.util.LinkedList;
import java.util.Queue;
import lombok.Getter;

@Getter
public class UserCommands {
    // make a global stack of strings

    private static Queue<String> d_stackOfCommands = new LinkedList<>();
    private static Queue<String> d_stackOfPlayerAddCommands = new LinkedList<>();
    private static Queue<String> d_stackOfStrategyCommands = new LinkedList<>();

    /**
     * This method is used to push the command to the queue
     * @param p_command command to be pushed
     */
    public static void pushCommand(String p_command, String type) {
        switch (type) {
            case "orders":
                d_stackOfCommands.add(p_command);
            case "playerAdd":
                d_stackOfPlayerAddCommands.add(p_command);
            case "strategy":
                d_stackOfStrategyCommands.add(p_command);
        
            default:
                return;
        }
    }

    /**
     * This method is used to pop the command from the queue
     * @return command to be popped
     */
    public static String popCommand(String type) {
        switch (type) {
            case "orders":
                return d_stackOfCommands.remove();
            case "playerAdd":
                return d_stackOfPlayerAddCommands.remove();
            case "strategy":
                return d_stackOfStrategyCommands.remove();
        
            default:
                return null;
        }
    };

    /**
     * This method is used to check the size of the queue
     * @return command to be peeked
     */
    public static Integer checkSize(String type) {
        switch (type) {
            case "orders":
                return d_stackOfCommands.size();
            case "playerAdd":
                return d_stackOfPlayerAddCommands.size();
            case "strategy":
                return d_stackOfStrategyCommands.size();
        
            default:
                return 0;
        }
    }

}
