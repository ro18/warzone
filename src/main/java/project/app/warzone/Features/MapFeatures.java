package project.app.warzone.Features;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import project.app.warzone.Model.Continent;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Node;
import project.app.warzone.Utilities.MapResouces;;

@Component
public class MapFeatures {

    public MapResouces mapResouces;

    public MapFeatures(MapResouces mapResouces){
        this.mapResouces = mapResouces;

    }

    public Map readMap(String filename){

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

                    while(!l_line.equals("")){

                        String[] countryDetails = l_line.split(" ");
                        gameMap.createAndInsertTerritory(countryDetails[1],continentsList.get(Integer.parseInt(countryDetails[2])-1) );
                        l_line= reader.readLine();


                    }
                    
                    nodesList = gameMap.getNodes();
                
                    
                
                }
               

                if(l_line.equals("[borders]")){

                    l_line=reader.readLine();

                    while( l_line != null){
                        
                        List<Node> bordersToConnect = new ArrayList<>();
                        String[] borderDetails = l_line.split(" ");
                        Node currentTerritory = nodesList.get(Integer.parseInt(borderDetails[0])-1);
                        
                        for(int i=1 ; i< borderDetails.length;i++){
                            Node node=nodesList.get(Integer.parseInt(borderDetails[i])-1);
                            bordersToConnect.add(node);
                        }
                        gameMap.addEdgesOfTerritory(currentTerritory, bordersToConnect);
                        l_line=reader.readLine();
                        

                    }

                    // nodesList = gameMap.getNodes();
                    
                    // for(Node c : nodesList){
                    // System.out.print(c.getData().getTerritoryName()+":");
                    // String borderString ="";
                    //         List<Node> listOfBorders = c.getBorders();
                    //         for(Node border : listOfBorders ){
                                
                    //             borderString+=border.getData().getTerritoryName()+"->";
                                
                    //         } 
                    //         borderString=borderString.substring(0,borderString.length()-2);
                    //         System.out.println(borderString);                       

                            
                    // }

                }

                



            }   
       
            reader.close();
            printMap(gameMap);
            return gameMap;

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return gameMap;

        }
        catch (IOException e) {
            e.printStackTrace();
            return gameMap;

        }
       
    }

    public void createMap(){
        
    }

    // public void saveChangesToFile(String p_filename) throws IOException{
    //     BufferedReader reader = new BufferedReader(new FileReader(p_filename));
    //     StringBuilder content = new StringBuilder(); 
    //     String line;
    //         while ((line = reader.readLine())) {
                
    //             line = line.replace("old text", "new text");
    //             content.append(line).append("\n");
    //         }

    //         reader.close();      

    // }
    
    

    public void printMap(Map gameMap){

        System.out.println("------ Map ------");
        System.out.println();
        String continent ="";
        List<Node> nodeList = gameMap.getNodes();
        for(Node c : nodeList){
            
            if(c.getData().getContinent().getContinentName() != continent ){
                System.out.println("Continent:"+c.getData().getContinent().getContinentName());
                continent = c.getData().getContinent().getContinentName();
                System.out.println("=======================================================================");
            }
            System.out.print(c.getData().getTerritoryName()+" : ");
            String borderString ="";
            List<Node> listOfBorders = c.getBorders();
            for(Node border : listOfBorders ){
                
                borderString+=border.getData().getTerritoryName()+" -> ";
                
            } 
            borderString=borderString.substring(0,borderString.length()-4);
            System.out.println(borderString);    
            System.out.println();                   

                    
            }
    }

    /**
     * @param p_gameMap
     * @param p_filename
     * @return
     */
    public Map writeToMap( Map p_gameMap, String p_filename){

        // BufferedReader l_reader = new BufferedReader(new FileReader(p_filename));
        // String l_continents = "[continents]";
        // String l_countries = "[countries]";
        // String l_borders="[borders]";

        // if(l_reader.readLine() == ""){
        //     System.out.println("Writing to an empty map file");
        //     //Files.writeString(p_filename, l_continents);


        // }

        return p_gameMap;

    }



    // public void validateByNodes(List<Node> p_allNodes, Map<Node,Integer> l_visitedList){

  
    //     //List<Node> l_allNodes = p_gameMap.getNodes();


    //     for( Node l_currentNode : l_allNodes){ // validating continent by continent           
    //         l_visitedList.add(l_currentNode,false);

    //     }
    //     Map.Entry<String,String> entry = map.entrySet().iterator().next();

    //     depthFirstSearch(entry.getKey(),l_visitedList);

    // }

    // public void validateEntireGraph(GameEngine gameEngine){


    //     System.out.println(" Running check on file:");
    //     Map<Node,boolean> l_visitedList = new HashMap<Node,boolean>();
    //     List<Continent> l_listOfContinent = gameEngine.gameMap.getListOfContinents();
    //     List<Node> l_listOfNodes = gameEngine.gameMap.getNodes();

    //     List<Node> l_nodesOfContinent = new ArrayList<>();


    //     for(Continent con : l_listOfContinent){

    //         validateSubGraph(con, l_listOfNodes,l_visitedList);          
            
    //     }

    // }

    // public void validateSubGraph(Continent con, List<Node> l_listOfNodes,Map<Node,Integer> l_visitedList){

    //     List<Node> l_nodesOfContinent = l_listOfNodes.stream().filter(c-> c.continent.equals(con));
    //     validateMap(l_nodesOfContinent,l_visitedList)


    // }

    // private Map<Node,Integer> depthFirstSearch(Node currentCountry, Map<Node,Integer> l_visitedList){

    //     l_visitedList.put(l_visitedList.get(currentCountry),true);
    //     List<Node> l_listOfBorderNodes = l_visitedList.getBorders();

    //     for(Node node : l_listOfBorderNodes){
    //         if(l_visitedList.get(node) != true){
    //             depthFirstSearch(node,l_visitedList);
    //         }
    //     }
    // }

    /**
     * @param listofContinents
     * @param gameEngine
     * @throws IOException
     */
    public void writeContinentsToFile(java.util.Map<String, String> listofContinents,GameEngine gameEngine) throws IOException{

        String l_mapLocation=gameEngine.gameMap.getMapDirectory()+"/"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map"; //mac
        java.util.Map<Integer, String> listOfContinentsResource = mapResouces.getAllContinents();

         try(BufferedWriter writer = new BufferedWriter(new FileWriter(l_mapLocation, true))){

            BufferedReader l_reader = new BufferedReader(new FileReader(l_mapLocation));
            List<String> l_lineToWrite =new ArrayList<String>();
            String l_line = l_reader.readLine();
            if(l_line == null){
                
                l_lineToWrite.add("[continents]");
            }
            else if(l_line == "[continents]"){
                l_line=l_reader.readLine();
                while(l_line != " "){
                   l_line=l_reader.readLine();
                           
              }
                  
       }
        l_reader.close(); 
       for(String continentId : listofContinents.keySet()){
                
                l_lineToWrite.add(listOfContinentsResource.get(Integer.parseInt(continentId))+" "+listofContinents.get(continentId));
;
            }

       for(String line : l_lineToWrite){
                writer.append(line);
                writer.newLine();
                writer.flush();
                }
            writer.write(System.getProperty("line.separator"));
            writer.close();

            
                
   }catch(IOException e){
    e.printStackTrace();
   }
  }

 public void writeCountriesToFile(java.util.Map<String, String> listofCountries,GameEngine gameEngine)throws IOException{
   String l_mapLocation=gameEngine.gameMap.getMapDirectory()+"/"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map"; //mac
        
        java.util.Map<Integer, String> listOfCountriesResource = mapResouces.getAllCountries();
                    List<String> l_lineToWrite =new ArrayList<String>();

        try(BufferedReader l_reader = new BufferedReader(new FileReader(l_mapLocation));){

            
            String l_line = l_reader.readLine();

            while(l_line!=null && !l_line.toString().equals("[countries]") ){

                l_line=l_reader.readLine();

            }
            if(l_line==null){
                l_lineToWrite.add("\n");
                l_lineToWrite.add("[countries]");

                
                }

            else{
                l_line=l_reader.readLine();
                while(l_line != "" && l_line != null){
                   l_line=l_reader.readLine();
                 }
            }
            l_reader.close(); 

            }
           

                  
        catch(IOException e){
    e.printStackTrace();


       }

       try(BufferedWriter writer = new BufferedWriter(new FileWriter(l_mapLocation, true))){
       for(String countryId : listofCountries.keySet()){
                
                l_lineToWrite.add(countryId+" "+listOfCountriesResource.get(Integer.parseInt(countryId))+" "+listofCountries.get(countryId));
                System.out.println((listofCountries.get(countryId)+" "+listOfCountriesResource.get(Integer.parseInt(countryId))+" "+listofCountries.get(countryId)));

            }

       for(String line : l_lineToWrite){
                writer.append(line);
                writer.newLine();
                writer.flush();
                }
            writer.write(System.getProperty("line.separator"));
            writer.close();

            }
        catch(IOException e){
      e.printStackTrace();

        }         
                
   }



 public void writeCountriesNeighborToFile(java.util.Map<String, String> listofNeighBours,GameEngine gameEngine)throws IOException{

    String l_mapLocation=gameEngine.gameMap.getMapDirectory()+"/"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map"; //mac

    File inputFile = new File(l_mapLocation);
    File tempFile = new File(gameEngine.gameMap.getMapDirectory()+"/tempMap.map");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;
    //List<String> countriesToremoved = listofNeighBours;
    do{
        currentLine = reader.readLine();
        if(currentLine == null){
            writer.write("[borders]" + System.getProperty("line.separator"));

            break;
        } 
        writer.write(currentLine + System.getProperty("line.separator"));
    }
    while(!currentLine.toString().equals("[borders]"));

    while(currentLine != null){

        System.out.println(currentLine.toString().split(" ")[0]);
        if(listofNeighBours.keySet().contains(currentLine.split(" ")[0]))
        {
            currentLine = currentLine +" "+listofNeighBours.get((currentLine.split(" ")[0]));
            listofNeighBours.remove(currentLine.split(" ")[0]);


            writer.write(currentLine + System.getProperty("line.separator"));


        }
        currentLine= reader.readLine();
    } 

    for(String l_i : listofNeighBours.keySet()){
        currentLine = l_i+" "+listofNeighBours.get(l_i);

        writer.write(currentLine+System.getProperty("line.separator"));


    }
    writer.write(System.getProperty("line.separator"));

    writer.close(); 
    reader.close(); 
    inputFile.delete();
    tempFile.renameTo(inputFile);
            

}

