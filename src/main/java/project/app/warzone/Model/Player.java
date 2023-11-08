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
	public Queue<Order> d_listOfOrders = new ArrayDeque<>(); // stores orders issued by the player

	public List<Cards> d_cardsInCollection; // List of cards owned by the player

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

	void addCardToCollection(Player p_player, Cards p_card) {
		p_player.d_cardsInCollection.add(p_card);

	}

	void removeCardfromCollection(Player p_player, String p_cardType) {
		p_player.d_cardsInCollection.removeIf(card -> card.getCardType().equalsIgnoreCase(p_cardType));

	}

	/**
	 * @param order storing order to add
	 */

	public void issue_order(int orderType,java.util.Map<String,Integer> order_details) {

		switch (orderType) {
			case 0:
				System.out.println("Order List:"+d_listOfOrders);
				System.out.print(order_details.get("Armies"));

				d_listOfOrders.add(new ConcreteDeploy(order_details.get("Armies"),order_details.get("CountryId")));
				// ConcreteDeploy l_order = ;

				// l_order.setL_numberOfArmies(p_armies);
				// l_order.setL_territory(l_country);
				break;
			case 1:
			    System.out.println("Inside Switch 1");
				System.out.print(order_details.get("Armies"));
				d_listOfOrders.add(new ConcreteAdvance(order_details.get("Armies"),order_details.get("CountryId")));

			break;
           case 2:
			    System.out.println("Inside Switch 2");
				System.out.print(order_details.get("Armies"));
				d_listOfOrders.add(new ConcreteAirlift());

			break;
			case 3:
			    System.out.println("Inside Switch 3");
				System.out.print(order_details.get("Armies"));
				d_listOfOrders.add(new ConcreteBlockade());

			break;
			case 4:
			    System.out.println("Inside Switch 4");
				System.out.print(order_details.get("Armies"));
				d_listOfOrders.add(new ConcreteBomb());

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