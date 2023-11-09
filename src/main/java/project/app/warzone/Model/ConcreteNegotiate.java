package project.app.warzone.Model;

public class ConcreteNegotiate extends OrderMethods  implements OrderInterface {
   
    private AttackOrder attackOrder;

    public ConcreteNegotiate()
    {

    }

    @Override
   public  void execute()
   {
    attackOrder.Negotiate();
   }



}
