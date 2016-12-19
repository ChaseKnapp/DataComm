
/**
 Starts server
 */
public class Main
{

   public static void main(String[] args)
   {
      try
      {
         Server main = new Server();
         main.run();
      }
      catch (Exception e)
      {
         System.out.println("Error: " + e);
      }

   }
}
