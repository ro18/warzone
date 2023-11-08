package project.app.warzone.Model;

public class ConcreteBomb extends OrderMethods  implements OrderInterface {
    private AttackOrder attackOrder;

    private Country d_targetCountry ;

    //constructor
public ConcreteBomb(Country target)
{
    this.d_targetCountry = target;

    this.attackOrder = new AttackOrder();

}

@Override
public  void execute()
{
 attackOrder.Bomb(d_targetCountry);
}    

}
