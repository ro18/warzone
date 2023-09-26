package project.app.warzone.Commands;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Model.Map;

@ShellComponent
public class MapEditorCommands {

   
    
    private final MapFeatures mapFeatures;
    public Map map;


    public MapEditorCommands(MapFeatures mapFeatures, Map map){
        this.mapFeatures = mapFeatures;
        this.map = map;


    }



    @ShellMethod(key= "loadmap", value="Player can create or open an existing map")
    public String loadMap(@ShellOption String p_filename){
        
        if(map.fileExists(p_filename)){
            System.out.println("One file found.");
            map.set_USER_SELECTED_FILE(p_filename);
            return "Choose one of the below commands to proceed:\n 1. showmap 2.editmap";
        }
        else{
            return "Map not found.";
        }
    }

    @ShellMethod(key= "showmap", value="Used to display map continents with terriotories and boundaries")
    public void showmap(){
        String p_mapLocation=map.getMapDirectory()+"\\"+map.get_USER_SELECTED_FILE()+".map";
        //System.out.println("map location:"+p_mapLocation);
        map = mapFeatures.readMap(p_mapLocation);

    }

    @ShellMethod(key= "editcontinent", value="This is used to add or update continents")
    public String editcontinent(){
        return "You can edit continents here";

    }

    @ShellMethod(key= "editcountry", value="This is used to add or update countries")
     public String editcountry(){
        return "You can edit countries here";

    }
    
    @ShellMethod(key= "editmap", value="This is used to add or create map")
     public String editmap(@ShellOption String p_filename){

        return "You can edit or create a map here";

    }


    @ShellMethod(key= "validateMap")
     public String validatemap(){
        return "True";
    }

    
}