public void removeCountriesFromFile(List<String> listofCountries,GameEngine gameEngine)throws IOException{

    // Read file in which we have to remove countries
    // Push the line as string to list of string
    // Iterate through the list of string and remove the line which contains the country
    // Write the list of string to the file
    System.out.println(listofCountries.get(0));
    
    String l_mapLocation=gameEngine.gameMap.getMapDirectory()+"/"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map"; //mac
    // System.out.println(l_mapLocation);

    File inputFile = new File(l_mapLocation);
    File tempFile = new File(gameEngine.gameMap.getMapDirectory()+"/tempMap.map");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;
    Set<String> countriesToremoved = new HashSet<>(listofCountries);
    System.out.println(countriesToremoved);
    do{
        currentLine = reader.readLine();
        if(currentLine == null) break;
        writer.write(currentLine + System.getProperty("line.separator"));
    }
    while(!currentLine.toString().equals("[countries]"));
        
    while((currentLine = reader.readLine()) != null) {
        System.out.println(currentLine);
        
        System.out.println(currentLine.split(" ")[0]);
        System.out.println(countriesToremoved.contains(currentLine.split(" ")[0]));
        if(countriesToremoved.contains(currentLine.split(" ")[0])) continue;
        writer.write(currentLine + System.getProperty("line.separator"));
    }
    writer.close(); 
    reader.close(); 
    inputFile.delete();
    tempFile.renameTo(inputFile);

   }


   public void removeContinentFromFile(List<String> listofContinent,GameEngine gameEngine)throws IOException{

    java.util.Map<Integer, String> listOfContinentsResource = mapResouces.getAllContinents();
    System.out.println(listofContinent.get(0));
    

    String l_mapLocation=gameEngine.gameMap.getMapDirectory()+"/"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map"; //mac

    File inputFile = new File(l_mapLocation);
    File tempFile = new File(gameEngine.gameMap.getMapDirectory()+"/tempMap.map");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;
    Set<String> continentsToremoved = new HashSet<>(listofContinent);
    System.out.println(continentsToremoved);
    // change here

    do{
        currentLine = reader.readLine();
        if(currentLine == null) break;
        // add a skip line
        writer.write(currentLine + System.getProperty("line.separator"));
    }
    while(!currentLine.toString().equals("[continents]"));
        

    while((currentLine = reader.readLine()) != null) {
        System.out.println(currentLine);        
        System.out.println(currentLine.split(" ")[0]);
        System.out.println(continentsToremoved.contains(currentLine.split(" ")[0]));
        //String l_continentName  = listOfContinentsResource.get()
        if(continentsToremoved.contains(currentLine.split(" ")[0])) continue;

        writer.write(currentLine + System.getProperty("line.separator"));
    }
    writer.close(); 
    reader.close(); 
    inputFile.delete();
    tempFile.renameTo(inputFile);         
            
}


