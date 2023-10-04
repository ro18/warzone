package project.app.warzone.Model;

import java.util.List;

/**
 * This class is the prototype simulating each country
 */
public class Node {

    private Country data;

    private List<Node> borderNodes;

    /**
     * counstructor for Node
     * 
     * @param data          storing country data
     */
    public Node(Country data) {
        this.data = data;
    }


    
    /** 
     * @return Country
     */
    public Country getData() {
        return data;
    }


    
    /** 
     * @return Country
     */
    public Country getDataPtr() {
        return data;
    }


    
    /** 
     * @return                 returns bordernodes
     */
    public List<Node> getBorders() { 
        return borderNodes;
    }


    
    /** 
     * @param data                  set country data
     */
    public void setData(Country data) {
        this.data = data;
    }


    
    /** 
     * @param borderCountries       list of border countries
     */
    public void addBorderTerritories(List<Node> borderCountries){
        borderNodes=borderCountries;
    }
    
}
