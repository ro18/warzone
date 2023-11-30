package project.app.warzone.Model;

/**
 * This class is a prototype of country
 */
public class Country implements Comparable<Country>{

    private String d_countryName;
    private int d_ownerId;
    private int d_numberOfArmies;
    private Continent d_continent;
    private int d_countryId;

    /**
     * Constructor for Country
     */
    public Country() {
    }

    /**
     * Constructor for initializing countryname, continent, ownerid, numberofarmies
     * 
     * @param p_countryName    storing country name
     * @param p_continent      storing continent
     * @param p_ownerId        storing owner id
     * @param p_numberOfArmies storing number of armies
     * @param p_countryId      storing id of country
     */
    public Country(int p_countryId, String p_countryName, Continent p_continent, int p_ownerId, int p_numberOfArmies) {
        this.d_countryName = p_countryName;
        this.d_ownerId = p_ownerId;
        this.d_numberOfArmies = p_numberOfArmies;
        this.d_continent = p_continent;
        this.d_countryId = p_countryId;
    }

    /**
     * Constructor for initializing countryname
     * 
     * @param p_countryName storing country name
     * @param p_continent   storing continent
     * @param p_countryId   storing country id
     */
    public Country(int p_countryId, String p_countryName, Continent p_continent) {
        this.d_countryName = p_countryName;
        this.d_continent = p_continent;
        this.d_countryId = p_countryId;
        this.d_ownerId = 0;
    }

    
    /** 
     * @param e country
     * @return int
     */
    @Override
	public int compareTo(Country e) {

        
        Integer l_countryArmies = this.getNumberOfArmies();
		return l_countryArmies.compareTo(e.getNumberOfArmies());
	}

    /**
     * @return returns Countryname
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * @return returns ownerId
     */
    public int getOwnerId() {
        return d_ownerId;
    }

    /**
     * @return returns numberofArmies
     */
    public int getNumberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * @return returns countryId
     */
    public int getCountryId() {
        return d_countryId;
    }

    /**
     * @return returns continent
     */
    public Continent getContinent() {
        return d_continent;
    }

    /**
     * @param p_newCountryName used for setting newCountryname
     */
    public void setCountryName(String p_newCountryName) {
        d_countryName = p_newCountryName;
    }

    /**
     * @param p_newOwnerId used for setting newOwnerId
     */
    public void setOwnerId(int p_newOwnerId) {
        d_ownerId = p_newOwnerId;
    }

    /**
     * @param p_newNumberOfArmies used for setting newNumberofArmies
     */
    public void setNumberOfArmies(int p_newNumberOfArmies) {
        d_numberOfArmies = p_newNumberOfArmies;
    }

    /**
     * @param p_newContinent used for setting newContinent
     */
    public void setContinent(Continent p_newContinent) {
        d_continent = p_newContinent;
    }

}
