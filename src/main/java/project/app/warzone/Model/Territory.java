package project.app.warzone.Model;

public class Territory {

    private String territoryName;
    private int ownerId;
    private int numberOfArmies;
    private Continent continent;


    public Territory() {}

    public Territory(String territoryName, Continent continent, int ownerId, int numberOfArmies) {
        this.territoryName = territoryName;
        this.ownerId = ownerId;
        this.numberOfArmies = numberOfArmies;
        this.continent = continent;
    }

    public Territory(String territoryName, Continent continent) {
        this.territoryName = territoryName;
        this.continent = continent;
    }

    public String getTerritoryName() {
        return territoryName;
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

    public void setTerritoryName(String newTerritoryName) {
        territoryName = newTerritoryName;
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
