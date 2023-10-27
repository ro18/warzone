package project.app.warzone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		String l_currentDirectory = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/gameplay.log";
		File l_file = new File(l_currentDirectory);
			try {
				BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_file, !(l_file.exists() && !l_file.isDirectory())));
				l_writer.write("Game Commnece\n");
				l_writer.close();
			} catch (Exception e) {
				System.out.println("Error Reading file");
			}
		
		SpringApplication.run(WarzoneApplication.class, args);
	}

}
