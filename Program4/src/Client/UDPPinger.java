package Client;
import java.net.*;
import java.util.*;

/**
 * SENDS PING MESSAGE AND RECEIVES ECHO FROM SERVER
 * Kevin Komro Chase Knapp
 */
public class UDPPinger 
{
   DatagramSocket sock; 
   final int PACKET_SIZE = 512;
   
   /**
    * Instantiates new Datagram Socket.
    */
   public void createSocket() 
   {   
      try 
      {   
         sock = new DatagramSocket();   
      } 
      catch (Exception ex) 
      {   
         System.out.println("UDPPinger Exception: " + ex);   
      }   
   } 
   
   /**
    * Sends UDP packet with an instance of PingMessage.
    * @param ping instance of PingMessage.
    */
   public void sendPing(PingMessage ping)
   {
      InetAddress host = ping.getIP();
      int port = ping.getPort();
      String msg = ping.getPayload();
      try
      {
         DatagramPacket packet = 
            new DatagramPacket(msg.getBytes(), msg.length(), host, port);
         sock.send(packet);
      }  
      catch (Exception ex)
      {
         System.out.println("UDPPinger Exception: " + ex);
      }
   }
   
   /**
    * Receives the UDP packet from server. May throw SocketTimeoutException.
    * @return UDP packet. 
    */
   public PingMessage receivePing()
   {
      Date date = new Date();
      String dString = String.format("%tc", date);
      byte recvBuf[] = new byte[PACKET_SIZE];
      PingMessage pMsg = null;
      DatagramPacket inPack = 
	 new DatagramPacket(recvBuf, PACKET_SIZE);
      
      try
      {
         sock.receive(inPack);
         System.out.println("Received packet from " + inPack.getAddress()
            + " " + inPack.getPort() + " " + dString);
         String recvMsg = new String(inPack.getData());
	 pMsg = new PingMessage(inPack.getAddress(),
            inPack.getPort(), recvMsg);
      }
      catch (Exception ex)
      {
         System.out.println("receivePing...java.net.SocketTimeoutException: " +
            "Receive timed out");
      }
      return pMsg;
   } 
}
