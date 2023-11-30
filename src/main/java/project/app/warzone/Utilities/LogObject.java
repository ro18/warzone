package project.app.warzone.Utilities;

import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to store the log object
 */
@Getter @Setter
public class LogObject {
    public String d_message ="";
    public String d_timestamp = new SimpleDateFormat("hh:mm:ss").format(new java.util.Date());
    public String d_command;
    public static int d_logLevel = 0;
    private enum d_listOfStatusCode{
        SUCCESS,
        FAILURE
    };
    public String d_statusCode;

    
    
    /** 
     * @param p_status
     * @param p_message
     */
    public void setStatus(boolean p_status, String p_message) {
        d_logLevel++;
        d_statusCode = p_status ? d_listOfStatusCode.SUCCESS.toString() : d_listOfStatusCode.FAILURE.toString();
        d_message = p_message;
    }

}
