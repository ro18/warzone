package project.app.warzone.Model;

/**
 * This class is a prototype of country
 */
public class Country {

    private String CountryName;
    private int ownerId;
    private int numberOfArmies;
    private Continent continent;
    private int countryId;


    public Country() {}         //default constructor

    /**
     * Constructor for initializing countryname, continent, ownerid, numberofarmies
     * 
     * @param CountryName
     * @param continent
     * @param ownerId
     * @param numberOfArmies
     */
    public Country(String CountryName, Continent continent, int countryId,int ownerId, int numberOfArmies) {
        this.CountryName = CountryName;
        this.ownerId = ownerId;
        this.numberOfArmies = numberOfArmies;
        this.continent = continent;
        this.countryId = countryId;
    }

    /**
     * Constructor for initializing countryname
     * 
     * @param CountryName
     * @param continent
     */
    public Country(int countryId,String CountryName, Continent continent) {
        this.CountryName = CountryName;
        this.continent = continent;
        this.countryId=countryId;
    }

    public String getCountryName() {
        return CountryName;             //returns Countryname
    }

    public int getOwnerId() {
        return ownerId;                 //returns ownerId
    }

    public int getNumberOfArmies() {
        return numberOfArmies;          //returns numberofArmies
    }

    public int getCountryId(){
        return countryId;
    }

    public Continent getContinent() {
        return continent;               //returns continent
    }

    public void setCountryName(String newCountryName) {
        CountryName = newCountryName;       //used for setting newCountryname
    }

    public void setOwnerId(int newOwnerId) {
        ownerId = newOwnerId;               //used for setting newOwnerId
    }

    public void setNumberOfArmies(int newNumberOfArmies) {
        numberOfArmies = newNumberOfArmies;         //used for setting newNumberofArmies
    }

    public void setContinent(Continent newContinent) {
        continent = newContinent;                   //used for setting newContinent
    }
    
}
