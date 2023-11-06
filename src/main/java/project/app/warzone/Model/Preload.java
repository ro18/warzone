package project.app.warzone.Model;


import project.app.warzone.Commands.MapEditorCommands;


public class Preload extends Edit {
    
    public MapEditorCommands dMapEditorCommands;

    Preload(GameEngine p_ge) {
		super(p_ge);
	} 
    
    public String loadMap(String p_filename) {
        if(ge.gameMap.fileExists(p_filename)){
            System.out.println("One file found.");
            ge.gameMap.set_USER_SELECTED_FILE(p_filename);
            ge.setPhase(new Postload(ge));
            return "Choose one of the below commands to proceed:\n 1. showmap 2.editmap";
        } else {
            return "Map not found.";
        }
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
