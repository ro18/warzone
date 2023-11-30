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
     * @param p_command
     * @param type
     */
    public static void pushCommand(String p_command, String type) {
        switch (type) {
            case "orders":
                d_stackOfCommands.add(p_command);
                break;
            case "playerAdd":
                d_stackOfPlayerAddCommands.add(p_command);
                break;
            case "strategy":
                d_stackOfStrategyCommands.add(p_command);
                break;
        
            default:
                return;
        }
    }

   
    
    /** 
     * @param type
     * @return String
     */
    public static String popCommand(String type) {
        String l_command = "";
        switch (type) {
            case "orders":
                l_command = d_stackOfCommands.remove();
                break;
            case "playerAdd":
                l_command = d_stackOfPlayerAddCommands.remove();
                break;
            case "strategy":
                l_command = d_stackOfStrategyCommands.remove();
                break;
        
            default:
                return null;
        }
        return l_command;
    };

    
    
    /** 
     * @param type
     * @return Integer
     */
    public static Integer checkSize(String type) {        
        int l_size = 0;
        switch (type) {
            case "orders":
                l_size = d_stackOfCommands.size();
                break;
            case "playerAdd":
                l_size = d_stackOfPlayerAddCommands.size();
                break;
            case "strategy":
                l_size = d_stackOfStrategyCommands.size();
                break;
        
            default:
                break;
        }
        return l_size;
    }

}
