package project.app.warzone.Features;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Pattern;
import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.HumanStrategy;
import project.app.warzone.Model.LogEntryBuffer;
import project.app.warzone.Utilities.LogObject;
import project.app.warzone.Utilities.UserCommands;
import project.app.warzone.Model.Node;
//import project.app.warzone.Model.Order;
import project.app.warzone.Model.OrderMethods;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.RandomStrategy;
import project.app.warzone.Model.AggressiveStrategy;
import project.app.warzone.Model.Attack;
import project.app.warzone.Model.BenevolentStrategy;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.CheaterStrategy;
import project.app.warzone.Model.ConcreteDeploy;
import project.app.warzone.Model.Country;

/**
 * This class stores all the player-related functions in gameplay
 */
@Component
public class PlayerFeatures implements Observer {

    // @Autowired
    // @Lazy
    // private LineReader lineReader;
    private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

    /**
     * @param p_allPlayers list of all players
     */
    public void showAllAssignments(List<Player> p_allPlayers) {

        for (Player l_p : p_allPlayers) {

            System.out.println("Player Id:" + l_p.d_playerid);

            System.out.println("Countries of " + l_p.d_playername + ":");
            List<Country> listOfTerritories = l_p.getListOfTerritories();

            for (Country l_t : listOfTerritories) {
                System.out.println(l_t.getCountryName());

            }
        }

    }

    /**
     * @param p_gameEngine storing gameEngine
     */
    public void assignCountries(GameEngine p_gameEngine) {

        Random l_random = new Random();

        for (Player l_player : p_gameEngine.getPlayers()) { // logic to have players -1 country

            int randomCountry = l_random.nextInt(p_gameEngine.gameMap.getNodes().size());

            // int randomId = l_random.nextInt(p_gameEngine.getPlayers().size()+1);

            while (p_gameEngine.gameMap.getNodes().get(randomCountry).getData().getOwnerId() != 0) {
                randomCountry = l_random.nextInt(p_gameEngine.gameMap.getNodes().size() + 1);

            }

            l_player.setTerritories(p_gameEngine.gameMap.getNodes().get(randomCountry).getData());

        }
    }

    /**
     * @param p_gameEngine  gameengine
     */
    public void assignCountriesForDemo(GameEngine p_gameEngine) {

        List<Integer> countryIds = new ArrayList<>() {
            {
                add(0);
                add(7);
                add(20);
                add(9);
            }
        };

        Random l_random = new Random();
        int i = 0;

        for (Player l_player : p_gameEngine.getPlayers()) { // logic to have players -1 country

            // int randomCountry = l_random.nextInt(p_gameEngine.gameMap.getNodes().size());

            // int randomId = l_random.nextInt(p_gameEngine.getPlayers().size()+1);

            // while
            // (p_gameEngine.gameMap.getNodes().get(randomCountry).getData().getOwnerId() !=
            // 0) {
            // randomCountry = l_random.nextInt(p_gameEngine.gameMap.getNodes().size() + 1);

            // }

            l_player.setTerritories(p_gameEngine.gameMap.getNodes().get(countryIds.get(i)).getData());

            i++;

        }
    }

    /**
     * @param p_playerName storing playername
     * @param p_gameEngine storing gameEngine
     */
    public void addPlayers(String p_playerName, GameEngine p_gameEngine) {

        Player player = new Player(p_gameEngine.getPlayers().size() + 1, p_playerName);
        p_gameEngine.d_playersList.add(player);

    }

