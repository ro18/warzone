package project.app.warzone.Commands;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import project.app.warzone.Features.MapFeatures;
<<<<<<< HEAD
import project.app.warzone.Model.Continent;
import project.app.warzone.Model.Map;
=======
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Utilities.Commands;
>>>>>>> 950f46e1ae6cedb00841943566b428247fbf0a66

@ShellComponent
public class MapEditorCommands {

   
    
    private final MapFeatures mapFeatures;
<<<<<<< HEAD
    public Map map;
    public String prevUserCommand="";

=======
    public GameEngine gameEngine;
    public PlayerCommands playerCommands;
    public PlayerFeatures playerFeatures;
>>>>>>> 950f46e1ae6cedb00841943566b428247fbf0a66

    public MapEditorCommands(MapFeatures mapFeatures, GameEngine gameEngine, PlayerFeatures playerFeatures){
        this.mapFeatures = mapFeatures;
        this.gameEngine = gameEngine;
        playerCommands = new PlayerCommands(gameEngine,playerFeatures);

    }


    @ShellMethod(key= "loadmap", value="Player can create or open an existing map")
    public String loadMap(@ShellOption String p_filename){
        gameEngine.prevUserCommand=Commands.LOADMAP;
        if(gameEngine.gameMap.fileExists(p_filename)){
            System.out.println("One file found.");
            gameEngine.gameMap.set_USER_SELECTED_FILE(p_filename);
            return "Choose one of the below commands to proceed:\n 1. showmap 2.editmap";
        }
        else{
            return "Map not found.";
        }
    }

    @ShellMethod(key= "showmap", value="Used to display map continents with terriotories and boundaries")
    public void showmap(){
<<<<<<< HEAD
        String p_mapLocation=map.getMapDirectory()+"/"+map.get_USER_SELECTED_FILE()+".map";
=======
        String p_mapLocation=gameEngine.gameMap.getMapDirectory()+"\\"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map";
>>>>>>> 950f46e1ae6cedb00841943566b428247fbf0a66
        //System.out.println("map location:"+p_mapLocation);
        gameEngine.gameMap = mapFeatures.readMap(p_mapLocation);

    }
    
     @ShellMethod(key= "editcontinent", prefix = "-", value="This is used to add or update continents")
    public String editcontinent(@ShellOption String p_editcmd){
        if(prevUserCommand=="editmap"){  
            String l_addCmd = "-add";
            String[] editCmd= p_editcmd.split(" ");
            Dictionary<Integer,String> continentDict = new Hashtable<Integer,String>();

<<<<<<< HEAD
            if(editCmd[0].equals(l_addCmd)){
                if(map.getListOfContinents() != null){
                    List<Continent> continentList =map.getListOfContinents();
                
                    try{
=======
    @ShellMethod(key= "editcontinent", value="This is used to add or update continents")
    public String editcontinent(){
        return "You can edit continents here";
        
        String p_mapLocation=gameEngine.gameMap.getMapDirectory()+"\\test"+".map";

        mapFeatures.writeToMap(gameMap,p_mapLocation)
>>>>>>> 950f46e1ae6cedb00841943566b428247fbf0a66

                        Continent continentName = continentList.get(Integer.parseInt(editCmd[1]));
                        System.out.println("ContinentName:::" +continentName);
                        String newcContinentName = editCmd[2] ;
                        System.out.println("newcContinentName" +newcContinentName);
                        continentList.add(Integer.parseInt(editCmd[1]),new Continent(newcContinentName));
                        
                    
                    } 
                    catch(IndexOutOfBoundsException exception){
                        return("incorrect index id");
                    }
                    return "Continent edited successfully";
            
                }
                else{

                    //creating map logic
                    
                    return "";
                }

                
            }
            else{
                return("Invalid command. Please enter editmap command to edit the continent");
            }

     
        }
        else{
            return "You cannot edit map now. Please enter editmap cmd";
        }
       
    }

    @ShellMethod(key= "editcountry", value="This is used to add or update countries")
     public String editcountry(){
        return "You can edit countries here";

    }
    @ShellMethod(key= "editneighbor", value="This is used to add or update neighbor")
     public String editneighbor(){
        return "You can edit neighbor here";

    }

    @ShellMethod(key= "editmap", value="This is used to add or create map")
     public String editmap(@ShellOption String p_filename){
        prevUserCommand="editmap";
        if(map.fileExists(p_filename)){
            System.out.println("One file found.");
            map.set_USER_SELECTED_FILE(p_filename);
            showmap();
            return "Choose one of the below commands to proceed:\n 1.editcontinent 2.editcountry 3.editneighbor";
        }
        else{

            System.out.println("File not found.");
            
            map.createNewMapFile(p_filename);
            return "New Mapfile created successfully";
        }
    

    }





    
}
