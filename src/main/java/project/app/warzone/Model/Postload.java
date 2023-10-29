package project.app.warzone.Model;

import project.app.warzone.Commands.MapEditorCommands;
import project.app.warzone.Model.Map;

public class Postload extends Edit {

    public MapEditorCommands dMapEditorCommands;
    Map dMap = new Map();
    GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
    String p_filename = dMap.get_USER_SELECTED_FILE();

	Postload(GameEngine p_ge) {
        super(p_ge);
    }
	
	public void showMap() {
		System.out.println("G **************************************************************** Postload Shomap");

        dMapEditorCommands.showmap();
        System.out.println("Map is Dsiplayed");
	}

	public void loadMap() {
        dMapEditorCommands.loadMap(p_filename);
        System.out.println("Map is loaded");
	}

	public void editCountry() {
        //Please call this function
        //dMapEditorCommands.editcountry("-a India", "-r India");
		System.out.println("country has been edited");
	}

	public void saveMap() {
		//Call savemap func after creation of it
		ge.setPhase(new PlaySetup(ge));
	}

	public void next() {
		System.out.println("must save map");
	}
}
