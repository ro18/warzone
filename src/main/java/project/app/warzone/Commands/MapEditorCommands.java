package project.app.warzone.Commands;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Model.Continent;
import project.app.warzone.Model.Map;

@ShellComponent
public class MapEditorCommands {

   
    
    private final MapFeatures mapFeatures;
    public Map map;
    public String prevUserCommand="";


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
    public String editcontinent(@ShellOption String p_editcmd){
        if(prevUserCommand=="editmap"){  
            String l_addCmd = "-add";
            String[] editCmd= p_editcmd.split(" ");
            Dictionary<Integer,String> continentDict = new Hashtable<Integer,String>();

            if(editCmd[0].equals(l_addCmd)){
                if(map.getListOfContinents() != null){
                    List<Continent> continentList =map.getListOfContinents();
                
                    try{

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
            return "Choose the below commands to create new map:\n 1.createmap";  //Create New Map Call createMap function
            //map.createMap(p_filename);
        }
    

    }





    
}
