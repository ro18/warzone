package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
   * @param p_armies  no of armies
   * @param p_country   country
   * @return    returns string
   */
  public String Deploy(int p_armies, Country p_country) {

    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("Deploy");
    p_country.setNumberOfArmies(p_country.getNumberOfArmies() + p_armies);

    l_logObject.setStatus(true, "Executed Deploy Attack");
    logEntryBuffer.notifyClasses(l_logObject);

    return "Deployed armies successfully";
  }

  
  /** 
   * @param player1 player 1
   * @param player2 player 2
   * @param p_armiesToAdv no of armies to advance
   * @param source  source country
   * @param target  target country
   */
  //Advance
  public void Advance( Player player1 , Player player2, int p_armiesToAdv, Country source, Country target)
  {
  //  System.out.println("Inside Advance Method");

    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("Advance");

    List<Player> getAllies = player1.getD_friendlyAlliesList();

    if(!getAllies.contains(player2)){

      long targetArmiesKilled = Math.round( target.getNumberOfArmies() * 0.7) ;

      long sourceArmiesKilled = Math.round (p_armiesToAdv * 0.6);

      if(( p_armiesToAdv - targetArmiesKilled ) > target.getNumberOfArmies() - sourceArmiesKilled){


      //Set armies in target country

      target.setNumberOfArmies((int)(p_armiesToAdv - targetArmiesKilled));


      //Set armies in source country

      source.setNumberOfArmies(source.getNumberOfArmies() -  p_armiesToAdv);


      //Remove the territory from defenders list
      if(player2 != null){
        player2.removeTerritory(target);

      }
      

      //Add the territory to attackers list
      player1.setTerritories(target);

      //adding card to players list

                                        Cards l_card ;

                                        int l_randomInt = (int) (Math.random() * 3);

                                        switch (2) {
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

    }
    else{


          target.setNumberOfArmies((int)(target.getNumberOfArmies() - sourceArmiesKilled));

          if ((source.getNumberOfArmies() - sourceArmiesKilled) >= 0)
          {
              source.setNumberOfArmies((int)(source.getNumberOfArmies() - sourceArmiesKilled));
          }
          else
          {
              source.setNumberOfArmies(0);
          }
    }

    }
    else{
      System.out.println(" Attack stopped as"+player1.getL_playername()+"used negotiation card on"+player2.getL_playername());
    }


    
 


    l_logObject.setStatus(true, "Executed Advance Attack");
    logEntryBuffer.notifyClasses(l_logObject);

    

  }


  /**
   * @param p_countryFrom source country
   * @param p_countryTo   target country
   * @param p_airliftArmies army to airlift
   */
  public void Airlift(Country p_countryFrom, Country p_countryTo, int p_airliftArmies) {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("airlift");
    p_countryFrom.setNumberOfArmies(p_countryFrom.getNumberOfArmies() - p_airliftArmies);
    p_countryTo.setNumberOfArmies(p_countryTo.getNumberOfArmies() + p_airliftArmies);

    l_logObject.setStatus(true, "Executed Airlift Attack");
    logEntryBuffer.notifyClasses(l_logObject);
  }

  
  /** 
   * @param player  current player
   * @param target  target player
   */
  // Blockade
  public void Blockade(Player player, Country target) {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("blockade");
    target.setNumberOfArmies(target.getNumberOfArmies() * 3);
    target.setOwnerId(0);

    player.removeTerritory(target);
    l_logObject.setStatus(true, "Executed Blockade Attack");
    logEntryBuffer.notifyClasses(l_logObject);

  }

  
  /** 
   * @param target  target player
   */
  public void Bomb(Country target)
  {
    LogObject l_logObject = new LogObject();
    l_logObject.setD_command("Airlift");
    target.setNumberOfArmies(target.getNumberOfArmies()/2);
    l_logObject.setStatus(true, "Executed Bomb Attack");

    logEntryBuffer.notifyClasses(l_logObject);


  }


  
  /** 
   * @param p_playerToNegotiate player to negotiate
   * @param currentPlayer current player
   */
  //Negotiate
  public void Negotiate(Player p_playerToNegotiate,Player currentPlayer)
  {
      LogObject l_logObject = new LogObject();

      p_playerToNegotiate.addriendlyAlly(p_playerToNegotiate);

      currentPlayer.addriendlyAlly(p_playerToNegotiate);

      l_logObject.setStatus(true, "Executed Negotiate Attack");

      logEntryBuffer.notifyClasses(l_logObject);






  }

  public void update(java.util.Observable p_obj, Object p_arg) {
        LogObject l_logObject = (LogObject) p_arg;
        if (p_arg instanceof LogObject) {
            try (BufferedWriter l_writer = new BufferedWriter(
                        new FileWriter(System.getProperty("logFileLocation"), true))){
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
