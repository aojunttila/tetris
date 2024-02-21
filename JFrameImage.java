import java.awt.Graphics2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Graphics;

public class JFrameImage extends JComponent
{
  private int xpos;
  private int ypos;
  private int xScale;
  private int yScale;
  private int rotation;
  Ellipse2D.Double ring2;
  BufferedImage image;
  String filepath;
  Graphics2D g1;
  int tttttt=0;

  public JFrameImage(int posx,int posy,int xScale,int yScale,int rotation, BufferedImage image2)
  {
    this.xpos = posx;
    this.ypos = posy;
    image=image2;

  }

  public void draw(Graphics2D g2){
    g1 = g2;
    //System.out.println("hi2"+tttttt);
    g2.drawImage(image,10,10,null);
    tttttt++;

    Ellipse2D.Double ring3 = new Ellipse2D.Double(0,0,100,100);
    g2.setColor(Color.RED);
    g2.fill(ring3);
  }

  public void nextFrame(int sidething, Graphics2D g2){
    //ring2 = new Ellipse2D.Double(sidething, 75, 250, 250);
    //g1.setColor(Color.BLACK);
    //g1.fill(ring2);
    //draw(g2);
    //ring2.x = tttttt;
    
    
  }
}