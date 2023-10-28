package project.app.warzone.Model;

import project.app.warzone.Commands.MapEditorCommands;

public abstract class Edit extends Phase{

    public MapEditorCommands dMapEditorCommands;

    public Map dMap;
    String p_filename = dMap.get_USER_SELECTED_FILE();
    
    public Edit() {
    }

    public Edit(GameEngine p_ge) {
        ge = p_ge;
    }

    public void showMap() {
        dMapEditorCommands.loadMap(p_filename);
        dMapEditorCommands.showmap();
        System.out.println("Edited map is displayed");
    }

    public void setPlayers() {
        printInvalidCommandMessage();
    }
    public void assignCountries() {
        printInvalidCommandMessage();
    }
    public void reinforce() {
        printInvalidCommandMessage();
    }
    public void attack() {
        printInvalidCommandMessage();
    }
    public void fortify() {
    printInvalidCommandMessage();
    }
    public void endGame() {
    printInvalidCommandMessage();
    }
}
