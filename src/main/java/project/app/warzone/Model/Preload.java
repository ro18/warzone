package project.app.warzone.Model;


import project.app.warzone.Commands.MapEditorCommands;


public class Preload extends Edit {
    
    public MapEditorCommands dMapEditorCommands;
    // Map dMap = new Map();
    // GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
    // String p_filename = dMap.get_USER_SELECTED_FILE();

    Preload(GameEngine p_ge) {
		super(p_ge);
	} 
    
    public void loadMap() {
        System.out.println("G **************************************************************** preload loadmap");
        // dMapEditorCommands.loadMap(p_filename);
        System.out.println("Map is loaded");
        ge.setPhase(new Postload(ge));
    }

    public String editCountry(String p_editcmd,String p_editremovecmd) {
        printInvalidCommandMessage(); 
        return null;
    }
	

	public void saveMap() {
		printInvalidCommandMessage(); 
	}

	public void next() {
		System.out.println("must load map");
	}
}
