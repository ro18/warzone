package project.app.warzone.Model;

public class ConcreteDeploy extends OrderMethods implements OrderInterface {
    private AttackOrder attackOrder;



    public ConcreteDeploy(int p_armies, int p_countryId)
    {
        super.d_numberOfArmies = p_armies;
        super.d_CountryId= p_countryId;
        System.out.println("Inside ConcreteDeploy");
        this.attackOrder = new AttackOrder();
    }

    // /**
    //  * This method is used to set armies for the particular order
    //  * 
    //  * @param p_numArmies storing numberofArmies to set
    //  */
    // public void setL_numberOfArmies(int p_numArmies) {
    //     this.d_numberOfArmies = p_numArmies;
    // }

    // /**
    //  * This method is user to set Country for the particular order
    //  * 
    //  * @param p_territory storing territory to set
    //  */
    // public void setL_territory(Country p_territory) {
    //     this.d_Country = p_territory;
    // }

    // /**
    //  * This method is returns the number of armies mentioned in order
    //  * 
    //  * @return int
    //  */
    // public int getL_numberOfArmies() {
    //     return this.d_numberOfArmies;
    // }

    /**
     * This method is used to get territory for the particular order
     * 
     * @return Territory
     */
    // public Country getL_Country() {
    //     return this.d_Country;
    // }

    @Override
   public  void execute()
   {
    attackOrder.Deploy();
   }

}
