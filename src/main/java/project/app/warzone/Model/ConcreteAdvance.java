package project.app.warzone.Model;

public class ConcreteAdvance extends OrderMethods implements OrderInterface{
private AttackOrder attackOrder;
    //constructor
public ConcreteAdvance(int p_armies, int p_countryId)
{
    super.d_numberOfArmies = p_armies;
    super.d_CountryId= p_countryId;
    System.out.println("Inside ConcreteAdvance");
    this.attackOrder = new AttackOrder();
}

@Override
public  void execute()
{
 attackOrder.Advance();
}   

}
