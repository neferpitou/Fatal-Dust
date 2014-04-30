package characters;

import java.awt.*;

public class SpriteAnimated extends Rectangle
{
   Animation[] animation;

   int pos;
   

   public SpriteAnimated(int x, int y, int w, int h, String direction, String fighterName, String[] positions, int[] count )
   {
	  
       super(x, y, 0, 0);
       
       this.x = x;
	   this.y = y;
	   
	   this.w = w;
	   this.h = h;

       animation = new Animation[positions.length];
       
       pos = 0;
      
       for(int i = 0; i < animation.length; i++)
          animation[i] = new Animation(fighterName, positions[i], direction , count[i]);
       
       
       

   }
   
   public void setPos( int pos )
   {
	   this.pos = pos; 
   }

   public void draw(Graphics g)
   {
   
	   
	  // img, x, y, w, h, observer
      g.drawImage(animation[pos].currentImage(), x, y, w, h, null);

      super.draw(g);
      

   }



}
