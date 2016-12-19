package Server;
import java.io.*; 
import java.net.*;
import java.util.*;

/**
 * This class implements a server for UDP service. It sits in an infinite loop
 * listening for incoming UDP packets. When a packet comes in, the Server simply 
 * retrieves the payload, encapsulates the payload in a UDP packet, and sends
 * the packet to the Client.
 * @author Kevin Komro Chase Knapp
 */
public class PingServer 
{
   final int SIZE = 512;
   final int PORT_NUM = 5701;
   final double LOSS_RATE = 0.3;
   final int AVG_DELAY = 100;
   final int DUB = 2;
   
   /**
    * Main Method. Creates a new PingServer object, calls it's run method
    * @param args . 
    */
   public static void main(String args[]) 
   {
      try
      {
         System.out.println("Ping Server running....");
         PingServer server = new PingServer();
         server.run();
      }
      catch (Exception ex)
      {
         System.out.println("PingServer Exception: " + ex);
      }
   }
   
   /**
    * Creates new pingthread, accepts connections
    */
   public void run()
   {
      try
      {
         Random random = new Random();
         DatagramSocket udpSock = new DatagramSocket(PORT_NUM);
         byte[] dataByte = new byte[SIZE];
         while (true)
         {
            System.out.println("Waiting for UDP packet....");
            DatagramPacket inpack = new DatagramPacket(dataByte, SIZE);
            udpSock.receive(inpack);
            printData(inpack);
            if(random.nextDouble() < LOSS_RATE)
               System.out.println("Packet loss...., packet not sent.");
            else
            {
               Thread.sleep((int)(random.nextDouble() * DUB * AVG_DELAY));
               InetAddress clientHost = inpack.getAddress();
               int clientPort = inpack.getPort();
               byte[] byt = inpack.getData();
               DatagramPacket reply = 
                  new DatagramPacket(byt, byt.length, clientHost, clientPort);
               udpSock.send(reply);
               System.out.println("Reply sent.");
            }
         }
      }
      catch (Exception ex)
      {
         System.out.println("PingServer Exception: " + ex);
      }
   }
      
   /**
    * Prints ping data to the output stream.
    * @param request datagram packet from client. 
    */
   private void printData(DatagramPacket request)
   {
      try
      {
         byte[] data = request.getData();
         ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
         InputStreamReader inputS = new InputStreamReader(byteIn);
         BufferedReader read = new BufferedReader(inputS);
         String outLine = read.readLine();
         System.out.println("Received from: " + 
            request.getAddress().getHostAddress() + " " + new String(outLine));
      }
      catch (Exception ex)
      {
         System.out.println("PingServer Exception: " + ex);
      }  
   }
}
