package project.app.warzone.Commands;
import java.io.File;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.Map;
import project.app.warzone.Utilities.MyFilenameFilter;

@ShellComponent
public class MapEditorCommands {

    String USER_SELECTED_FILE="europe";
    String MAP_DIRECTORY="src\\main\\java\\project\\app\\warzone\\Utilities\\Maps";
    String d_userSelectedMapFile="";
    private final Map mapObject;


    public MapEditorCommands(Map mapObject){
        this.mapObject = mapObject;
    }



    @ShellMethod(key= "loadmap", value="Player can create or open an existing map")
    public String loadMap(@ShellOption String p_filename){
        
        File l_mapDirectory = new File(MAP_DIRECTORY);
        System.out.println(l_mapDirectory);
        MyFilenameFilter filter = new MyFilenameFilter(p_filename);
        File[] l_matchingFiles = l_mapDirectory.listFiles(filter);

        if(l_matchingFiles !=null && l_matchingFiles.length ==1 ){
            System.out.println("One file found.");
            USER_SELECTED_FILE=p_filename;
            return "Choose one of the below commands to proceed:\n 1. showmap 2.editmap";
        }
        else{
            return "Map not found.";
        }
    }

    @ShellMethod(key= "showmap", value="Used to display map continents with terriotories and boundaries")
    public void showmap(){
        String p_mapLocation=MAP_DIRECTORY+"\\"+USER_SELECTED_FILE+".map";
        System.out.println("map location:"+p_mapLocation);
        mapObject.readMap(p_mapLocation);

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
