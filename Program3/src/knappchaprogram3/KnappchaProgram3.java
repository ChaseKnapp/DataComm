/*
 * Startup object that runs the server.
 */
package knappchaprogram3;

/**
 *
 * @author Chase
 */
public class KnappchaProgram3 {

    public static void main(String[] args) {
      try
      {
         WebServer server = new WebServer();
         server.run();
      }
      catch (Exception e)
      {
         System.out.println("Error running server:" + e);
      }
    }
    
}
