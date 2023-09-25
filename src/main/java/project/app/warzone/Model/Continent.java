package project.app.warzone.Model;

public class Continent {

    private String continentName;
    private int bonus;

    public Continent(String continentName) {
        this.continentName = continentName;
    }

    public Continent(String continentName, int bonus) {
        this.continentName = continentName;
        this.bonus = bonus;
    }

    public String getContinentName() {
        return continentName;
    }

    public int getBonus() {
        return bonus;
    }

    public void setContinentName(String newContinentName) {
        continentName = newContinentName;
    }

    public void setBonus(int newBonus) {
        bonus = newBonus;
    }
    
}
