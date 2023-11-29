package project.app.warzone.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import project.app.warzone.Utilities.LogObject;

public class ConquestFileReader {
    private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

    //Empty Constructor
    public ConquestFileReader(){
       
    }
    public Map readMap(String filename){
        System.out.println("Inside ConquestFileReader: readMap()");
        // LogObject l_logObject = new LogObject();
        // l_logObject.setD_command("showmap");
        // l_logEntryBuffer.addObserver((Observer) this);

        String l_line="";
        List<Continent> continentsList = new ArrayList<>();
        List<Node> nodesList = new ArrayList<>();
        Map gameMap = new Map();
        try{

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ( l_line !=null ) { 
                
                l_line=reader.readLine();
                if(l_line.equals("[continents]")){
                    l_line=reader.readLine();

                    while(!l_line.equals("")){

                        String[] continentDetails = l_line.split(" ");            
                        gameMap.createContinent(continentDetails[0], Integer.parseInt((continentDetails[1])));
                        l_line= reader.readLine();

                    }
                        
                    continentsList= gameMap.getListOfContinents();

                    
                }

                if(l_line.equals("[countries]")){

                    l_line=reader.readLine();

                    while(!l_line.equals("") ){

                        String[] countryDetails = l_line.split(" ");
                        gameMap.createAndInsertCountry(Integer.parseInt(countryDetails[0]),countryDetails[1],continentsList.get(Integer.parseInt(countryDetails[2])-1) );
                        l_line= reader.readLine();


                    }
                    
                    nodesList = gameMap.getNodes();
                
                    
                
                }
               

                if(l_line.equals("[borders]")){

                    l_line=reader.readLine();

                    while(  l_line != null && !l_line.equals("")){
                        
                        List<Node> bordersToConnect = new ArrayList<>();
                        String[] borderDetails = l_line.split(" ");
                        Node currentTerritory = nodesList.get(Integer.parseInt(borderDetails[0])-1);
                        
                        for(int i=1 ; i< borderDetails.length;i++){
                            Node node=nodesList.get(Integer.parseInt(borderDetails[i])-1);
                            bordersToConnect.add(node);
                        }
                        gameMap.addEdgesOfCountry(currentTerritory, bordersToConnect);
                        l_line=reader.readLine();
                        

                    }
                }

            }   
       
            reader.close();
            // l_logObject.setStatus(true, "User printed map " + filename.split("/")[filename.split("/").length-1].split(".map")[0]);
            // l_logEntryBuffer.notifyClasses(l_logObject);
            printMap(gameMap);
            return gameMap;

        }
        catch(FileNotFoundException e){
            // l_logObject.setStatus(false, "Error: File not found");
            // l_logEntryBuffer.notifyClasses(l_logObject);
            e.printStackTrace();
            return gameMap;

        }
        catch (IOException e) {
            // l_logObject.setStatus(false, "IO Exception");
            // l_logEntryBuffer.notifyClasses(l_logObject);
            e.printStackTrace();
            return gameMap;

        }
       
    }


    public void printMap(Map gameMap){
 System.out.println("Inside ConquestFileReader: printMap()");
        System.out.println("------Conquest Map ------");
        System.out.println();
        String continent ="";
        List<Node> nodeList = gameMap.getNodes();
        for(Node c : nodeList){
            
            if(c.getData().getContinent().getContinentName() != continent ){
                System.out.println("Continent:"+c.getData().getContinent().getContinentName());
                continent = c.getData().getContinent().getContinentName();
                System.out.println("=======================================================================");
            }
            System.out.print(c.getData().getCountryName()+" : ");
            String borderString ="";
            if(c.getBorders() != null &&  c.getBorders().size()> 0  ){
                List<Node> listOfBorders = c.getBorders();

                for(Node border : listOfBorders ){
                
                borderString+=border.getData().getCountryName()+" -> ";
                
            } 
                borderString=borderString.substring(0,borderString.length()-4);
                System.out.println(borderString);    
                System.out.println();  
            }
                             

                    
            }
    }



}
