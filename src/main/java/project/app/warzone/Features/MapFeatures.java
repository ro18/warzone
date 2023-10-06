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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import project.app.warzone.Model.Continent;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Node;
import project.app.warzone.Utilities.MapResources;;

/**
 * This class stores all the map-related functions in gameplay
 */
@Component
public class MapFeatures {

    public MapResources mapResouces;

    public MapFeatures(MapResources mapResouces){
        this.mapResouces = mapResouces;

    }

    
    /** 
     * used for reading map
     * 
     * @param filename      used for storing file name
     * @return Map          returns gamemap
     */
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

    
    /** 
     *used for displaying map 
     *
     * @param gameMap       used for storing game map
     */
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


    
    /**
     * used for validation 
     * 
     * @param p_allNodes                storing list of all nodes
     * @param l_visitedList             storing list of visited nodes
     * {@literal (@return Map<Node, Boolean>)}      storing map
     */
    public java.util.Map<Node,Boolean> validateByNodes(List<Node> p_allNodes, java.util.Map<Node,Boolean> l_visitedList){

        for( Node l_currentNode : p_allNodes){ 
            if(!l_visitedList.keySet().contains(l_currentNode)){
                l_visitedList.put(l_currentNode,false);
            }
          

        }

       Node n = l_visitedList.entrySet().iterator().next().getKey();
       depthFirstSearch(l_visitedList.entrySet().iterator().next().getKey(),l_visitedList);

       return l_visitedList;

    }


    
    /**
     * used for validating entire graph 
     * 
     * @param gameEngine           storing gameEngine
     * @return Boolean             returns the status of validating
     */
    public Boolean validateEntireGraph(GameEngine gameEngine){


        System.out.println();
        System.out.println("------------------------------");

        System.out.println("Validating map...");
        java.util.Map<Node,Boolean> l_visitedList = new LinkedHashMap<Node,Boolean>();

        List<Continent> l_listOfContinent = gameEngine.gameMap.getListOfContinents();
        List<Node> l_listOfNodes = gameEngine.gameMap.getNodes();

        for(Continent con : l_listOfContinent){
            
            Boolean l_result = validateSubGraph(con, l_listOfNodes,l_visitedList);
            if(!l_result){
                return false;
            }            

        }

        return true;

        

    }


    
    /** 
     * used for validating sub-graph
     * 
     * @param con                       storing continent
     * @param l_listOfNodes             storing list of nodes
     * @param l_visitedList             storing list of visited nodes
     * @return boolean                  returns the status of validating
     */
    public boolean validateSubGraph(Continent con, List<Node> l_listOfNodes,java.util.Map<Node,Boolean> l_visitedList){

        List<Node> l_nodesOfContinent = l_listOfNodes.stream().filter(c-> c.getData().getContinent().equals(con)).toList();
        if(l_nodesOfContinent.size() == 0){
            return false;
        }
        l_visitedList =validateByNodes(l_nodesOfContinent,l_visitedList);
        for( Node n : l_nodesOfContinent){
            if(l_visitedList.containsKey(n) && !l_visitedList.get(n)){
                System.out.println(con.getContinentName()+" is not connected");
                System.out.println(n.getData().getCountryName()+" cannot be reached");
                return false;
            }
        }

        return true;


    }


    
    /** 
     * used for implementing DFS
     * 
     * @param currentCountry        storing current country
     * @param l_visitedList         storing list of visited list
     * @return Map<Node, Boolean>   returns status    
     */
    private java.util.Map<Node,Boolean> depthFirstSearch(Node currentCountry, java.util.Map<Node,Boolean> l_visitedList){

        l_visitedList.put(currentCountry,true);

        List<Node> l_listOfBorderNodes = currentCountry.getBorders();

        if(l_listOfBorderNodes != null && l_listOfBorderNodes.size() > 0){

            for( Node l_currentNode : l_listOfBorderNodes){ 
                
                if(!l_visitedList.keySet().contains(l_currentNode)){
                l_visitedList.put(l_currentNode,false);

                }        

            }


            for(Node node : l_listOfBorderNodes){
                if(l_visitedList.get(node) != true){
                    depthFirstSearch(node,l_visitedList);
                }
            }

        }
       

        return l_visitedList;
    }

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
            //writer.write(System.getProperty("line.separator"));
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
                    l_lineToWrite.add( System.getProperty("line.separator"));

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
            //writer.write(System.getProperty("line.separator"));
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
            writer.write( System.getProperty("line.separator"));
            writer.write("[borders]" + System.getProperty("line.separator"));

            break;
        } 
        writer.write(currentLine + System.getProperty("line.separator"));
    }
    while(!currentLine.toString().equals("[borders]"));

    while(currentLine != null){

        //System.out.println(currentLine.toString().split(" ")[0]);
        if(listofNeighBours.keySet().contains(currentLine.split(" ")[0]))
        {
            currentLine = currentLine +" "+listofNeighBours.get((currentLine.split(" ")[0]));
            listofNeighBours.remove(currentLine.split(" ")[0]);


            //writer.write(currentLine + System.getProperty("line.separator"));
            


        }

        if(!currentLine.toString().equals("[borders]")){
            writer.write(currentLine + System.getProperty("line.separator"));

        }

        currentLine= reader.readLine();

    } 

    for(String l_i : listofNeighBours.keySet()){
        currentLine = l_i+" "+listofNeighBours.get(l_i);

        writer.write(currentLine+System.getProperty("line.separator"));


    }

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