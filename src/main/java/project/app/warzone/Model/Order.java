package project.app.warzone.Model;

import java.util.List;

/**
 * This class is used to prototye the orders for a player in the game.
 */
public class Order {

    private String d_orderType = "DEPLOY"; // The number of armies to deploy.

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

    /**
     * This method executes the order and deploys the armies on the target country.
     * 
     * @return A string containing whether the order was successfully executed or
     *         not.
     */
    public String execute() {
        if (this.d_orderType == "DEPLOY") {
            this.d_Country.setNumberOfArmies(this.d_Country.getNumberOfArmies() + this.d_numberOfArmies);
            return "Deployed armies successfully";

        }

        /*if (this.d_orderType == "BOMB") {

            if(bombValidate())
            {       
            int l_halfArmy = (int) Math.round((double) target.getNumberOfArmies() / 2);
            target.setNumberOfArmies(l_halfArmy);
            return "Bomb attacked successfully";
            }

        }

        if (this.d_orderType == "BLOCKADE") {

            //this card is only for 1 player
            
            //validate method need to create which check both the country are of current one player
            //one validate method can be used for blockage and airlift
            
            targetcountry.setNumberOfArmies(targetcountry.getNumberOfArmies() * 2);

            // target country is set to be neutral...so set country owner as no one
            targetcountry owner is null or 0;

            //updating and removeing country from players' country list
            List<Territory> l_newOwnedCountryList = player.getTerritoriesOwned();
            l_newOwnedCountryList.remove(targetcountry);
            player.setTerritoriesOwned(l_newOwnedCountryList);

            return "Blockade executed successfully";

        }


        if (this.d_orderType == "AIRLIFT") {

            //if(airilftValidate())       create validate functino to check if source and target country should be both player's

            //this card is only for 1 player
            
            //validate method need to create which check both the country are of current one player
            //one validate method can be used for blockage and airlift


            source.setNumberOfArmies(source.getNumberOfArmies() - numOfArmyToAirlift);
            target.setNumberOfArmies(target.getNumberOfArmies() + numOfArmyToAirlift);
            return "Airlift executed successfully";

        }

        if (this.d_orderType == "NEGOTIATE") {

            //no need to validate here

            //need to change player 1 and 2 

            //setting frinds for player 1
            List<Player> l_friendlyAlliesfor_player1 ;
            for (Player player : player1.getFriendlyAllies()) {
                l_friendlyAlliesfor_player1.add(player);
            }

            l_friendlyAlliesfor_player1.add(player2);
            player1.setFriendlyAllies(l_friendlyAlliesfor_player1);

            
          //setting frinds for player 2

            List<Player> l_friendlyAllies_for_player2 ;
            for (Player player : player2.getFriendlyAllies()) {
                l_friendlyAllies_for_player2.add(player);
            }

            l_friendlyAllies_for_player2.add(player1);
            player2.setFriendlyAllies(l_friendlyAllies_for_player2);

            return "Negotiate executed successfully";

        }
        
*/
        return "Invalid order";
    }

 /*    private boolean bombValidate() {

        // to check if player is not bombing their own country

        // get lpayers owned country list if required

        if (player.getlistOfCountriesOwned.contains(target)) {
            return false;
        } else {
            return true;
        }

    }

    boolean block_Airlift_validate() {

        // get lpayers owned country list if required

        if (player.getlistOfCountriesOwned.contains(country1) && player.getlistOfCountriesOwned.contains(country2)) {
            return true;
        } else {
            return false;
        }

    } */

    
}