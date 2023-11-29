package project.app.warzone.Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

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

	public PlayerStrategy d_playerStrategy; 




	

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
		d_friendlyAlliesList = new ArrayList<>();
		d_cardsInCollection = new ArrayList<>();
		d_friendlyAlliesList.add(this);

	}


	public void setStrategy(PlayerStrategy p_strat) {
		d_playerStrategy = p_strat; 
	} 


	public PlayerStrategy getStrategy(){
		return d_playerStrategy;
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

	
	/** 
	 * @param p_noOfArmies no of armies
	 */
	public void setReinforcementMap(int p_noOfArmies) {
		this.d_reinforcementPool = p_noOfArmies;
	}

	
	/** 
	 * @return int
	 */
	public int getReinforcementMap() {
		return d_reinforcementPool;
	}

	/**
	 * @return lists
	 */
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

	/**
	 * @return lists
	 */
	public List<Cards> getCardsInCollection() {
		return d_cardsInCollection;
	}

	
	/** 
	 * @param p_cardsInCollection cards in collection
	 */
	public void setCardsInCollection(List<Cards> p_cardsInCollection) {
		this.d_cardsInCollection = p_cardsInCollection;
	}

	
	/** 
	 * @return List<Player>
	 */
	public List<Player> getD_friendlyAlliesList() {
		return d_friendlyAlliesList;
	}

	
	/** 
	 * @param player player
	 */
	public void addriendlyAlly(Player player) {
		d_friendlyAlliesList.add(player);
	}

	
	/** 
	 * @param d_friendlyAlliesList	allies lists
	 */
	public void setD_friendlyAlliesList(List<Player> d_friendlyAlliesList) {
		this.d_friendlyAlliesList = d_friendlyAlliesList;
	}

	
	/** 
	 * @param p_player	player
	 * @param p_card	card
	 */
	void addCardToCollection(Player p_player, Cards p_card) {
		p_player.d_cardsInCollection.add(p_card);

	}

	
	/** 
	 * @param p_player	player
	 * @param p_cardType	card type
	 */
	void removeCardfromCollection(Player p_player, String p_cardType) {
		p_player.d_cardsInCollection.removeIf(card -> card.getCardType().equalsIgnoreCase(p_cardType));

	}


	public void issue_order(PlayerStrategy playerStrategy){

		List<OrderInterface> newOrders  = playerStrategy.createOrder();

		if(newOrders != null && newOrders.size() > 0){

			for(OrderInterface order : newOrders){
				d_listOfOrders.add(order);

			}

			
		}
		
	}
	

	
	/** 
	 * @param p_gameEngine	gameengine
	 * @param orderType	order type
	 * @param order_details		order details
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
			    // System.out.println("Inside Switch 1");

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


				d_listOfOrders.add(new ConcreteAirlift(l_countryFromAirlift,l_countryToAirlift,order_details.get("AirLiftArmies")));

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
				Player playerToNegotiate = p_gameEngine.getPlayers().get(order_details.get("PlayerToBlock"));
				Player currentPlayer = p_gameEngine.getPlayers().get(order_details.get("CurrentPlayer"));


				d_listOfOrders.add(new ConcreteNegotiate(playerToNegotiate,currentPlayer));

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