package project.app.warzone.Model;

/**
 * This class is used to prototye the orders for a player in the game. 
 */
public class Order {

    /**
     * The number of armies to deploy.
     */
    private String d_orderType = "DEPLOY";
    /**
     * The number of armies and territory to deploy the armies to.
     */
    private int d_numberOfArmies;
    private Territory d_Territory;

    public void setL_numberOfArmies(int p_numArmies) {
        this.d_numberOfArmies = p_numArmies;
    }

    public void setL_territory(Territory p_territory) {
        this.d_Territory = p_territory;
    }

    public int getL_numberOfArmies() {
        return this.d_numberOfArmies;
    }

    public Territory getL_territory() {
        return this.d_Territory;
    }

    /**
     * This method executes the order and deploys the armies on the target country.
     * @return A string containing whether the order was successfully executed or not.
     */
    public String execute() {
        if (this.d_orderType == "DEPLOY") {
            this.d_Territory.setNumberOfArmies(this.d_Territory.getNumberOfArmies() + this.d_numberOfArmies);
            return "Deployed armies successfully";

        }
        return "Invalid order";
    }
}