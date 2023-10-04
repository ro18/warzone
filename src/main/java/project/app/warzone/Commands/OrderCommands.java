package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * This class stores all the order-related commands allowed in gameplay
 */
@ShellComponent
public class OrderCommands {

    @ShellMethod(key = "deploy", value = "This is used to deploy armies")
    public String deployArmies(@ShellOption int p_countryID, @ShellOption int p_armies) {
        return "You can deploy armies here";
    }
}
