package project.app.warzone.Features;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Model.Continent;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Node;;

@Component
public class MapFeatures {

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
                        gameMap.createAndInsertTerritory(countryDetails[1],continentsList.get(Integer.parseInt(countryDetails[2])-1) );
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
            if(c.getBorders().size()> 0 &&  c.getBorders() != null){
                List<Node> listOfBorders = c.getBorders();

                for(Node border : listOfBorders ){
                
                borderString+=border.getData().getTerritoryName()+" -> ";
                
            } 
                borderString=borderString.substring(0,borderString.length()-4);
                System.out.println(borderString);    
                System.out.println();  
            }
                             

                    
            }
    }

    /**
     * @param p_gameMap
     * @param p_filename
     * @return
     */
    //public Map writeToMap( Map p_gameMap, String p_filename){
    // public Map writeToMap( Map p_gameMap, String p_filename){

    //     BufferedReader l_reader = new BufferedReader(new FileReader(filename));
    //     String l_continents = "[continents]";
    //     String l_countries = "[countries]";
    //     String l_borders="[borders]";

    //     if(l_reader.readLine() == ""){
    //         System.out.println("Writing to an empty map file");
    //         Files.writeString(filename, l_continents);


    //     }

    //}



    public HashMap<Node,Boolean> validateByNodes(List<Node> p_allNodes, HashMap<Node,Boolean> l_visitedList){

        for( Node l_currentNode : p_allNodes){ 
            if(!l_visitedList.keySet().contains(l_currentNode)){
                l_visitedList.put(l_currentNode,false);
            }
          

        }

       Node n = l_visitedList.entrySet().iterator().next().getKey();
       depthFirstSearch(l_visitedList.entrySet().iterator().next().getKey(),l_visitedList);

       return l_visitedList;

    }

    public Boolean validateEntireGraph(GameEngine gameEngine){


        System.out.println();
        System.out.println("------------------------------");

        System.out.println("Validating map...");
        HashMap<Node,Boolean> l_visitedList = new HashMap<Node,Boolean>();
        List<Continent> l_listOfContinent = gameEngine.gameMap.getListOfContinents();
        List<Node> l_listOfNodes = gameEngine.gameMap.getNodes();

        for(Continent con : l_listOfContinent){

            Boolean l_result = validateSubGraph(con, l_listOfNodes,l_visitedList);
            if(!l_result){
                return false;
            }            

        }
                        
        

        System.out.println("Final visited list:");
         for(Node n : l_visitedList.keySet()){

            System.out.println(n.getData().getTerritoryName()+":"+l_visitedList.get(n));

        }

        return true;

        

    }

    public boolean validateSubGraph(Continent con, List<Node> l_listOfNodes,HashMap<Node,Boolean> l_visitedList){

        List<Node> l_nodesOfContinent = l_listOfNodes.stream().filter(c-> c.getData().getContinent().equals(con)).toList();
        l_visitedList =validateByNodes(l_nodesOfContinent,l_visitedList);
        for( Node n : l_nodesOfContinent){
            if(l_visitedList.containsKey(n) && !l_visitedList.get(n)){
                System.out.println(con.getContinentName()+" is not connected");
                System.out.println(n.getData().getTerritoryName()+" cannot be reached");
                return false;
            }
        }

        return true;


    }

    private HashMap<Node,Boolean> depthFirstSearch(Node currentCountry, HashMap<Node,Boolean> l_visitedList){

        l_visitedList.put(currentCountry,true);

        List<Node> l_listOfBorderNodes = currentCountry.getBorders();

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

        return l_visitedList;
    }
}
