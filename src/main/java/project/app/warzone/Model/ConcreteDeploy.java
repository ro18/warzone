package project.app.warzone.Model;

public class ConcreteDeploy extends OrderMethods implements OrderInterface {
    private AttackOrder attackOrder;

    private Country d_country ;



    public ConcreteDeploy(int p_armies, Country p_country)
    {
        super.d_numberOfArmies = p_armies;
        d_country= p_country;
        System.out.println("Inside ConcreteDeploy");
        this.attackOrder = new AttackOrder();
    }

    @Override
   public  void execute()
   {
       attackOrder.Deploy(d_numberOfArmies,d_country);
   }

}
