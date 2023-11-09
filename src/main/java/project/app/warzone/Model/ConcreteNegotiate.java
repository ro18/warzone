package project.app.warzone.Model;

public class ConcreteNegotiate extends OrderMethods  implements OrderInterface {
   
    private AttackOrder attackOrder;

    public Player d_playerToNegotiate;

    public Player d_currentPlayer;

    /**
     * @param p_playerToNegotiate   player to negotiate
     * @param p_currenPlayer        current player
     */
    public ConcreteNegotiate(Player p_playerToNegotiate,Player p_currenPlayer)
    {
        this.d_playerToNegotiate = p_playerToNegotiate;

        this.d_currentPlayer = p_currenPlayer;

        this.attackOrder = new AttackOrder();


    }

    @Override
   public  void execute()
   {
        attackOrder.Negotiate(d_playerToNegotiate,d_currentPlayer);
   }



}
