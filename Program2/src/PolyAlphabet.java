
/**
 Handles the encryption alphabet.

 */
public class PolyAlphabet
{

   private int loc = 0;
    private final int[] polyArray =
   {
      5, 19, 19, 5, 19
   };

   public String encode(String enc)
   {
      StringBuilder encoded = new StringBuilder();
      loc = 0;
      for (char i : enc.toCharArray())
      {
         if (Character.isLetter(i))
         {
            if (Character.isUpperCase(i))
               encoded.append((char) ('A' + (i - 'A' + getOff()) % 26));
            else
               encoded.append((char) ('a' + (i - 'a' + getOff()) % 26));
         }
         else
            encoded.append(i);
      }
      loc = 0;
      return encoded.toString();
   }

   private int getOff()
   {
      int key = polyArray[loc++];
      if (loc == polyArray.length)
      {
         loc = 0;
      }
      return key;
   }
}
