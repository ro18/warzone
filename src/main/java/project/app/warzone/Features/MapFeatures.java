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
public class MapFeatures 
{

    public Map readMap(String){

        String l_line="";
        System.out.println("in map.java");
        List<Continent> continentsList = new ArrayList<>();
        List<Node> nodesList = new ArrayList<>();
        Map gameMap = new Map();
        try{

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            System.out.println("-- Map --");
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

                    nodesList = gameMap.getNodes();
                    
                    for(Node c : nodesList){
                    System.out.print(c.getData().getTerritoryName()+":");
                    String borderString ="";
                            List<Node> listOfBorders = c.getBorders();
                            for(Node border : listOfBorders ){
                                
                                borderString+=border.getData().getTerritoryName()+"->";
                                
                            } 
                            borderString=borderString.substring(0,borderString.length()-2);
                            System.out.println(borderString);                       

                            
                    }

                }

                



            }
	/*
	Map Validation
	*/
	
	public Boolean Validate() {
        return (!checkForNullObjects() && checkContinentConnectivity() && checkCountryConnectivity());
    }
       
	/* assuming nodelist is country list*/
	public Boolean checkForNullObjects (){
	 if(continentsList==null || continentsList.isEmpty()){
            System.out.println("Invalid Map:Map must possess atleast one continent!");
        }
        if(nodesList==null || nodesList.isEmpty()){
            System.out.println("Invalid Map:Map must possess atleast one country!");
        }
        for(Country c: nodesList){
            if(c.borderString().size()<1){
                System.out.println("Invalid Map: "+c.getTerritoryName()+" does not possess any neighbour, hence isn't reachable!");
            }
        }
        return false;
	}
	
	 /**
     * Checks All Continent's Inner Connectivity.
	 *
	 * @return Boolean Value True if all are connected
	 */
	 /* assuming nodelist is country list*/
	public Boolean checkContinentConnectivity(){
		boolean l_flagConnectivity=true;
		for (Continent c:continentsList){
			if (null == c.nodesList() || c.nodesList().size()<1){
				System.out.println("Invalid Map: "+c.getD_continentName() + " has no countries, it must possess atleast 1 country");
			}
			if(!subGraphConnectivity(c)){
				l_flagConnectivity=false;
			}
		}
		return l_flagConnectivity;
	}	
	
	
    /**
     * Checks Inner Connectivity of a Continent.
     *
     * @param continentsList Continent being checked
     * @return Bool Value if Continent is Connected
     */
    public boolean subGraphConnectivity(Continent continentsList) {
        HashMap<Integer, Boolean> l_continentCountry = new HashMap<Integer, Boolean>();

        for (Country c : continentsList.nodesList()) {
            l_continentCountry.put(c.nodesList(), false);
        }
        dfsSubgraph(continentsList.nodesList().get(0), l_continentCountry, continentsList);

        // Iterates Over Entries to locate unreachable countries in continent
        for (Entry<Integer, Boolean> entry : l_continentCountry.entrySet()) {
            if (!entry.getValue()) {
                Country l_country = nodesList(entry.getKey());
                System.out.println("Invalid Map: " + l_country.getTerritoryName() + " in Continent " + continentsList.getD_continentName() + " is not reachable";
                
            }
        }
        return !l_continentCountry.containsValue(false);
    }

	 /**
     * DFS Applied to the Continent Subgraph.
	 *
	 * @param p_c country visited
     * @param continentsListCountry Hashmap of Visited Boolean Values
     * @param continentsList continent being checked for connectivity
	 
     */	
    public void dfsSubgraph(Country p_c, HashMap<Integer, Boolean> continentsListCountry, Continent continentsList) {
        continentsListCountry.put(p_c.getD_countryId(), true);//to do :create country id obj
        for (Country c : continentsList.getD_countries()) {
            if (p_c.borderDetails().contains(c.getcountryId())) {
                if (!continentsListCountry.get(c.getD_countryId())) {
                    dfsSubgraph(c, continentsListCountry, continentsList);
                }
            }
        }
    }

		/*to do check country conn*/
	}
            reader.close();
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
    
}
