package project.app.warzone.Model;

import project.app.warzone.Commands.MapEditorCommands;

public class Postload extends Edit {

    public MapEditorCommands dMapEditorCommands;
    Postload Mypostloader = new Postload(); 
    GameEngine ge = new GameEngine(dMapEditorCommands.returnMap());// map object is required to be passed here
    
    public Postload() {
        System.out.println("Inside Postload COnstructor");
    }
    
    public Postload(GameEngine p_ge) {
        System.out.println("Inside Postload");
        ge = p_ge;

    }
    
    public void loadMap() {
        System.out.println("map has been loaded");
    }
    
    public void editCountry() {
        System.out.println("country has been edited");
    }
    
    public void saveMap() {
        System.out.println("map has been saved");
        //ge.setPhase(new Playsetup(ge));
    }
    
    public void next() {
        System.out.println("must save map");
    }
}
