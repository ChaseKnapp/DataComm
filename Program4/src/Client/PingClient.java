   package Client; 
   import java.net.*;
   import java.util.*;
   import java.util.Scanner;

   /**
    * Client Class. Extends the UDPPinger class and
    * implements the Java Runnable Interface. Creates and sends 10 datagrams
    * to Server.
    * @author Kevin Komro Chase Knapp
    */
   public class PingClient extends UDPPinger implements Runnable
   {
      static String rHost = "";;
      static Scanner stdin;
      final static int rPort = 5701;
      final int TIMEOUT = 1000;
      final int REPLY_TIMEOUT = 5000;
      final int NO_PINGS = 10;
      int numReplies = 0;
      boolean[] replies = new boolean[10];
      long[] rtt = new long[10];
      long[] temp2 = new long[10];


      /**
       * MAIN FUNCTION FOR PINGCLIENT.
       * @param args
       */
      public static void main(String args[])
      {
         stdin = new Scanner(System.in);
         System.out.println( "Enter IP Address of Server");
         String host = rHost;
         rHost = stdin.next();
         int port = rPort;
         System.out.println("Contacting host " + host + " at port " + port);
         PingClient client = new PingClient();
         client.run();
       }

      /**
       * CREATES A DATAGRAM SOCKET, SENDS PINGS
       */
      public void run()
      {
         createSocket();
         try
         {
            sendPing();
            sock.setSoTimeout(REPLY_TIMEOUT);
            while (numReplies < NO_PINGS)
            {
               try
               {
                  PingMessage reply = receivePing();
                  calcReply(reply.getPayload());
               }
               catch (Exception ex)
               {
                  numReplies = NO_PINGS;
               }
            }
            for (int i = 0; i < NO_PINGS; i++)
            {
               System.out.println("PING " + i + ": " + replies[i] + " RTT: "
                  + Long.toString(rtt[i]) + " ms");
            }
            long min = getMinVal(rtt);
            long max = getMaxVal(rtt);
            double avg = average(rtt);
            System.out.println("Minimum = " + min + "ms, Maximum = " + max
               + "ms, Average = " + avg + "ms.");
         }
         catch (Exception ex)
         {
            System.out.println("PingClient Exception: " + ex);
         }
      }

      /**
       * SENDS 10 PINGS TO THE SERVER.
       */
      private void sendPing()
      {
         try
         {
            sock.setSoTimeout(TIMEOUT);
            for (int i = 0; i < NO_PINGS; i++)
            {
               Date now = new Date();
               Long temp = now.getTime();
               String message = "PING " + i + " " + temp;
               temp2[i] = temp;
               replies[i] = false;
               rtt[i] = TIMEOUT;
               PingMessage ping = null;
               ping = new PingMessage(InetAddress.getByName(rHost),
                  rPort, message);
               sendPing(ping);
               try
               {
                  PingMessage reply = receivePing();
                  calcReply(reply.getPayload());
               }
               catch (Exception ex)
               {
               }
            }
         }
         catch (Exception ex)
         {
            System.out.println("PingClient Exception: " + ex);
         }
      }

      /**
       * CALCULATES RTT.
       * @param reply ECHOING PING.
       */
      private void calcReply(String reply)
      {
         String[] tmp = reply.split(" ");
         int pingNumber = Integer.parseInt(tmp[1]);
         replies[pingNumber] = true;
         Date now = new Date();
         rtt[pingNumber] = now.getTime() - temp2[pingNumber];
         numReplies++;
      }

      /**
       * CALCULATES MAX VALUE.
       * @param array ARRAY
       * @return MAX VALUE
       */
      private static long getMaxVal(long[] array)
      {
         long maxValue = array[0];
         for(int i = 1; i < array.length; i++)
         {
            if(array[i] > maxValue)
            {
               maxValue = array[i];
            }
         }
         return maxValue;
      }

      /**
       * CALCULATES MIN VALUE.
       * @param array ARRAY
       * @return MIN VALUE
       */
      private static long getMinVal(long[] array)
      {
         long minValue = array[0];
         for(int i = 1; i < array.length; i++)
         {
            if(array[i] < minValue)
            {
               minValue = array[i];
            }
         }
         return minValue;
      }

      /**
       * CALCULATES AVERAGE
       * @param data ARRAY
       * @return AVERAGE
       */
      private double average(long[] data)
      {
         long sum = 0;
         for(int i = 0; i < data.length; i++)
            sum = sum + data[i];
         double average = (double)sum / data.length;
         return average;
      }
   }


   /**
    * MAIN FUNCTION FOR PINGCLIENT.
    * @param args
    */
   public static void main(String args[])
   {
      String host = rHost;
      int port = rPort;
      System.out.println("Contacting host " + host + " at port " + port);
      PingClient client = new PingClient();
      client.run();
    }

   /**
    * CREATES A DATAGRAM SOCKET, SENDS PINGS
    */
   public void run()
   {
      createSocket();
      try
      {
         sendPing();
         sock.setSoTimeout(REPLY_TIMEOUT);
         while (numReplies < NO_PINGS)
         {
            try
            {
               PingMessage reply = receivePing();
               calcReply(reply.getPayload());
            }
            catch (Exception ex)
            {
               numReplies = NO_PINGS;
            }
         }
         for (int i = 0; i < NO_PINGS; i++)
         {
            System.out.println("PING " + i + ": " + replies[i] + " RTT: "
               + Long.toString(rtt[i]) + " ms");
         }
         long min = getMinVal(rtt);
         long max = getMaxVal(rtt);
         double avg = average(rtt);
         System.out.println("Minimum = " + min + "ms, Maximum = " + max
            + "ms, Average = " + avg + "ms.");
      }
      catch (Exception ex)
      {
         System.out.println("PingClient Exception: " + ex);
      }
   }

   /**
    * SENDS 10 PINGS TO THE SERVER.
    */
   private void sendPing()
   {
      try
      {
         sock.setSoTimeout(TIMEOUT);
         for (int i = 0; i < NO_PINGS; i++)
         {
            Date now = new Date();
            Long temp = now.getTime();
            String message = "PING " + i + " " + temp;
            temp2[i] = temp;
            replies[i] = false;
            rtt[i] = TIMEOUT;
            PingMessage ping = null;
            ping = new PingMessage(InetAddress.getByName(rHost),
               rPort, message);
            sendPing(ping);
            try
            {
               PingMessage reply = receivePing();
               calcReply(reply.getPayload());
            }
            catch (Exception ex)
            {
            }
         }
      }
      catch (Exception ex)
      {
         System.out.println("PingClient Exception: " + ex);
      }
   }

   /**
    * CALCULATES RTT.
    * @param reply ECHOING PING.
    */
   private void calcReply(String reply)
   {
      String[] tmp = reply.split(" ");
      int pingNumber = Integer.parseInt(tmp[1]);
      replies[pingNumber] = true;
      Date now = new Date();
      rtt[pingNumber] = now.getTime() - temp2[pingNumber];
      numReplies++;
   }

   /**
    * CALCULATES MAX VALUE.
    * @param array ARRAY
    * @return MAX VALUE
    */
   private static long getMaxVal(long[] array)
   {
      long maxValue = array[0];
      for(int i = 1; i < array.length; i++)
      {
         if(array[i] > maxValue)
         {
            maxValue = array[i];
         }
      }
      return maxValue;
   }

   /**
    * CALCULATES MIN VALUE.
    * @param array ARRAY
    * @return MIN VALUE
    */
   private static long getMinVal(long[] array)
   {
      long minValue = array[0];
      for(int i = 1; i < array.length; i++)
      {
         if(array[i] < minValue)
         {
            minValue = array[i];
         }
      }
      return minValue;
   }

   /**
    * CALCULATES AVERAGE
    * @param data ARRAY
    * @return AVERAGE
    */
   private double average(long[] data)
   {
      long sum = 0;
      for(int i = 0; i < data.length; i++)
         sum = sum + data[i];
      double average = (double)sum / data.length;
      return average;
   }
}
