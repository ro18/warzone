package project.app.warzone.Model;

public class ConcreteBlockade extends OrderMethods  implements OrderInterface {
private AttackOrder attackOrder;
private Country d_blockadeCountry ;
private Player d_player;

/**
 * @param p_player  player
 * @param p_blockadeCountry country to blockade
 */
public ConcreteBlockade(Player p_player,Country p_blockadeCountry)
{
    this.attackOrder = new AttackOrder();
    this.d_blockadeCountry = p_blockadeCountry;
    this.d_player = p_player;

}

@Override
public  void execute()
{
    attackOrder.Blockade(d_player,d_blockadeCountry);
}    



}
