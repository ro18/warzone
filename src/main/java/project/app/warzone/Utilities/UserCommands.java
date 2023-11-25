package project.app.warzone.Utilities;

import java.util.LinkedList;
import java.util.Queue;
import lombok.Getter;

@Getter
public class UserCommands {
    // make a global stack of strings

    private static Queue<String> d_stackOfCommands = new LinkedList<>();

    /**
     * This method is used to push the command to the queue
     * @param p_command command to be pushed
     */
    public static void pushCommand(String p_command) {
        d_stackOfCommands.add(p_command);
    }

    /**
     * This method is used to pop the command from the queue
     * @return command to be popped
     */
    public static String popCommand() {
        return d_stackOfCommands.remove();
    };

    /**
     * This method is used to check the size of the queue
     * @return command to be peeked
     */
    public static Integer checkSize() {
            return d_stackOfCommands.size();
    }

}
