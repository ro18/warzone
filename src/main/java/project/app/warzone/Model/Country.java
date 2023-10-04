package project.app.warzone.Model;

/**
 * This class is a prototype of country
 */
public class Country {

    private String CountryName;
    private int ownerId;
    private int numberOfArmies;
    private Continent continent;


    public Country() {}

    public Country(String CountryName, Continent continent, int ownerId, int numberOfArmies) {
        this.CountryName = CountryName;
        this.ownerId = ownerId;
        this.numberOfArmies = numberOfArmies;
        this.continent = continent;
    }

    public Country(String CountryName, Continent continent) {
        this.CountryName = CountryName;
        this.continent = continent;
    }

    public String getCountryName() {
        return CountryName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getNumberOfArmies() {
        return numberOfArmies;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setCountryName(String newCountryName) {
        CountryName = newCountryName;
    }

    public void setOwnerId(int newOwnerId) {
        ownerId = newOwnerId;
    }

    public void setNumberOfArmies(int newNumberOfArmies) {
        numberOfArmies = newNumberOfArmies;
    }

    public void setContinent(Continent newContinent) {
        continent = newContinent;
    }
    
}
