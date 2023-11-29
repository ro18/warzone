package project.app.warzone.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.LogObject;
import project.app.warzone.Utilities.MapResources;

public class Postload extends Edit implements java.util.Observer {
    private MapFeatures d_mapFeatures = MapFeatures.getInstance();
    private MapResources d_mapResources;
    private LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer();

    Postload(GameEngine p_ge) {
        super(p_ge);
        d_mapResources = new MapResources();
    }

    public void loadMap(String p_filename) {
        printInvalidCommandMessage();
    }

    public void editMap(String p_fileName) {
        printInvalidCommandMessage();
    }

    public void showMap(MapFeatures newmapFeatures) {
        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("showmap");

        String p_mapLocation = ge.gameMap.getMapDirectory() + "//" + ge.gameMap.get_USER_SELECTED_FILE() + ".map";
        Boolean l_result = false;
        ge.gameMap = newmapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if (!l_result) {
            l_logObject.setStatus(false, "Map is not shown but map is not valid!");
            l_logEntryBuffer.notifyClasses(l_logObject);
            ge.setPhase(new Preload(ge));
            System.out.println("This map is not valid.Please try with some other map");
        } else {
            l_logObject.setStatus(true, "Map is shown");
            l_logEntryBuffer.notifyClasses(l_logObject);
            ge.setPhase(new PlaySetup(ge));
            System.out.println("You can now proceed to add gameplayers");
        }
    }

    public void showstats() {
        printInvalidCommandMessage();
    }

    public void showmapstatus() {
        printInvalidCommandMessage();
    }

    public void reinforce(int p_countryID, int p_armies) {
        printInvalidCommandMessage();
    }

    public void editCountry(String p_editcmd, String p_editremovecmd) {
        // Please call this function
        // dMapEditorCommands.editcountry("-a India", "-r India");

        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("editcountry " + p_editcmd + " " + p_editremovecmd);

        if (p_editcmd != null && p_editcmd != "") {
            Map<String, String> listofCountries = new HashMap<String, String>();
            ge.prevUserCommand = Commands.ADDCOUNTRY;

            String[] editCmd = p_editcmd.split(",");
            int l_i = 0;

            String commandToCheck = "-add";
            while (l_i < editCmd.length) {

                if (editCmd[l_i].toString().equals(commandToCheck)) {
                    l_i++;
                    continue;

                } else {
                    listofCountries.put(editCmd[l_i], editCmd[l_i + 1]);
                    l_i += 2;

                }

            }
            try {
                d_mapFeatures.writeCountriesToFile(listofCountries, ge);

            } catch (IOException e) {
                e.printStackTrace();

            }
            l_logObject.setStatus(true, "Countries added successfully");
            l_logEntryBuffer.notifyClasses(l_logObject);
            System.out.println("Countries addded succesfully");
        } else {

            List<String> l_listofCountries = new ArrayList<>();
            String[] l_editremovecmd = p_editremovecmd.split(",");

            int l_i = 0;

            String commandToCheck = "-remove";

            while (l_i < l_editremovecmd.length) {

                System.out.println(l_editremovecmd[l_i] + ":" + commandToCheck);
                if (l_editremovecmd[l_i].toString().equals(commandToCheck)) {

                    l_i++;
                    continue;

                } else {
                    l_listofCountries.add(l_editremovecmd[l_i]);

                    System.out.println("listofCountries" + l_listofCountries);
                    l_i++;
                }

            }
            try {
                d_mapFeatures.removeCountriesFromFile(l_listofCountries, ge);

            } catch (IOException e) {
                e.printStackTrace();

            }
            l_logObject.setStatus(true, "Countries removed successfully");
            l_logEntryBuffer.notifyClasses(l_logObject);
            System.out.println("Countries removed succesfully");
        }
    }

