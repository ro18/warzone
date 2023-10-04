package project.app.warzone.Model;

/**
 * This class is a prototype of country
 */
public class Country {

    private String CountryName;
    private int ownerId;
    private int numberOfArmies;
    private Continent continent;


    /**
     * Constructor for Country
     */
    public Country() {}    

    /**
     * Constructor for initializing countryname, continent, ownerid, numberofarmies
     * 
     * @param CountryName           storing country name
     * @param continent             storing continent 
     * @param ownerId               storing owner id
     * @param numberOfArmies        storing number of armies
     */
    public Country(String CountryName, Continent continent, int ownerId, int numberOfArmies) {
        this.CountryName = CountryName;
        this.ownerId = ownerId;
        this.numberOfArmies = numberOfArmies;
        this.continent = continent;
    }

    /**
     * Constructor for initializing countryname
     * 
     * @param CountryName           storing country name
     * @param continent             storing continent
     */
    public Country(String CountryName, Continent continent) {
        this.CountryName = CountryName;
        this.continent = continent;
    }

    /**
     * @return                  returns Countryname
     */
    public String getCountryName() {
        return CountryName;
    }

    /**
     * @return                  returns ownerId
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @return                  returns numberofArmies
     */
    public int getNumberOfArmies() {
        return numberOfArmies;          
    }

    /**
     * @return                  returns continent
     */
    public Continent getContinent() {
        return continent;               
    }

    /**
     * @param newCountryName         used for setting newCountryname
     */
    public void setCountryName(String newCountryName) {
        CountryName = newCountryName;      
    }

    /**
     * @param newOwnerId              used for setting newOwnerId
     */
    public void setOwnerId(int newOwnerId) {
        ownerId = newOwnerId;             
    }

    /**
     * @param newNumberOfArmies       used for setting newNumberofArmies
     */
    public void setNumberOfArmies(int newNumberOfArmies) {
        numberOfArmies = newNumberOfArmies;         
    }

    /**
     * @param newContinent            used for setting newContinent
     */
    public void setContinent(Continent newContinent) {
        continent = newContinent;                   
    }
    
}
