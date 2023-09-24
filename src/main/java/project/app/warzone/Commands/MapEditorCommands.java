package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MapEditorCommands {


     @ShellMethod(key= "loadMap", value="Player can create or open an existing")
    public String loadMap(@ShellOption String p_filename){
        return "You can edit continents here"+p_filename;

    }
    @ShellMethod(key= "editcontinent", value="This is used to add or update continents")
    public String editcontinent(){
        return "You can edit continents here";

    }

    @ShellMethod(key= "editcountry", value="This is used to add or update countries")
     public String editcountry(){
        return "You can edit countries here";

    }



    
}
