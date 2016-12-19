
import java.io.*;
import java.net.*;

/**
 Sends to server
 */
public class ServerThread extends Thread
{
   private Socket sock;
   private Logger log;
   private PrintWriter out;
   private BufferedReader in;
   

   public ServerThread(Socket sock, Logger log)
   {
      this.log = log;
      this.sock = sock;
      try
      {
         out = new PrintWriter(sock.getOutputStream(), true);
         in = new BufferedReader(new InputStreamReader(
               sock.getInputStream()));
      }
      catch (Exception e)
      {
         log.print("Error: " + e.getClass().toString() + e);
      }
   }

   @Override
   public void run()
   {
      try
      {
         PolyAlphabet pa = new PolyAlphabet();
         boolean quit = false;
         while (!quit)
         {
            if (!sock.isClosed())
            {
               String inLine = in.readLine();
               if (inLine.equalsIgnoreCase("Quit"))
                  quit = true;
               else
               {
                  String outLine = pa.encode(inLine);
                  out.println(outLine);
               }
            }
            else
                quit = true;
         }
         out.println("Good Bye!");
         sock.close();
         log.print("\tConnection Closed. Port " + sock.getPort());
      }
      catch (Exception e)
      {
         log.print("\tConnection Closed. Port " + sock.getPort());
      }

   }
}
