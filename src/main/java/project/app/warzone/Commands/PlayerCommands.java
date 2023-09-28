package project.app.warzone.Commands;

    
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.Territory;


@ShellComponent
public class PlayerCommands {

    public GameEngine gameEngine;
    public String prevUserCommand; 

    public PlayerCommands(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @ShellMethod(key= "gameplayer", value="Player can create or remove a player")
    public String gamePlayer(@ShellOption(value = "-add") String add,@ShellOption(value = "-r",defaultValue = ShellOption.NULL) String p_removePlayerCmd){

        
        return "work";
        // String l_addCmd="-add";
        // String l_removeCmd = "-remove";

        // if(gameEngine.prevUserCommand.equals("loadmap")){
        //     String l_playerCmd[] = p_playerCmd.split(" "); 

        //     if(l_playerCmd[0].equals(l_addCmd)){
        //         String l_playerName = l_playerCmd[1];
        //         System.out.println("player name is : "+l_playerName);
        //         List<Territory> listOfCountries = new ArrayList<>() ;
        //         Player player= new Player(1, l_playerCmd[1],listOfCountries);
        //         gameEngine.d_playersList.add(player);
        //         return "Player added successfully";
            
        //     }
        //     else if(l_playerCmd[0].equals(l_removeCmd)){

        //         Optional<Player> playerToDelete = gameEngine.d_playersList.stream().filter(c-> c.getL_playername().equals(l_playerCmd[1])).findFirst();
        //         if(playerToDelete!= null){
        //             gameEngine.d_playersList.remove(playerToDelete);
        //             return "Player deleted successfully";
        //         }
        //         else{
        //             return "Player not found";
        //         }
                


        //     }
        //     else{
        //     return "Invalid Command";
        //     }

        // }
        // else{
        //     return "You cannnot add players at this stage";
        // }

        

        
    }

    //  @ShellMethod(key= "gameplayer", value="Player can create or remove a player")
    // public String gamePlayer(@ShellOption(value = "-remove") String p_playerCmd){
    // }

    


    // @ShellMethod(key= "gameplayer", value="Player can create or remove a player")
    // public String gamePlayer(@ShellOption String p_filename){
        
        
    // }

    // @ShellMethod(key= "assignCountries", value="Player can assign countries randomly")
    // public String gamePlayer(@ShellOption String p_filename){
        
        
    // }
    
}