public void removeborderFromFile(java.util.Map<String, String> listofNeighBours,GameEngine gameEngine)throws IOException{
    
    String l_mapLocation=gameEngine.gameMap.getMapDirectory()+"/"+gameEngine.gameMap.get_USER_SELECTED_FILE()+".map"; //mac

    File inputFile = new File(l_mapLocation);
    File tempFile = new File(gameEngine.gameMap.getMapDirectory()+"/tempMap.map");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;

    do{
    currentLine = reader.readLine();
    if(currentLine == null) break;
    writer.write(currentLine + System.getProperty("line.separator"));
    }
    while(!currentLine.toString().equals("[borders]"));


     while(currentLine != null){

        String finalString="";

        if(listofNeighBours.keySet().contains(currentLine.split(" ")[0]))
        {
            String[] mainString = currentLine.split(" ");
            String l_ToRemove = listofNeighBours.get(mainString[0]);
            for(int i=1 ; i < mainString.length ;i++){
                if(l_ToRemove.contains(mainString[i].toString())){
                    mainString[i]="";
                }
            }
            for(String s : mainString){
                finalString=finalString+" "+s;
            }
            currentLine=finalString.trim();
           



        }
        writer.write(currentLine + System.getProperty("line.separator"));

        currentLine= reader.readLine();
    }
    
    writer.close(); 
    reader.close(); 
    inputFile.delete();
    tempFile.renameTo(inputFile);     


}


}

