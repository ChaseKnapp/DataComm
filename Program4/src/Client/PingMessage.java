package Client;
import java.net.*;

/**
 * Creates ping message to send to server.
 * Kevin Komro Chase Knapp
 */
public class PingMessage 
{
   InetAddress IPA;
   int portNo;
   String dataPayload;
   
   /**
    * Constructor for the PingMessage class.
    * @param addr Destination IP Address.
    * @param port Destination Port Number.
    * @param payload datagram payload. 
    */
   public PingMessage(InetAddress addr, int port, String payload)
   {
      IPA = addr;
      portNo = port;
      dataPayload = payload;
   }
   
   /**
    * Gets servers port Number
    * @return Server's Port Number
    */
   public int getPort()
   {
      return portNo;
   }
    /**
    * Gets server IP
    * @return Server's IP 
    */
   
   public InetAddress getIP()
   {
      return IPA;
   }
   /**
    * Gets Client's Payload
    * @return Payload of the Client 
    */
   public String getPayload()
   {
      return dataPayload;
   }
}
