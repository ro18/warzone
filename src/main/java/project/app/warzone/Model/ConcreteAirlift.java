package project.app.warzone.Model;

public class ConcreteAirlift extends OrderMethods  implements OrderInterface{
     
private AttackOrder attackOrder;

private Country d_countryFrom ;
private Country d_countryTo ;
private int d_airliftArmies;

//constructor
public ConcreteAirlift(Country p_countryFrom,Country p_countryTo,int p_airliftArmies)
{
    this.d_countryFrom = p_countryFrom;
    this.d_countryFrom = p_countryTo;
    this.d_airliftArmies = p_airliftArmies;

}

@Override
public  void execute()
{
 attackOrder.Airlift(d_countryFrom,d_countryTo,d_airliftArmies);
}  

}
