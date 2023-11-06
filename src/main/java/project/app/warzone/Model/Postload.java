package project.app.warzone.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Utilities.Commands;

public class Postload extends Edit {
	MapFeatures d_mapFeatures = MapFeatures.getInstance() ;
	Postload(GameEngine p_ge) {
        super(p_ge);
    }

    public String loadMap(String p_filename) {
		printInvalidCommandMessage(); 
		return null;
	}
	
	public String showMap() {
        System.out.println("show map");
        String p_mapLocation=ge.gameMap.getMapDirectory()+"//"+ge.gameMap.get_USER_SELECTED_FILE()+".map";
        Boolean l_result=false;
        ge.gameMap = d_mapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if(!l_result){
            return("This map is not valid.Please try with some other map");
        }
        else{
            return("You can now proceed to add gameplayers");
        }
	}

	public String editCountry(String p_editcmd,String p_editremovecmd) {
        //Please call this function
        //dMapEditorCommands.editcountry("-a India", "-r India");
        if (ge.prevUserCommand == Commands.ADDCONTINENT
                || ge.prevUserCommand == Commands.REMOVECONTINENT
                || ge.prevUserCommand == Commands.ADDCOUNTRY
                || ge.prevUserCommand == Commands.REMOVECOUNTRY) {

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
                return ( "Countries addded succesfully");
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
                return( "Countries removed succesfully");
            }
        } else {
            return ("You cannnot add country");

        }

		//return ("country has been edited");
	}

	public void saveMap() {
		//Call savemap func after creation of it
		ge.setPhase(new PlaySetup(ge));
	}

	public void next() {
		System.out.println("must save map");
	}
}
