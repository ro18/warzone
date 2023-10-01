package project.app.warzone.Utilities;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import project.app.warzone.Model.Continent;

public class MapResouces {
    Dictionary<Integer, String> listofContinents= new Hashtable<Integer,String>();
    Dictionary<Integer, String> listofTerritories= new Hashtable();

    public MapResouces(){
       

    }


    public void setAllContinents(){

        listofContinents.put(1, "North_Europe");
        listofContinents.put(2, "East_Europe");
        listofContinents.put(3, "South_Europe");
        listofContinents.put(4, "West_Europe");

    }


    public Dictionary<Integer,String> getAllContinents(){
     return listofContinents;

    }

    public void setAllCountries(){

        listofContinents.put(1, "North_Europe");
        listofContinents.put(2, "East_Europe");
        listofContinents.put(3, "South_Europe");
        listofContinents.put(4, "West_Europe");

    }


    public Dictionary<Integer,String> getAllCountries(){
     return listofContinents;

    }


}