    public void editContinent(String p_editcmd, String p_editremovecmd) {
        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("editcontinent " + p_editcmd + " " + p_editremovecmd);

        Map<String, String> listofContinents = new HashMap<String, String>();
        java.util.Map<Integer, String> listOfContinentsResource = d_mapResources.getAllContinents();

        if (p_editcmd != null && p_editcmd != "") {
            ge.prevUserCommand = Commands.ADDCONTINENT;
            String[] l_editCmd = p_editcmd.split(",");

            int l_i = 0;

            String l_commandToCheck = "-add";
            while (l_i < l_editCmd.length) {

                // System.out.println(l_editCmd[l_i] + ":" + l_commandToCheck);
                if (l_editCmd[l_i].toString().equals(l_commandToCheck)) {
                    l_i++;
                    continue;

                } else {
                    listofContinents.put(l_editCmd[l_i], l_editCmd[l_i + 1]);
                    l_i += 2;

                }

            }

            try {
                d_mapFeatures.writeContinentsToFile(listofContinents, ge);
                System.out.println("Continents addded succesfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
                l_logObject.setStatus(true, "Continents added successfully");
                l_logEntryBuffer.notifyClasses(l_logObject);
        } else {

            ge.prevUserCommand = Commands.REMOVECONTINENT;

            List<String> l_listOfContinentsToRemove = new ArrayList<>();
            String[] l_editremovecmd = p_editremovecmd.split(",");
            int l_i = 0;

            String commandToCheck = "-remove";

            while (l_i < l_editremovecmd.length) {

                // System.out.println(l_editremovecmd[l_i] + ":" + commandToCheck);
                if (l_editremovecmd[l_i].toString().equals(commandToCheck)) {

                    l_i++;
                    continue;

                } else {
                    l_listOfContinentsToRemove
                            .add(listOfContinentsResource.get(Integer.parseInt(l_editremovecmd[l_i])));
                    // System.out.println("listofContinents" + l_listOfContinentsToRemove);
                    l_i++;
                }

            }
            try {
                d_mapFeatures.removeContinentFromFile(l_listOfContinentsToRemove, ge);
                System.out.println("Continent removed succesfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            l_logObject.setStatus(true, "Continents removed successfully");
            l_logEntryBuffer.notifyClasses(l_logObject);
        }
    }

    public void editNeighbor(String p_editcmd, String p_editremovecmd) {
        LogObject l_logObject = new LogObject();
        l_logObject.setD_command("editneighbor " + p_editcmd + " " + p_editremovecmd);

        if (p_editcmd != null && p_editcmd != "") {
            Map<String, String> listofBorders = new HashMap<String, String>();
            String[] editCmd = p_editcmd.split(",");

            int l_i = 0;

            String l_commandToCheck = "-add";
            while (l_i < editCmd.length) {

                if (editCmd[l_i].toString().equals(l_commandToCheck)) {
                    l_i++;
                    continue;

                } else {
                    listofBorders.put(editCmd[l_i],
                            listofBorders.get(editCmd[l_i]) != null
                                    ? listofBorders.get(editCmd[l_i]).toString() + " " + editCmd[l_i + 1].toString()
                                    : editCmd[l_i + 1]);
                    l_i += 2;

                }

            }
            try {
                d_mapFeatures.writeCountriesNeighborToFile(listofBorders, ge);

            } catch (IOException e) {
                e.printStackTrace();

            }
            l_logObject.setStatus(true, "Country borders added successfully");
            l_logEntryBuffer.notifyClasses(l_logObject);
            System.out.println("Country borders addded succesfully");
        } else {

            ge.prevUserCommand = Commands.REMOVENEIGHBOUR;

            String[] l_editremovecmd = p_editremovecmd.split(",");
            int l_i = 0;

            String commandToCheck = "-remove";
            Map<String, String> listofBorders = new HashMap<String, String>();

            while (l_i < l_editremovecmd.length) {

                if (l_editremovecmd[l_i].toString().equals(commandToCheck)) {

                    l_i++;
                    continue;

                } else {
                    listofBorders.put(l_editremovecmd[l_i],
                            listofBorders.get(l_editremovecmd[l_i]) != null
                                    ? listofBorders.get(l_editremovecmd[l_i]).toString() + " "
                                            + l_editremovecmd[l_i + 1].toString()
                                    : l_editremovecmd[l_i + 1]);
                    l_i += 2;
                }

            }
            try {
                d_mapFeatures.removeborderFromFile(listofBorders, ge);

            } catch (IOException e) {
                e.printStackTrace();

            }
            System.out.println("Borders removed succesfully");
        }
        l_logObject.setStatus(true, "Country borders removed successfully");
        l_logEntryBuffer.notifyClasses(l_logObject);
        System.out.println("You cannot use edit neighbor command now.");
    }

    public void saveMap() {
        // Call savemap func after creation of it
        ge.setPhase(new PlaySetup(ge));
    }

    public void next() {
        System.out.println("must save map");
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
