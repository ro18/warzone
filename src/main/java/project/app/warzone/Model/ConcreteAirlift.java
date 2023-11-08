package project.app.warzone.Model;

public class ConcreteAirlift extends OrderMethods  implements OrderInterface{
     
private AttackOrder attackOrder;
    //constructor
public ConcreteAirlift()
{

}

@Override
public  void execute()
{
 attackOrder.Airlift();
}  

}
