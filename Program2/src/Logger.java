
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 Log File

 */
public class Logger
{
   private static final String File = "serverLog.log";
   public Logger()
   {
      print("\n\nLogger has been started ");
   }
   public void print(String message)
   {
      try (PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(File, true))))
      {
         out.println(message);
      }
      catch (Exception e)
      {
         System.out.println("Error: " + e);
      }
   }
}
