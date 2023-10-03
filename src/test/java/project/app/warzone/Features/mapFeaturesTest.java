package test.easycodeforall.changeit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.apache.log4j.Logger;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
public class MapFeaturesTest {

   private Logger log = Logger.getLogger(this.getClass());
   @BeforeAll
   static void initAll() {}
   @BeforeEach
   void init() {}

   @Test
   @DisplayName("read Map")
   public void readMap() {
      try {
         log.info("Starting execution of readMap");
         Map expectedValue = null;
         String filename = "";

         MapFeatures mapfeatures = new MapFeatures();
         Map actualValue = mapfeatures.readMap(filename);
         log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         System.out.println("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         Assertions.assertEquals(expectedValue, actualValue);
      } catch (Exception exception) {
         log.error("Exception in execution of execute1GetAllLogFromFirstMovF-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }

   @Test
   @DisplayName("create Map")
   public void createMap() {
      try {
         log.info("Starting execution of createMap");

         MapFeatures mapfeatures = new MapFeatures();
         mapfeatures.createMap();
         Assertions.assertTrue(true);
      } catch (Exception exception) {
         log.error("Exception in execution ofcreateMap-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }

   @Test
   @DisplayName("print Map")
   public void printMap() {
      try {
         log.info("Starting execution of printMap");
         Map gameMap = null;

         MapFeatures mapfeatures = new MapFeatures();
         mapfeatures.printMap(gameMap);
         Assertions.assertTrue(true);
      } catch (Exception exception) {
         log.error("Exception in execution ofprintMap-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }

   @Test
   @DisplayName("validate By Nodes")
   public void validateByNodes() {
      try {
         log.info("Starting execution of validateByNodes");
         HashMap < Node, Boolean > expectedValue = null;
         List < Node > p_allNodes = null;
         HashMap < Node, Boolean > l_visitedList = null;

         MapFeatures mapfeatures = new MapFeatures();
         HashMap < Node, Boolean > actualValue = mapfeatures.validateByNodes(p_allNodes, l_visitedList);
         log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         System.out.println("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         Assertions.assertEquals(expectedValue, actualValue);
      } catch (Exception exception) {
         log.error("Exception in execution of execute1GetAllLogFromFirstMovF-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }

   @Test
   @DisplayName("validate Entire Graph")
   public void validateEntireGraph() {
      try {
         log.info("Starting execution of validateEntireGraph");
         Boolean expectedValue = false;
         GameEngine gameEngine = null;

         MapFeatures mapfeatures = new MapFeatures();
         Boolean actualValue = mapfeatures.validateEntireGraph(gameEngine);
         log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         System.out.println("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         Assertions.assertEquals(expectedValue, actualValue);
      } catch (Exception exception) {
         log.error("Exception in execution of execute1GetAllLogFromFirstMovF-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }

   @Test
   @DisplayName("validate Sub Graph")
   public void validateSubGraph() {
      try {
         log.info("Starting execution of validateSubGraph");
         boolean expectedValue = false;
         Continent con = null;
         List < Node > l_listOfNodes = null;
         HashMap < Node, Boolean > l_visitedList = null;

         MapFeatures mapfeatures = new MapFeatures();
         boolean actualValue = mapfeatures.validateSubGraph(con, l_listOfNodes, l_visitedList);
         log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         System.out.println("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         Assertions.assertEquals(expectedValue, actualValue);
      } catch (Exception exception) {
         log.error("Exception in execution of execute1GetAllLogFromFirstMovF-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }

   @Test
   @DisplayName("depth First Search")
   public void depthFirstSearch() {
      try {
         log.info("Starting execution of depthFirstSearch");
         HashMap < Node, Boolean > expectedValue = null;
         Node currentCountry = null;
         HashMap < Node, Boolean > l_visitedList = null;

         MapFeatures mapfeatures = new MapFeatures();
         HashMap < Node, Boolean > actualValue = mapfeatures.depthFirstSearch(currentCountry, l_visitedList);
         log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         System.out.println("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
         Assertions.assertEquals(expectedValue, actualValue);
      } catch (Exception exception) {
         log.error("Exception in execution of execute1GetAllLogFromFirstMovF-" + exception, exception);
         exception.printStackTrace();
         Assertions.assertFalse(false);
      }
   }
   @AfterEach
   void tearDown() {}
   @AfterAll
   static void tearDownAll() {}
}
