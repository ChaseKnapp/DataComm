/*
 * Web server run by main. creates HTTPRequest. 
 */
package knappchaprogram3;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Chase
 */
public class WebServer
{

   private static final int port = 5764;
   /**
    * Starts server and writes running message. Catches exceptions.
    */
   public void run()
   {
      try
      {
         ServerSocket servSock = new ServerSocket(port);
         System.out.println("Server is up and running");
         while (true)
         {
            Socket newSock = servSock.accept();
            HTTPRequest newHTTPRequest = new HTTPRequest(newSock);
            System.out.println("Connection from " + newSock.getInetAddress() 
                  + " on port " + newSock.getPort());
            newHTTPRequest.start(); 
         }
      }
      catch (Exception e)
      {
         System.out.println("Socket Exception: " + e);
      }
   }
}
