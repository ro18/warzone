package project.app.warzone.Model;

public class OrderMethods {

    private String d_orderType = ""; // The number of armies to deploy.

    private int d_numberOfArmies; // The number of armies and territory to deploy the armies to.

    private Country d_Country; // The territory associated with the particular order

    /**
     * This method is used to set armies for the particular order
     * 
     * @param p_numArmies storing numberofArmies to set
     */
    public void setL_numberOfArmies(int p_numArmies) {
        this.d_numberOfArmies = p_numArmies;
    }

    /**
     * This method is user to set Country for the particular order
     * 
     * @param p_territory storing territory to set
     */
    public void setL_territory(Country p_territory) {
        this.d_Country = p_territory;
    }

    /**
     * This method is returns the number of armies mentioned in order
     * 
     * @return int
     */
    public int getL_numberOfArmies() {
        return this.d_numberOfArmies;
    }

    /**
     * This method is used to get territory for the particular order
     * 
     * @return Territory
     */
    public Country getL_Country() {
        return this.d_Country;
    }
    
}
