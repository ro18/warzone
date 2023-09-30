package project.app.warzone.Features;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Model.Continent;
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

    public Map writeToMap( Map p_gameMap, String p_filename){

        BufferedReader l_reader = new BufferedReader(new FileReader(filename));
        String l_continents = "[continents]";
        String l_countries = "[countries]";
        String l_borders="[borders]";

        if(l_reader.readLine() == ""){
            System.out.println("Writing to an empty map file");
            Files.writeString(filename, l_continents);


        }

    }



    public void validateMap(List<Nodes> p_allNodes){

        System.out.println(" Running check on file:");
        //List<Node> l_allNodes = p_gameMap.getNodes();

        Map<Node,boolean> l_visitedList = new HashMap<Node,boolean>();

        for( Node l_currentNode : l_allNodes){ // validating continent by continent           
            l_visitedList.add(l_currentNode,false);

        }
        Map.Entry<String,String> entry = map.entrySet().iterator().next();
= 
        depthFirstSearch(entry.getKey(),l_visitedList);

    }

    public void validateEntireGraph(GameEngine gameEngine){

        List<Continent> l_listOfContinent = gameEngine.gameMap.getListOfContinents();
        List>

        for(Continent con : l_listOfContinent){

        }

    }

    public void validateSubGraph(){

    }

    private Map<Node,Integer> depthFirstSearch(Node currentCountry, Map<Node,Integer> l_visitedList){

        l_visitedList.put(l_visitedList.get(currentCountry),true);
        List<Node> l_listOfBorderNodes = l_visitedList.getBorders();

        for(Node node : l_listOfBorderNodes){
            if(l_visitedList.get(node) != true){
                depthFirstSearch(node,l_visitedList);
            }
        }
    }
}