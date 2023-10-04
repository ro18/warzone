package project.app.warzone.Utilities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


/**
 * This class stores the current database for user to create custom maps
 */
@Component
public class MapResouces {
    Map<Integer, String> listofContinents= new HashMap<Integer,String>();
    Map<Integer, String> listofCountries= new HashMap<Integer, String>();

    /**
     * Constructor for MapResources
     */
    public MapResouces(){

        setAllContinents();
        setAllCountries();
       

    }


    /**
     * method to set all continents
     */
    public void setAllContinents(){

        listofContinents.put(1, "North_Europe");
        listofContinents.put(2, "East_Europe");
        listofContinents.put(3, "South_Europe");
        listofContinents.put(4, "West_Europe");

    }


    
    /**
     * method to set all countries
     */
    public void setAllCountries(){

        listofCountries.put(1, "England");
        listofCountries.put(2, "Scotland");
        listofCountries.put(3, "N_Ireland");
        listofCountries.put(4, "Rep_Ireland");
        listofCountries.put(5, "Wales");
        listofCountries.put(6, "Belgium");
        listofCountries.put(7, "Netherlands");
        listofCountries.put(8, "Denmark");
        listofCountries.put(9, "Germany");
        listofCountries.put(10, "Poland");
        listofCountries.put(11, "Czech_Rep");
        listofCountries.put(12, "Slovakia");
        listofCountries.put(13, "Hungary");
        listofCountries.put(14, "Austria");
        listofCountries.put(15, "Switzerland");
        listofCountries.put(16, "Italy");
        listofCountries.put(17, "Sicily");
        listofCountries.put(18, "Sardinia");
        listofCountries.put(19, "Corsica");
        listofCountries.put(20, "Majorca");
        listofCountries.put(21, "France");
        listofCountries.put(22, "Spain");
        listofCountries.put(23, "Portugal");
        listofCountries.put(24, "Luxembourg");
    }

    /**
     * @return      returns list of continents
     */
    public Map<Integer, String>getAllContinents(){
     return listofContinents;

    }


    /**
     * @return      returns list of country
     */
    public Map<Integer, String>getAllCountries(){
     return listofCountries;

    }

    /**
     * @param p_detailsOfMap        storing details of map
     */
    public void printMapDetails(Map<Integer, String>  p_detailsOfMap){

        for(Integer key : p_detailsOfMap.keySet()){
            System.out.println(key+":"+p_detailsOfMap.get(key));

        }
      

    }

}
