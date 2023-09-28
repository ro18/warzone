package project.app.warzone.Commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Model.Player;
import project.app.warzone.Model.Territory;

@ShellComponent
public class PlayerCommands {

    @ShellMethod(key= "gameplayer", value="Player can create or remove a player")
    public String gamePlayer(@ShellOption String p_playername){
        

        List<Territory> listOfCountries = new ArrayList<>() ;
        Player P = new Player(1, p_playername,listOfCountries);
        return "String";
    }

    // @ShellMethod(key= "gameplayer", value="Player can create or remove a player")
    // public String gamePlayer(@ShellOption String p_filename){
        
        
    // }

    // @ShellMethod(key= "assignCountries", value="Player can assign countries randomly")
    // public String gamePlayer(@ShellOption String p_filename){
        
        
    // }
    
}