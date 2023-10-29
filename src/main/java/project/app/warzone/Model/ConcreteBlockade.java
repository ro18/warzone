package project.app.warzone.Model;

public class ConcreteBlockade extends OrderMethods  implements OrderInterface {
private AttackOrder attackOrder;
    //constructor
public ConcreteBlockade()
{

}

@Override
public  void execute()
{
 attackOrder.Blockade();
}    



}
