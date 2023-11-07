package project.app.warzone.Model;


import project.app.warzone.Commands.MapEditorCommands;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.MapResources;


public class Preload extends Edit {
    
    public MapEditorCommands dMapEditorCommands;
    public MapResources d_mapResources;

    Preload(GameEngine p_ge) {
		super(p_ge);
        d_mapResources = new MapResources();
	} 
    
    public void loadMap(String p_filename) {
        if(ge.gameMap.fileExists(p_filename)){
            System.out.println("One file found.");
            ge.gameMap.set_USER_SELECTED_FILE(p_filename);
            System.out.println("Choose one of the below commands to proceed:\n 1. showmap 2.editmap");
            next();
        } else {
            System.out.println("Map not found.");
        }
    }

    public void editMap(String p_fileName){
        if (ge.gameMap.fileExists(p_fileName)) {
            System.out.println("One file found.");
            ge.gameMap.set_USER_SELECTED_FILE(p_fileName);
            //TODO: add map validation
            // showmap(); // add check to see if file is proper
            System.out.println("Please use gameplayer -add command to add players in the game");
        } else {
            ge.prevUserCommand = Commands.EDITMAP;
            ge.gameMap.set_USER_SELECTED_FILE(p_fileName);

            ge.gameMap.createNewMapFile(p_fileName);
            System.out.println("\n");
            System.out.println(
                    "Choose one of the below commands to proceed:\n 1.editcontinent 2.editcountry 3.editneighbor");
            System.out.println("\n");
            System.out.println("Continet list:::: Select the continents you need to add");
            System.out.println("----------------------------------------------------------");
            d_mapResources.printMapDetails(d_mapResources.getAllContinents());
            System.out.println("\n");
            System.out.println("Countries list:::: Select the Countries you need to add");
            System.out.println("----------------------------------------------------------");

            d_mapResources.printMapDetails(d_mapResources.getAllCountries());

            System.out.println("\n");
            System.out.println("Please select the add or remove command");

        }
        next();
	}
    public void editNeighbor(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}

    public void editCountry(String p_editcmd,String p_editremovecmd) {
        printInvalidCommandMessage(); 
    }	

	public void saveMap() {
		printInvalidCommandMessage(); 
	}

    public void editContinent(String p_editcmd,String p_editremovecmd) {
		printInvalidCommandMessage(); 
	}

    public void showstats() {
        printInvalidCommandMessage(); 
    }
    public void reinforce(int p_countryID, int p_armies) {
		printInvalidCommandMessage(); 
	}

	public void next() {
        ge.setPhase(new Postload(ge));
	}
}
