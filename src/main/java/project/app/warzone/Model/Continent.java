package project.app.warzone.Model;
/**
 * This class is used to store the territories for the map
 */
public class Continent {

    private String continentName;
    private Integer bonus;

    public Continent(String continentName) {
        this.continentName = continentName;
    }

    public Continent(String continentName, Integer bonus) {
        this.continentName = continentName;
        this.bonus = bonus;
    }

    
    /** 
     * @return String
     */
    public String getContinentName() {
        return continentName;
    }


    
    /** 
     * @return int
     */
    public int getBonus() {
        return bonus;
    }

     /** 
     * @param newBonus
     */
    public void setBonus(int newBonus) {
        bonus = newBonus;
    }
    
    
    /** 
     * @param newContinentName
     */
    public void setContinentName(String newContinentName) {
        continentName = newContinentName;
    }


    
   
}
