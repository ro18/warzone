package project.app.warzone.Model;

public class ConcreteDeploy extends OrderMethods  implements OrderInterface {
    private AttackOrder attackOrder;

    public ConcreteDeploy()
    {

    }

    @Override
   public  void execute()
   {
    attackOrder.Deploy();
   }

}
