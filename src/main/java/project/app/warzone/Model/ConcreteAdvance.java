package project.app.warzone.Model;

public class ConcreteAdvance extends OrderMethods implements OrderInterface{
private AttackOrder attackOrder;
    //constructor
public ConcreteAdvance()
{

}

@Override
public  void execute()
{
 attackOrder.Advance();
}   

}
