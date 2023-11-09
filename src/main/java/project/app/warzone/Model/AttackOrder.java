package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.LogObject;

/**
 * This class is a receiver class which have implementation for all the commands
 */
@Component
public class AttackOrder implements Observer {
  private LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

  // Constructor

  public AttackOrder() {
    logEntryBuffer.addObserver(this);
  }

  /**
   * This method is used to deploy armies on a country
   */
  public String Deploy(int p_armies, Country p_country) {
    p_country.setNumberOfArmies(p_country.getNumberOfArmies() + p_armies);

    return "Deployed armies successfully";
  }

  // /**
  // * This method is used to deploy armies on a country
  // * @param p_gameEngine Instance of the game engine
  // * @param p_countryID ID of the country to deploy armies on
  // * @param p_armies Number of armies to deploy
  // * @return A string containing status of the game.
  // */

  // public String Deploy(GameEngine p_gameEngine, int p_countryID, int p_armies)
  // {
  // List<Player> l_players = p_gameEngine.getPlayers();

  // Player l_player =
  // p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
  // Country l_country =
  // p_gameEngine.gameMap.getNodes().get(p_countryID-1).getData();

  // /**
  // * Check if the player has enough armies in the reinforcement pool to deploy
  // */

  // if (l_player.getReinforcementArmies() < p_armies) {
  // return "Not enough armies to be deployed. Available armies: " +
  // l_player.getReinforcementArmies();
  // }

  // /**
  // * Check if the country is owned by the player
  // */

  // Optional<Country> l_territory = l_player.d_listOfCountriesOwned.stream()
  // .filter(c ->
  // c.getCountryName().equals(l_country.getCountryName())).findFirst();

  // if (!l_territory.isPresent()) {
  // return "Country is not owned by the player";
  // }

  // // Order l_deployOrder = new OrderMethods();
  // // l_deployOrder.setL_numberOfArmies(p_armies);
  // // l_deployOrder.setL_territory(l_country);

  // java.util.Map<String, Integer> l_orderDetails = new HashMap<String,
  // Integer>();

  // l_orderDetails.put("Armies", p_armies);
  // l_orderDetails.put("CountryId", p_countryID);

  // //IssueOrder
  // l_player.issue_order(0,l_orderDetails);

  // /**
  // * Main Game loop in round robin fashion which checks the reinforcement pool
  // of the player and if it is 0, then
  // * ask the next player to deploy armies. If all players have deployed all
  // their armies, then execute the orders
  // */
  // p_gameEngine.execute_orders();

  // Boolean l_flag = false;
  // int l_i = PlayerCommands.d_CurrentPlayerId+1;

  // while (l_i != PlayerCommands.d_CurrentPlayerId) {
  // if (l_i == l_players.size()) {
  // l_i = 0;
  // continue;
  // }

  // if (l_players.get(l_i).getReinforcementArmies() > 0) {
  // l_flag = true;
  // break;
  // }
  // l_i++;
  // }

  // if (l_flag) {
  // PlayerCommands.d_CurrentPlayerId = l_i;
  // return "Turn of " + l_players.get(l_i).getL_playername() + " to deploy army";
  // } else {
  // p_gameEngine.execute_orders();
  // return "Orders successfully executed";
  // }
  // }

  // Advance
  public void Advance(Player player1, Player player2, int p_armiesToAdv, Country source, Country target) {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("advance");

    System.out.println("Inside Advance Method");

    long targetArmiesKilled = Math.round(target.getNumberOfArmies() * 0.7);

    long sourceArmiesKilled = Math.round(source.getNumberOfArmies() * 0.6);

    if ((p_armiesToAdv - targetArmiesKilled) > target.getNumberOfArmies() - sourceArmiesKilled) {

      // Set armies in target country

      target.setNumberOfArmies((int) (p_armiesToAdv - targetArmiesKilled));

      // Set armies in source country

      source.setNumberOfArmies(source.getNumberOfArmies() - p_armiesToAdv);

      // Remove the territory from defenders list
      if (player2 != null) {
        player2.removeTerritory(target);

      }

      // Add the territory to attackers list
      player1.setTerritories(target);

      // adding card to players list

      Cards l_card;

      int l_randomInt = (int) (Math.random() * 4);

      switch (l_randomInt) {
        case 0:
          l_card = new Cards("BOMB");
          break;
        case 1:
          l_card = new Cards("BLOCKADE");
          break;
        case 2:
          l_card = new Cards("AIRLIFT");
          break;
        case 3:
          l_card = new Cards("NEGOTIATE");
          break;

        default:
          throw new IllegalArgumentException("Invalid card type");
      }

      player1.d_cardsInCollection.add(l_card);

      // add bonus armies on conquering territory
      player1.addReinforcementArmies(2);

    } else {

      target.setNumberOfArmies((int) (target.getNumberOfArmies() - sourceArmiesKilled));

      if ((source.getNumberOfArmies() - sourceArmiesKilled) >= 0) {
        source.setNumberOfArmies((int) (source.getNumberOfArmies() - sourceArmiesKilled));
      } else {
        
        source.setNumberOfArmies(0);
      }
    }
      l_logObject.setStatus(true, "Player " + player1.getL_playername() + " has attacked " + target.getCountryName());
      logEntryBuffer.notifyClasses(l_logObject);

  }

  // Airlift
  public void Airlift(Country p_countryFrom, Country p_countryTo, int p_airliftArmies) {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("airlift");
    p_countryFrom.setNumberOfArmies(p_countryFrom.getNumberOfArmies() - p_airliftArmies);
    p_countryTo.setNumberOfArmies(p_countryTo.getNumberOfArmies() + p_airliftArmies);

    l_logObject.setStatus(true, "Airlift executed successfully");
    logEntryBuffer.notifyClasses(l_logObject);
  }

  // Blockade
  public void Blockade(Player player, Country target) {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("blockade");
    target.setNumberOfArmies(target.getNumberOfArmies() * 3);
    target.setOwnerId(0);

    player.removeTerritory(target);
    l_logObject.setStatus(true, "Blockade executed successfully");
    logEntryBuffer.notifyClasses(l_logObject);

  }

  // Bomb
  public void Bomb(Country target) {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("bomb");
    
    System.out.println("Inside Bomb Method");
    target.setNumberOfArmies(target.getNumberOfArmies() / 2);
    l_logObject.setStatus(true, "Bomb attack executed successfully");
    logEntryBuffer.notifyClasses(l_logObject);
  }

  // Negotiate
  public String Negotiate() {
    System.out.println("Inside Negotiate Method");
    return "--Inside Negotiate Method";
  }

  public void update(java.util.Observable p_obj, Object p_arg) {
        LogObject l_logObject = (LogObject) p_arg;
        if (p_arg instanceof LogObject) {
            try {
                BufferedWriter l_writer = new BufferedWriter(
                        new FileWriter(System.getProperty("logFileLocation"), true));
                l_writer.newLine();
                l_writer.append(LogObject.d_logLevel + " " + l_logObject.d_command + "\n" + "Time: " + l_logObject.d_timestamp + "\n" + "Status: "
                        + l_logObject.d_statusCode + "\n" + "Description: " + l_logObject.d_message);
                l_writer.newLine();
                l_writer.close();
            } catch (IOException e) {
                System.out.println("Error Reading file");
            }
        }
    }

}
