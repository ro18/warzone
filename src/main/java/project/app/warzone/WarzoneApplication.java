package project.app.warzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.app.warzone.Model.GameEngine;

/**
 * main class for the warzone application
 */
@SpringBootApplication
public class WarzoneApplication {

	/**
	 * main method for warzone		
	 * 
	 * @param args			string
	 */
	public static void main(String[] args) {
		SpringApplication.run(WarzoneApplication.class, args);
		//System.out.println("test");
		// testing aish
		// start void
		GameEngine l_GameEngine = new GameEngine();
		System.out.println("test GE");
		l_GameEngine.start();
		
		
	}

}
