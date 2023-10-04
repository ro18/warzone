package project.app.warzone.Commands;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.MapResouces;

/**
 * This class is used to edit the map using shell commands.
 */
@ShellComponent
public class MapEditorCommands {

   
    
    private final MapFeatures mapFeatures;
    public GameEngine gameEngine;
    public PlayerCommands playerCommands;
    public PlayerFeatures playerFeatures;
    private final MapResouces mapResources;

    public MapEditorCommands(MapFeatures mapFeatures, GameEngine gameEngine, PlayerFeatures playerFeatures, MapResouces mapResources){
        this.mapFeatures = mapFeatures;
        this.gameEngine = gameEngine;
        playerCommands = new PlayerCommands(gameEngine,playerFeatures);
        this.mapResources= mapResources;
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
    public String showmap(){
        String p_mapLocation=gameEngine.gameMap.getMapDirectory()+"\\"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map";

        gameEngine.gameMap = mapFeatures.readMap(p_mapLocation);
        Boolean l_result = mapFeatures.validateEntireGraph(gameEngine);
        if(!l_result){
            return("PLease try with some other map");
        }
        else{
            return("You can now proceed to add gameplayers");
        }

    }

    
     /**
     * @param p_editcmd
     * @param p_editremovecmd
     * @return
     */
    @ShellMethod(key= "editcontinent", prefix = "-", value="This is used to add or update continents")
    public String editcontinent(@ShellOption(value="a",defaultValue=ShellOption.NULL)String p_editcmd, @ShellOption(value="r",defaultValue=ShellOption.NULL) String p_editremovecmd){
        if(gameEngine.prevUserCommand==Commands.EDITMAP){  
            //String l_addCmd = "-add";
            Dictionary<Integer,String> continentDict = new Hashtable<Integer,String>();

            // if(p_editcmd != null && p_editcmd != ""){
               String[] editCmd= p_editcmd.split(",");
               System.out.println("length of string:"+editCmd.length);
               System.out.println("Inside edit continent");
               Map<String, String> listofContinents= new HashMap<String,String>();
               int l_i=0;

              String commandToCheck= "-add";
              while(l_i< editCmd.length){

               System.out.println(editCmd[l_i]+":"+commandToCheck);
               if(editCmd[l_i].toString().equals(commandToCheck)){
                l_i++;
                continue;
                
               }
               else{
                listofContinents.put(editCmd[l_i], editCmd[l_i+1]);
                l_i+=2;
                System.out.println("variable"+l_i);
                System.out.println("listofContinents" +listofContinents);
               }

              
               }
                
            //     if(gameEngine.gameMap.getListOfContinents() != null){
            //         List<Continent> continentList =gameEngine.gameMap.getListOfContinents();
                
            //         try{

            //             Continent continentName = continentList.get(Integer.parseInt(editCmd[0]));
            //             System.out.println("ContinentName:::" +continentName);
            //             String newcContinentName = editCmd[1] ;
            //             System.out.println("newcContinentName" +newcContinentName);
            //             continentList.add(Integer.parseInt(editCmd[1]),new Continent(newcContinentName));

            //             //MapFeatures.saveChangesToFile();
                        
                    
            //         } 
            //         catch(IndexOutOfBoundsException exception){
            //             return("incorrect index id");
            //         }
            //         return "Continent edited successfully";
            
            //     }
                // else{

                //     //creating map logic
                    
                //     return "";
                // }
        return " ";
                
            }
            else{
                return("Invalid command. Please enter editmap command to edit the continent");
            }

     
        
        // else{
        //     return "You cannot edit map now. Please enter editmap cmd";
        // }
       
    }
    @ShellMethod(key= "editcountry", prefix = "-", value="This is used to add continents")
    public String editcountry(@ShellOption(value="a",defaultValue=ShellOption.NULL)String p_editcmd, @ShellOption(value="r",defaultValue=ShellOption.NULL) String p_editremovecmd){
     if(gameEngine.prevUserCommand==Commands.EDITMAP){  
     
               Dictionary<Integer,String> countryDict = new Hashtable<Integer,String>();
               String[] editCmd= p_editcmd.split(",");
               System.out.println("length of string:"+editCmd.length);
               System.out.println("Inside edit Country");
               Map<String, String> listofCountries= new HashMap<String,String>();
               int l_i=0;

              String commandToCheck= "-add";
              while(l_i< editCmd.length){

               System.out.println(editCmd[l_i]+":"+commandToCheck);
               if(editCmd[l_i].toString().equals(commandToCheck)){
                l_i++;
                continue;
                
               }
               else{
                listofCountries.put(editCmd[l_i], editCmd[l_i+1]);
                l_i+=2;
                //System.out.println("variable"+l_i);
                System.out.println("listofCountries" +listofCountries);
               }

              
               }
                
    
    
    }
    else{
        return("Invalid command. Please enter editmap command to edit the continent");
        }


        Boolean l_result = mapFeatures.validateEntireGraph(gameEngine);
        if(!l_result){
            return("PLease try with some other map");
        }
        else{
            return("You can now proceed to add gameplayers");
        }

    }

    // @ShellMethod(key= "editcontinent", value="This is used to add or update continents")
    // public String editcontinent(){
    //     return "You can edit continents here";
        
    //     String p_mapLocation=gameEngine.gameMap.getMapDirectory()+"\\test"+".map";

    //     mapFeatures.writeToMap(gameMap,p_mapLocation)

    // }

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
        
        if(gameEngine.gameMap.fileExists(p_filename)){
            System.out.println("One file found.");
            gameEngine.gameMap.set_USER_SELECTED_FILE(p_filename);
            showmap();
            return("Please use gameplayer -add command to add players in the game");
        }
        else{
            gameEngine.prevUserCommand=Commands.EDITMAP;
            //System.out.println("File not found.");
            
            gameEngine.gameMap.createNewMapFile(p_filename);
           // System.out.println("New File created successfully..");
           System.out.println("\n");
            System.out.println("Choose one of the below commands to proceed:\n 1.editcontinent 2.editcountry 3.editneighbor");
             System.out.println("\n");
            System.out.println("Continet list:::: Select the continents you need to add");
            System.out.println("----------------------------------------------------------");
            mapResources.printMapDetails(mapResources.getAllContinents());
            System.out.println("\n");
            System.out.println("Countries list:::: Select the Countries you need to add");
            System.out.println("----------------------------------------------------------");

            mapResources.printMapDetails(mapResources.getAllCountries());

            System.out.println("\n");
           return "Please select the add or remove command";
            


        }
    

    }





    
}
