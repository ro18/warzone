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
       // System.out.println("Inside ConquestFileReader: readMap()");
        //  LogObject l_logObject = new LogObject();
        //  l_logObject.setD_command("showmap");
        // l_logEntryBuffer.addObserver((Observer) this);

        String l_line="";
        List<Continent> continentsList = new ArrayList<>();
        List<Node> nodesList = new ArrayList<>();
        Map gameMap = new Map();
        try{

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            l_line=reader.readLine();
            while ( l_line !=null ) {
                
                if(l_line.equals("[Map]")){
                    do{
                    l_line=reader.readLine();

                    }while(!l_line.equals(""));
                }
                if(l_line.equals("[Continents]")){
                        l_line=reader.readLine();

                        while(!l_line.equals("")){
                            String[] continentDetails =l_line.split("=");
                            gameMap.createContinent(continentDetails[0], Integer.parseInt((continentDetails[1])));
                            l_line=reader.readLine();
                        };
                        continentsList= gameMap.getListOfContinents();
                    }     
              
                if(l_line.equals("[Territories]")){

                    l_line=reader.readLine();
                    int i=0;
                    int countryId = -1;
                   // int k=1;
                      String[][] listOfCountries= new String[28][20]; 
                      // This loop is used to create the countries
                    while(l_line != null){
                        String[] countryDetails = l_line.split(",");
                        for(int j=0; j<countryDetails.length; j++){
                            listOfCountries[i][j]=countryDetails[j];

                            for(int k=0; k<continentsList.size(); k++){
                                if(continentsList.get(k).getContinentName().equals(countryDetails[3])){
                                    countryId=k;
                                    break;
                                }
                            }

                        }
                        gameMap.createAndInsertCountry(i++,countryDetails[0],continentsList.get(countryId) );
                          
                      
                        l_line= reader.readLine();
                    }
                    nodesList = gameMap.getNodes();      
                    for(int k=0; k<listOfCountries.length; k++){

                        List<Node> bordersToConnect = new ArrayList<>();
                        Node currentTerritory = nodesList.get(k); //setting an id for borders
                        for(int j=0; j < listOfCountries[k].length ;j++)
                        {
                            // listOfCountries[k][j] is the name of the country
                            for(i=0; i<listOfCountries.length; i++){
                                if(listOfCountries[i][0].equals(listOfCountries[k][j])){
                                    Node node=nodesList.get(i);
                                    bordersToConnect.add(node);
                                    break;
                                }
                            }
                            gameMap.addEdgesOfCountry(currentTerritory, bordersToConnect);
                        }
                    }

                }
               
                l_line=reader.readLine();
              

            }   
       
            reader.close();
            //  l_logObject.setStatus(true, "User printed map " + filename.split("/")[filename.split("/").length-1].split(".map")[0]);
            //  l_logEntryBuffer.notifyClasses(l_logObject);
            printMap(gameMap);
            return gameMap;

        }
        catch(FileNotFoundException e){
            //  l_logObject.setStatus(false, "Error: File not found");
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
 //System.out.println("Inside ConquestFileReader: printMap()");
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
