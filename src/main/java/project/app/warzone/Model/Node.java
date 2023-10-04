package project.app.warzone.Model;

import java.util.List;

/**
 * This class is the prototype simulating each country
 */
public class Node {

    private Country data;

    private List<Node> borderNodes;

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
     * @return List<Node>
     */
    public List<Node> getBorders() { 
        return borderNodes;
    }


    
    /** 
     * @param data
     */
    public void setData(Country data) {
        this.data = data;
    }


    
    /** 
     * @param borderCountries
     */
    public void addBorderTerritories(List<Node> borderCountries){
        borderNodes=borderCountries;
    }
    
}
