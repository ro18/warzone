package project.app.warzone.Model;

public class ConcreteAdvance extends OrderMethods implements OrderInterface{
private AttackOrder attackOrder;

private Country d_countryFrom ;
private Country d_countryTo ;

private Player d_player1;

private Player d_player2;


/**
 * @param p_player1 player 1
 * @param p_player2 player 2
 * @param p_armies  no of armies
 * @param p_countryFrom source country
 * @param p_countryTo   target country
 */
public ConcreteAdvance(Player p_player1, Player p_player2,int p_armies, Country p_countryFrom , Country p_countryTo)
{
    super.d_numberOfArmies = p_armies;
    this.d_countryFrom= p_countryFrom;
    this.d_countryTo= p_countryTo;
    this.d_player1 = p_player1;
    this.d_player2 = p_player2;

    this.attackOrder = new AttackOrder();
}

@Override
public  void execute()
{
 attackOrder.Advance(d_player1,d_player2,d_numberOfArmies,d_countryFrom,d_countryTo);


}   

}
