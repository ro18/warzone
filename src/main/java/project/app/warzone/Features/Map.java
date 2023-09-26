package project.app.warzone.Features;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.springframework.stereotype.Component;

@Component
public class Map {


    public void readMap(String filename){

        String l_line;
        String l_nextLine="";
        System.out.println("in map.java");
        Dictionary<String,String> continents = new Hashtable<String,String>();
        Dictionary<String,String> countries = new Hashtable<String,String>();


        try{

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        System.out.println("-- Map --");
        while ((l_line = reader.readLine()) != null) { 

            if(l_line.equals("[continents]")){
            l_line=reader.readLine();
            while(l_line != "\n"){
            String[] continentDetails = l_line.split(" ");
            System.out.println(continentDetails[0]);
            continents.put(continentDetails[0],continentDetails[1]);
            System.out.println("found line");
            l_line= reader.readLine();

            }
             Enumeration<String> keys = continents.keys();

            while(keys.hasMoreElements()){
                
                System.out.println(keys.nextElement()+":"+continents.get(keys.nextElement()));
            }
            
            }

            if(l_line.equals("[countries]")){
            l_line=reader.readLine();
            while(l_line != "\n"){
            String[] continentDetails = l_line.split(" ");
            System.out.println(continentDetails[0]);
            continents.put(continentDetails[0],continentDetails[1]);
            System.out.println("found line");
            l_line= reader.readLine();

            }
             Enumeration<String> keys = continents.keys();

            while(keys.hasMoreElements()){
                
                System.out.println(keys.nextElement()+":"+continents.get(keys.nextElement()));
            }
            
            }

           
          }
       
        reader.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
}
