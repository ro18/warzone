package project.app.warzone.Model;


import project.app.warzone.Commands.MapEditorCommands;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Utilities.Commands;
import project.app.warzone.Utilities.MapResources;


public class Preload extends Edit {

    private MapFeatures d_mapFeatures = MapFeatures.getInstance();    
    public MapEditorCommands dMapEditorCommands;
    public MapResources d_mapResources;

    Preload(GameEngine p_ge) {
		super(p_ge);
        d_mapResources = new MapResources();
	} 

    private void validateMap() {
        String p_mapLocation = ge.gameMap.getMapDirectory() + "//" + ge.gameMap.get_USER_SELECTED_FILE() + ".map";
        Boolean l_result = false;
        ge.gameMap = d_mapFeatures.readMap(p_mapLocation);
        l_result = d_mapFeatures.validateEntireGraph(ge);
        if (!l_result) {
            System.out.println("This map is not valid.Please try with some other map");
        } else {
            System.out.println("Map is validated. You can now proceed to add gameplayers");
        }
    }
    
    public void loadMap(String p_filename) {
        if(ge.gameMap.fileExists(p_filename)){
            System.out.println("Current Phase: Preload");
            System.out.println("One file found");
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
            validateMap(); // add check to see if file is proper
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
