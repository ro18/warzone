package project.app.warzone.Model;

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

    public String getContinentName() {
        return continentName;
    }

    // public String getBonus() {
    //     return bonus;
    // }

    public void setContinentName(String newContinentName) {
        continentName = newContinentName;
    }

    // public void setBonus(int newBonus) {
    //     bonus = newBonus;
    // }
    
}