    public void setPlayerStrategy(GameEngine p_gameEngine){
       
        for(Player l_p : p_gameEngine.d_playersList){
            LogObject l_logObject = new LogObject();
            l_logEntryBuffer.addObserver(this);
        
            System.out.println("Current Player: "+l_p.d_playername);
            String input ="";
            System.out.println("Strategy: "+ UserCommands.checkSize("strategy"));
            if(UserCommands.checkSize("strategy")>0){
                input = UserCommands.popCommand("strategy");
            }
            else{
                while (true) {

                    Scanner reader = new Scanner(System.in);
                    System.out.println(
                            "Choose one of the following:\n 1.Human\n 2. Aggressive\n 3. Benevolent\n 4. Random\n 5.Cheater\n");
                    System.out.print("->");
                    input = reader.nextLine();

                    if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2") || input.equalsIgnoreCase("3")
                            || input.equalsIgnoreCase("4") || input.equalsIgnoreCase("5")) {
                        l_logObject.setD_command(input + "_strategy");
                        l_logObject.setStatus(true, "User is setting the strategy");
                        l_logEntryBuffer.notifyClasses(l_logObject);
                        break;

                    } else {

                        System.out.println("Incorrect Input");
                    }

                    reader.close();

                }
            }
            
            switch(input){

                case "1": l_p.setStrategy(new HumanStrategy(l_p,p_gameEngine));
                break;

                case "2": l_p.setStrategy(new AggressiveStrategy(l_p,p_gameEngine));
                break;

                case "3": l_p.setStrategy(new BenevolentStrategy(l_p,p_gameEngine));
                break;

                case "4": l_p.setStrategy(new RandomStrategy(l_p,p_gameEngine));
                break;

                case "5": l_p.setStrategy(new CheaterStrategy(l_p,p_gameEngine));
                break;          
            }
        }         
    }

    /**
     * @param p_gameEngine gameEngine object
     */
    public void setPlayerIds(GameEngine p_gameEngine) {

        int l_i = p_gameEngine.getPlayers().size();
        for (Player l_player : p_gameEngine.getPlayers()) {

            l_player.setL_playerid(l_i);
        }

    }

    /**
     * @param p_playerName storing playername
     * @param p_gameEngine storing gameEngine
     */
    public void removePlayers(String p_playerName, GameEngine p_gameEngine) {

        List<Player> l_playerList = p_gameEngine.getPlayers();
        Optional<Player> l_playerToRemove = l_playerList.stream().filter(c -> c.getL_playername().equals(p_playerName))
                .findFirst();
        l_playerList.remove(l_playerToRemove.get());
        setPlayerIds(p_gameEngine);

    }

    /**
     * @param p_gameEngine storing gameEngine
     */
    public void printAllPlayers(GameEngine p_gameEngine) {
        System.out.println("Final players of the game are:");
        List<Player> l_players = p_gameEngine.getPlayers();
        for (int i = 1; i <= l_players.size(); i++) {
            System.out.println("Player" + i + ":" + l_players.get(i - 1).getL_playername());
        }
    }

    /**
     * @param p_gameengine storing gameEngine
     */
    public void showStats(GameEngine p_gameengine) {
        List<Player> l_listOfPlayers = p_gameengine.getPlayers();
        for (Player l_p : l_listOfPlayers) {
            System.out.println("Player Name:" + l_p.d_playername + "\nPlayerId:" + l_p.d_playerid +"\nPlayer Strategy:" + l_p.getStrategy().getClass().getSimpleName());
            System.out.println("Total Armies available per round: " + l_p.getReinforcementArmies());
            System.out.println("CountryID - Countries Owned - Armies");

            for (Country t : l_p.getListOfTerritories()) {
                System.out.println(t.getCountryId() + " - " + t.getCountryName() + " - " + t.getNumberOfArmies());
            }
            System.out.println("Available Cards:");
            int l_i=1;
            for( Cards c : l_p.d_cardsInCollection){
                System.out.println(l_i+". "+c.getCardType());
                l_i++;
            }
            System.out.println("-------------------------------");
        }

    }

    /**
     * @param p_gameengine storing gameEngine
     */
    public void showMapStatus(GameEngine p_gameengine) {

        System.out.println("------ Current Map Status ------");
        System.out.println();
        String continent = "";
        List<Node> nodeList = p_gameengine.gameMap.getNodes();
        for (Node c : nodeList) {

            if (c.getData().getContinent().getContinentName() != continent) {
                System.out.println("Continent:" + c.getData().getContinent().getContinentName());
                continent = c.getData().getContinent().getContinentName();
                System.out.println("=======================================================================");
            }
            System.out.print(c.getData().getCountryId() + "-" + c.getData().getCountryName() + ": ("
                    + c.getData().getNumberOfArmies() + ") :");
            String borderString = "";
            if (c.getBorders() != null && c.getBorders().size() > 0) {
                List<Node> listOfBorders = c.getBorders();

                for (Node border : listOfBorders) {

                    borderString += border.getData().getCountryName() + "(" + border.getData().getNumberOfArmies()
                            + ") -> ";

                }
                borderString = borderString.substring(0, borderString.length() - 4);
                System.out.println(borderString);
                System.out.println();
            }

        }

    }

    /**
     * This method is used to advance armies on a country
     * 
     * @param currentPlayerId player initiating this command
     * 
     * @param p_gameEngine    Instance of the game engine
     * @param p_countryIDFrom ID of the source country
     * @param p_countryIDTo   ID of the target country
     * 
     * @param p_armiesToAdv   Number of armies to advance
     * @return A string containing status of the game.
     */

    public String advanceArmies( GameEngine p_gameEngine, int currentPlayerId,int p_countryIDFrom, int p_countryIDTo,
            int p_armiesToAdv) {
        // List<Player> l_players = p_gameEngine.getPlayers();



        Player l_player = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
        Country l_countryFrom = p_gameEngine.gameMap.getNodes().get(p_countryIDFrom - 1).getData();

        Country l_countryTo = p_gameEngine.gameMap.getNodes().get(p_countryIDTo - 1).getData();

        Optional<Country> countryInPlayer = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_countryFrom.getCountryName())).findFirst();

        if (!countryInPlayer.isPresent()) {
            System.out.println("Country is not owned by the player");
            p_gameEngine.checkPlayersReinforcements();

            return "Country is not owned by the player";
        }

        boolean found = false;

        List<Node> nodeList = p_gameEngine.getGameMap().getNodes();

        for (Node c : nodeList) {

            if (c.getData() == l_countryFrom) {

                if (c.getBorders() != null && c.getBorders().size() > 0) {

                    List<Node> listOfBorders = c.getBorders();

                    for (Node border : listOfBorders) {

                        if (border.getData() == l_countryTo) {
                            found = true;
                            break;
                        }

                    }

                }

                break;
            }
            if (found == true) {
                break;
            }

        }

        if (found == false) {
            System.out.println("Target Country" + l_countryFrom.getCountryName() +" is not connected with" + l_countryTo.getCountryName());
            p_gameEngine.checkPlayersReinforcements();

            return "Target Country " + l_countryFrom.getCountryName() + " is not connected with " + l_countryTo.getCountryName();
        }

        /**
         * Check if the player has enough armies in the reinforcement pool to deploy
         */
        if (l_countryFrom.getNumberOfArmies() < p_armiesToAdv) {
            System.out.println("Not enough armies to be advance. Available armies in " + l_countryFrom.getCountryName() + ":"+ l_countryFrom.getNumberOfArmies());
            p_gameEngine.checkPlayersReinforcements();

            return "Not enough armies to be advance. Available armies in " + l_countryFrom.getCountryName() + ":"+ l_countryFrom.getNumberOfArmies();

        }

        java.util.Map<String, Integer> l_orderDetails = new HashMap<String, Integer>();

        l_orderDetails.put("AdvanceArmies", p_armiesToAdv);
        l_orderDetails.put("CountryIdFrom", p_countryIDFrom);
        l_orderDetails.put("CountryIdTo", p_countryIDTo);
        l_orderDetails.put("PlayerId", currentPlayerId);

        // IssueOrder
        l_player.issue_order(p_gameEngine, 1, l_orderDetails);


        PlayerCommands.d_CurrentPlayerId = PlayerCommands.d_CurrentPlayerId + 1;

        p_gameEngine.checkPlayersReinforcements();


        return "";

    }

    /**
     * @param p_gameEngine  gameengine
     * @param p_countryID   country ID
     * @param p_armies      number of armies
     * @return      returns string
     */
    public String deployArmies(GameEngine p_gameEngine, int p_countryID, int p_armies) {
        // List<Player> l_players = p_gameEngine.getPlayers();
        LogObject l_logObject = new LogObject();
        l_logEntryBuffer.addObserver(this);
        l_logObject.d_command = "deploy " + p_countryID + " " + p_armies;

        Player l_player = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
        Country l_country = p_gameEngine.gameMap.getNodes().get(p_countryID - 1).getData();

        /**
         * Check if the player has enough armies in the reinforcement pool to deploy
         */

        if (l_player.getReinforcementArmies() < p_armies) {
            l_logObject.setStatus(false, "User tried to deploy armies more than the remaining reinforcement pool.");
            l_logEntryBuffer.notifyClasses(l_logObject);
            System.out.println("Not enough armies to be deployed. Available armies: " + l_player.getReinforcementArmies());
            p_gameEngine.checkPlayersReinforcements();

            return "";
        }
        

        /**
         * Check if the country is owned by the player
         */

        Optional<Country> l_territory = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_country.getCountryName())).findFirst();

        if (!l_territory.isPresent()) {
            l_logObject.setStatus(false, "User tried to deploy armies on a country not owned by him.");
            l_logEntryBuffer.notifyClasses(l_logObject);
            System.out.println("Country is not owned by the player");

            p_gameEngine.checkPlayersReinforcements();

            return "";

        }

        java.util.Map<String, Integer> l_orderDetails = new HashMap<String, Integer>();

        l_orderDetails.put("Armies", p_armies);
        l_orderDetails.put("CountryId", p_countryID);

        // IssueOrder
        l_player.issue_order(p_gameEngine, 0, l_orderDetails);

        if (l_player.getReinforcementArmies() - p_armies > 0) {
            l_player.setReinforcementMap(l_player.getReinforcementArmies() - p_armies);
            l_logObject.setStatus(true, "Armies deployed successfully.");
            l_logEntryBuffer.notifyClasses(l_logObject);

        } else {
            l_player.setReinforcementMap(0);

        }

        PlayerCommands.d_CurrentPlayerId = PlayerCommands.d_CurrentPlayerId + 1;

        p_gameEngine.checkPlayersReinforcements();
        return "";

    }

   

    /**
     * @param p_gameEngine  gameengine
     * @param p_countryID   country ID
     * @return     returns string
     */
    public String blockadeCountry(GameEngine p_gameEngine, int p_countryID) {

        Player l_player = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        Country l_country = p_gameEngine.gameMap.getNodes().get(p_countryID - 1).getData();

        Optional<Country> countryInPlayer = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_country.getCountryName())).findFirst();

        if (!countryInPlayer.isPresent()) {
            p_gameEngine.checkPlayersReinforcements();

            return "You do not own the country" + l_country.getCountryName() + "to blockade it";

        }

        java.util.Map<String, Integer> l_orderDetails = new HashMap<String, Integer>();

        l_orderDetails.put("CountryId", p_countryID);
        l_orderDetails.put("PlayerId", PlayerCommands.d_CurrentPlayerId);

        // IssueOrder
        l_player.issue_order(p_gameEngine, 3, l_orderDetails);



        PlayerCommands.d_CurrentPlayerId = PlayerCommands.d_CurrentPlayerId + 1;


        p_gameEngine.checkPlayersReinforcements();

        return "";

    }

    /**
     * @param p_gameEngine  gameengine
     * @param p_countryID   country ID
     * @return  returns
     */
    public String bombCountry(GameEngine p_gameEngine, int p_countryID) {
        // List<Player> l_players = p_gameEngine.getPlayers();

        Player l_player = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        Country l_country = p_gameEngine.gameMap.getNodes().get(p_countryID - 1).getData();

        Optional<Country> countryInPlayer = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_country.getCountryName())).findFirst();

        if (!countryInPlayer.isPresent()) {

            boolean found = false;

            List<Country> listOfCountriesOwned = l_player.getlistOfCountriesOwned();

            List<Node> nodeList = p_gameEngine.getGameMap().getNodes();

            for (Node n : nodeList) {
                if (n.getData().getCountryName().equals(l_country.getCountryName()) && found == false) {

                    List<Node> listOfBorders = n.getBorders();

                    for (Node lb : listOfBorders) {

                        if (listOfCountriesOwned.contains(lb.getData())) {
                            found = true;

                            System.out.println("The territory to bomb is valid");
                        }
                        if (found = true) {
                            break;
                        }

                    }

                    break;

                }

            }

            if (found == false) {
                System.out.println("Target Country " + l_country.getCountryName() + " is not a neighbouring country");

                p_gameEngine.checkPlayersReinforcements();

            }

        }

        else {

            System.out.println("You cannot target your own country");
            p_gameEngine.checkPlayersReinforcements();

            return "";

        }

        java.util.Map<String, Integer> l_orderDetails = new HashMap<String, Integer>();

        l_orderDetails.put("CountryId", p_countryID);

        // IssueOrder
        l_player.issue_order(p_gameEngine, 4, l_orderDetails);


        PlayerCommands.d_CurrentPlayerId = PlayerCommands.d_CurrentPlayerId + 1;


        p_gameEngine.checkPlayersReinforcements();

        return "";

    }

    /**
     * @param p_gameEngine      gameengine
     * @param p_countryIDFrom   country from
     * @param p_countryIDTo     country to
     * @param p_armiesToAirlift    armies to airlift
     * @return  returns string
     */
    public String airlift(GameEngine p_gameEngine, int p_countryIDFrom, int p_countryIDTo, int p_armiesToAirlift) {

        Player l_player = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
        Country l_countryFrom = p_gameEngine.gameMap.getNodes().get(p_countryIDFrom - 1).getData();

        Country l_countryTo = p_gameEngine.gameMap.getNodes().get(p_countryIDTo - 1).getData();

        Optional<Country> countryInPlayerFrom = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_countryFrom.getCountryName())).findFirst();

        Optional<Country> countryInPlayerTo = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_countryTo.getCountryName())).findFirst();

        if (!countryInPlayerFrom.isPresent()) {

            System.out.println("FromCountry is not owned by the player");

            p_gameEngine.checkPlayersReinforcements();

        }

        if (!countryInPlayerTo.isPresent()) {


            System.out.println("ToCountry is not owned by the player");

            p_gameEngine.checkPlayersReinforcements();

        }

        /**
         * Check if the player has enough armies in the reinforcement pool to deploy
         */
        if (l_countryFrom.getNumberOfArmies() < p_armiesToAirlift) {


            System.out.println("Not enough armies to be advance. Available armies in " + l_countryFrom.getCountryName() + ":"
                    + l_countryFrom.getNumberOfArmies());

            p_gameEngine.checkPlayersReinforcements();


        }

        java.util.Map<String, Integer> l_orderDetails = new HashMap<String, Integer>();

        l_orderDetails.put("AirLiftArmies", p_armiesToAirlift);
        l_orderDetails.put("CountryIdFrom", p_countryIDFrom);
        l_orderDetails.put("CountryIdTo", p_countryIDTo);
        l_orderDetails.put("PlayerId", PlayerCommands.d_CurrentPlayerId);

        // IssueOrder
        l_player.issue_order(p_gameEngine, 2, l_orderDetails);


        PlayerCommands.d_CurrentPlayerId = PlayerCommands.d_CurrentPlayerId + 1;


        p_gameEngine.checkPlayersReinforcements();

        return "";

    }

    /**
     * @param p_gameEngine  gameengine
     * @param p_targetPlayerId  target player ID
     * @return      returns string
     */
    public String negotiate(GameEngine p_gameEngine, int p_targetPlayerId) {

        Player l_player_1 = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
        Player l_player_2 = p_gameEngine.getPlayers().get(p_targetPlayerId-1);

        if(l_player_1 != l_player_2){


            

            java.util.Map<String, Integer> l_orderDetails = new HashMap<String, Integer>();

            l_orderDetails.put("PlayerToBlock", p_targetPlayerId-1);

            l_orderDetails.put("CurrentPlayer", PlayerCommands.d_CurrentPlayerId);

            

            // IssueOrder
            l_player_1.issue_order(p_gameEngine, 5, l_orderDetails);

            PlayerCommands.d_CurrentPlayerId = PlayerCommands.d_CurrentPlayerId + 1;


            p_gameEngine.checkPlayersReinforcements();

            return "";

        }

        else{

            System.out.println("Negotiate unsuccessful : both players cannot be the same");

            p_gameEngine.checkPlayersReinforcements();

            return "";


        }
    }

        
       
       

    /**
     * This method is used to update the log
     * 
     * @param p_obj to be updated
     * @param p_arg to be updated
     */
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
