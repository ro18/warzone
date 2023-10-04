package project.app.warzone.Model;
/**
 * This class is used to store the territories for the map
 */
public class Continent {

    private String continentName;
    private Integer bonus;

    /**
     * Intializing continentName 
     * 
     * @param continentName         continent name
     */
    public Continent(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Intializing continentName and bonus
     * 
     * @param continentName         continent name
     * @param bonus                 bonus value
     */
    public Continent(String continentName, Integer bonus) {
        this.continentName = continentName;
        this.bonus = bonus;
    }

    
    /** 
     * @return String              returns Continent name 
     */
    public String getContinentName() {
        return continentName;            
    }


    
    /** 
     * @return int                returns Bonus value
     */
    public int getBonus() {
        return bonus;                      
    }

     /** 
     * @param newBonus            storing new Bonus value to be set
     */
    public void setBonus(int newBonus) {
        bonus = newBonus;
    }
    
    
    /** 
     * @param newContinentName          storing new continent name to be set
     */
    public void setContinentName(String newContinentName) {
        continentName = newContinentName;
    }


    
   
}
