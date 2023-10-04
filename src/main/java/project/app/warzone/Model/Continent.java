package project.app.warzone.Model;
/**
 * This class is used to store the territories for the map
 */
public class Continent {

    private String continentName;
    private Integer bonus;

    //Intializing continentName 
    public Continent(String continentName) {
        this.continentName = continentName;
    }

    //Intializing continentName and bonus
    public Continent(String continentName, Integer bonus) {
        this.continentName = continentName;
        this.bonus = bonus;
    }

    
    /** 
     * @return String
     */
    public String getContinentName() {
        return continentName;              //returns Continent name
    }


    
    /** 
     * @return int
     */
    public int getBonus() {
        return bonus;                      //returns Bonus value
    }

     /** 
     * @param newBonus
     */
    public void setBonus(int newBonus) {
        bonus = newBonus;                 //used for setting new Bonus value
    }
    
    
    /** 
     * @param newContinentName
     */
    public void setContinentName(String newContinentName) {
        continentName = newContinentName;           //used for setting new Continent name
    }


    
   
}
