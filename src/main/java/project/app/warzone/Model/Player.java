package project.app.warzone.Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * This class represents a player in the Warzone game.
 */
public class Player {

	public int d_playerid; // Player id of the player to decide the round robin order to deploy
	public String d_playername; // Name of the player assigned by the user
	public int d_reinforcementPool; // Total number of players available to deploy in each round

	public List<Country> d_listOfCountriesOwned; // List of countries owned by the player
	public Queue<OrderInterface> d_listOfOrders = new ArrayDeque<>(); // stores orders issued by the player
	public boolean pendingOrder=true;

	public List<Cards> d_cardsInCollection; // List of cards owned by the player
	public List<Player> d_friendlyAlliesList; // List of cards owned by the player


	

	/**
	 * Creates a new player with the given ID.
	 * 
	 * @param p_playerid   The ID of the player.
	 * @param p_playername The name of the player.
	 */
	public Player(int p_playerid, String p_playername) {
		this.d_playerid = p_playerid;
		this.d_playername = p_playername;
		this.d_listOfCountriesOwned = new ArrayList<>();
		this.d_reinforcementPool = 3;

	}

	/**
	 * @return int
	 */
	public int getL_playerid() {
		return d_playerid;
	}



	/**
	 * @param p_playerid storing playerid to set
	 */
	public void setL_playerid(int p_playerid) {
		d_playerid = p_playerid;
	}

	/**
	 * @return String
	 */
	public String getL_playername() {
		return d_playername;
	}

	/**
	 * @param noOfArmies storing numberofArmies to set reinforcement
	 */
	public void initReinforcementArmies(int p_noOfArmies) {
		d_reinforcementPool = p_noOfArmies;
	}

	/**
	 * @param noOfArmies storing numberofArmies to add reinforcement
	 */
	public void addReinforcementArmies(int p_noOfArmies) {
		d_reinforcementPool += p_noOfArmies;
	}

	/**
	 * @return int
	 */
	public int getReinforcementArmies() {
		return d_reinforcementPool;
	}

	/**
	 * @param l_playername storing playername to set
	 */
	public void setL_playername(String p_playername) {
		d_playername = p_playername;
	}

	public void setReinforcementMap(int p_noOfArmies) {
		this.d_reinforcementPool = p_noOfArmies;
	}

	public int getReinforcementMap() {
		return d_reinforcementPool;
	}

	public List<Country> getlistOfCountriesOwned() {
		return d_listOfCountriesOwned;
	}

	/**
	 * @param country storing country to add into player territories
	 */
	public void setTerritories(Country country) {

		d_listOfCountriesOwned.add(country);
		country.setOwnerId(getL_playerid());
	}

	/**
	 * @param country remove country from player territories
	 */
	public void removeTerritory(Country country) {

		
		d_listOfCountriesOwned.remove(country);
	}

	/**
	 * @return returns players' list of territory
	 */
	public List<Country> getListOfTerritories() {
		return d_listOfCountriesOwned;
	}

	public List<Cards> getCardsInCollection() {
		return d_cardsInCollection;
	}

	public void setCardsInCollection(List<Cards> p_cardsInCollection) {
		this.d_cardsInCollection = p_cardsInCollection;
	}

	public List<Player> getD_friendlyAlliesList() {
		return d_friendlyAlliesList;
	}

	public void setD_friendlyAlliesList(List<Player> d_friendlyAlliesList) {
		this.d_friendlyAlliesList = d_friendlyAlliesList;
	}

	void addCardToCollection(Player p_player, Cards p_card) {
		p_player.d_cardsInCollection.add(p_card);

	}

	void removeCardfromCollection(Player p_player, String p_cardType) {
		p_player.d_cardsInCollection.removeIf(card -> card.getCardType().equalsIgnoreCase(p_cardType));

	}

	/**
	 * @param order storing order to add
	 */

	public void issue_order(GameEngine p_gameEngine,int orderType,java.util.Map<String,Integer> order_details) {

		switch (orderType) {
			case 0:
				// System.out.println("Order List:"+d_listOfOrders);
				// System.out.print(order_details.get("Armies"));

				
				Country l_country = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryId")-1).getData();

				d_listOfOrders.add(new ConcreteDeploy(order_details.get("Armies"),l_country));

				break;
			case 1:
			    System.out.println("Inside Switch 1");

				Country l_countryFrom = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryIdFrom")-1).getData();
				Country l_countryTo = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryIdTo")-1).getData();


				int l_countryToOwner = l_countryTo.getOwnerId();

				Player player1 = p_gameEngine.getPlayers().get(order_details.get("PlayerId"));
				Player player2 = null;

				if(  l_countryToOwner == 0 ){
					player2 = null;
				}
				else{
					player2 = p_gameEngine.getPlayers().get(l_countryToOwner-1);
				}

				d_listOfOrders.add(new ConcreteAdvance(player1,player2,order_details.get("AdvanceArmies"),l_countryFrom,l_countryTo));

			break;
           case 2:

				Country l_countryFromAirlift = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryIdFrom")-1).getData();
				Country l_countryToAirlift = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryIdTo")-1).getData();


				d_listOfOrders.add(new ConcreteAirlift(l_countryFromAirlift,l_countryToAirlift,order_details.get("AirliftArmies")));

			break;
			case 3:
			
				Player playerBlock = p_gameEngine.getPlayers().get(order_details.get("PlayerId"));

				Country l_co = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryId")-1).getData();
				d_listOfOrders.add(new ConcreteBlockade(playerBlock,l_co));

			break;
			case 4:
				Country l_co1 = p_gameEngine.gameMap.getNodes().get(order_details.get("CountryId")-1).getData();

				
				d_listOfOrders.add(new ConcreteBomb(l_co1));

			break;
			case 5:
			    System.out.println("Inside Switch 5");
				System.out.print(order_details.get("Armies"));
				d_listOfOrders.add(new ConcreteNegotiate());

			break;

		
			default:
				break;
		}

		// this.setReinforcementMap(this.getReinforcementArmies() - order.getL_numberOfArmies());
	
	}

	/**
	 * method used to clear order list
	 */
	public void clear_orderList() {
		d_listOfOrders.clear();
	}

	/**
	 * @return Order
	 */
	public OrderInterface next_order() {
		if (d_listOfOrders.size() > 0)
			return d_listOfOrders.remove();
		return null;
	}

}