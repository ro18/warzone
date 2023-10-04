package project.app.warzone.Model;
/**
 * This class is used to store the territories for the map
 */
public class Continent {

    private String d_continentName;
    private Integer d_bonus;

    /**
     * Intializing continentName 
     * 
     * @param p_continentName         continent name
     */
    public Continent(String p_continentName) {
        this.d_continentName = p_continentName;
    }

    /**
     * Intializing continentName and bonus
     * 
     * @param p_continentName         continent name
     * @param p_bonus                 bonus value
     */
    public Continent(String p_continentName, Integer p_bonus) {
        this.d_continentName = p_continentName;
        this.d_bonus = p_bonus;
    }

    
    /** 
     * @return String              returns Continent name 
     */
    public String getContinentName() {
        return d_continentName;            
    }


    
    /** 
     * @return int                returns Bonus value
     */
    public int getBonus() {
        return d_bonus;                      
    }

     /** 
     * @param p_newBonus            storing new Bonus value to be set
     */
    public void setBonus(int p_newBonus) {
        d_bonus = p_newBonus;
    }
    
    
    /** 
     * @param p_newContinentName          storing new continent name to be set
     */
    public void setContinentName(String p_newContinentName) {
        d_continentName = p_newContinentName;
    }


    
   
}
