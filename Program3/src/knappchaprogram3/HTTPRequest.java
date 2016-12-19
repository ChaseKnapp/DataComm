/*
 * HTTPRequest has socket and bufferd reader. Checks exceptions and errors.
 */
package knappchaprogram3;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Chase
 */
public class HTTPRequest extends Thread
{
   private PrintWriter write;
   private BufferedReader read;
   private Socket sock;
   private HTTP httpVar;
   private static String responseVar = "\r\n";
   private static String httpV = "HTTP/1.0 ";

   
   /*
   intializes socket and sets writer and reader
   */
   public HTTPRequest(Socket sock)
   {
      this.sock = sock;
      try
      {
         write = new PrintWriter(sock.getOutputStream(), true);
         read = new BufferedReader(new InputStreamReader(
               sock.getInputStream()));
      }
      catch (Exception e)
      {
         System.out.println("HTTPRequest Exception: " + e);
      }
   }

   @Override
   public void start()
   {
      try
      {
         httpVar = new HTTP(read.readLine());
         write.print(stringResp());
         write.print(conType());
         sendHSock();
         sock.close();
      }
      catch (Exception e)
      {
         System.out.println("HTTPRequest Exception: " + e);
      }

   }
   /*
   returns message codes
   */
    private String getCode()
   {
      if (httpVar.validCheck())
         return "200 OK ";
      else
         return "404 NotFound ";
   }
   /*
    sends socket to HTTP
    */ 
   private void sendHSock()
   {
      httpVar.sendSock(sock);
   }
   
   /*
   displays content type
   */
   private String conType()
   {
      return "Content-type: " + httpVar.getType();
   }

   /*
   creates respons with message code
   */
   private String stringResp()
   {
      String response = httpV;
      response = response + getCode() + responseVar;

      return response;
   }
}
