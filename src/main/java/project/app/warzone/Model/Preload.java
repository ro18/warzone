package project.app.warzone.Model;


import project.app.warzone.Commands.MapEditorCommands;


public class Preload extends Edit {
    
    public MapEditorCommands dMapEditorCommands;
    Preload MyPreloader = new Preload(); 
    GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
    String p_filename = dMap.get_USER_SELECTED_FILE();
   


    public Preload() {
         System.out.println("Inside Preload CONSTRUCTOR");
    }

    public Preload(GameEngine p_ge) {
        ge = p_ge;
        System.out.println("Inside Preload Main");
    }
    
    public void loadMap() {
        dMapEditorCommands.loadMap(p_filename);
        System.out.println("Map is loaded");
       
    }

    public void showMap() {
        dMapEditorCommands.showmap();
        System.out.println("Map is displayed");
        ge.setPhase(new Postload(ge));
    }

    public void editmap() {
        dMapEditorCommands.editmap(p_filename);
        System.out.println("Map is displayed");
        ge.setPhase(new Postload(ge));
    }

    public void editCountry() {
        printInvalidCommandMessage();
    }
    //edit continent and edit neighbor are not required in preload state
    public void saveMap() {
        printInvalidCommandMessage();
    }
    public void next() {
        System.out.println("must load map");
    }
}
