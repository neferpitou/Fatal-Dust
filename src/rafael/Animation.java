package rafael;

import java.awt.*;

public class Animation
{
   Image[] image;

   int current = 1;

   int delay = 0;

   public Animation(String fighterName, String position, String direction, int count)
   {
      image = new Image[count];

      for(int i = 0; i < count; i++)

         image[i] = Toolkit.
         			getDefaultToolkit().
         			getImage("resources/" + fighterName + "/" + position +"_" + direction +"_"+i+".gif");
   }


   public Image currentImage()
   {
      if (delay == 10)
      {
         delay = -1;

         current++;

         if (current >= image.length) current = 1;
      }

      delay++;

      return image[current];
   }

   public Image staticImage()
   {
      return image[0];
   }
}