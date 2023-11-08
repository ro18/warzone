package project.app.warzone.Model;

public class ConcreteBomb extends OrderMethods  implements OrderInterface {
    private AttackOrder attackOrder;
    //constructor
public ConcreteBomb()
{

}

@Override
public  void execute()
{
 attackOrder.Bomb();
}    

}
