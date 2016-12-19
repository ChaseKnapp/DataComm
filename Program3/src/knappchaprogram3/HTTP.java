/*
 * HTTP class. checks if url is valid. if not displays error on webpage. 
 */
package knappchaprogram3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Chase
 */
public class HTTP
{

   private static final int cSize = 1024;
    private FileInputStream fileIn;
   private OutputStream out;
   private String url;
   private boolean flag = true;
   
   /* constructor
   */
   public HTTP(String request)
   {
      tokenReq(request);
   }
   /* checks if URL is valid with try,catch
   */
   public boolean validCheck()
   {
      try
      {
         fileIn = new FileInputStream(url);
      }
      catch (FileNotFoundException fe)
      {
         
         flag = false;
         return false;
      }
      return true;
   }
   /* string tokenizer for requests
   */
   private void tokenReq (String request)
   {
      StringTokenizer st = new StringTokenizer(request);
      if (st.hasMoreTokens())
      {
         st.nextToken();
      }
      if (st.hasMoreTokens())
      {
         this.url = "." + st.nextToken();
      }
   }
   /* gets type of file of the url, returns appropriate message
   */
   public String getType()
   {
      if (url.endsWith(".html") || url.endsWith(".htm"))
         return "text/html";
      if (url.endsWith(".gif"))
         return "image/gif";
      if (url.endsWith(".bmp"))
         return "image/bmp";
      if (url.endsWith(".jpg") || url.endsWith(".jpeg"))
         return "image/jpeg";
      return "application/octet-stream";
   }
   /*
    sends file, prints message with url. If file not found displays not found
   */  
   public boolean sendSock(Socket sock)
   {
      try
      {
         out = sock.getOutputStream();
         byte[] buffer = new byte[cSize];
         int bytes = 0;
         if (flag)
         {
            while ((bytes = fileIn.read(buffer)) != -1)
            {
               out.write(buffer, 0, bytes);
            }
            System.out.println("File sent: " + url);
         }
         else
         {
            String entityBody = "<HTML>"+"<HEAD><TITLE>Not Found</TITLE></HEAD>"
                                            + "<BODY>Not Found</BODY></HTML>";
            byte[] token = entityBody.getBytes();
            out.write(token);
            System.out.println("No file sent!");
         }
      }
      catch (Exception e)
      {
         System.out.println("HTTP Exception: " + e);
      }
      return true;
   }

   

}
