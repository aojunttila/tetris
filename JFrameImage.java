import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
  //AffineTransform at;

  public JFrameImage(int posx,int posy,int xWidth,int yWidth,int rotation2, BufferedImage image2)
  {
    xpos=posx;ypos=posy;
    xScale=xWidth;yScale=yWidth;
    rotation=rotation2;
    image=image2;

    //at.setToRotation(1.5, xpos*10 + (image.getWidth()*0.1 / 2), ypos*10 + (image.getHeight()*0.1 / 2));
    //at.rotate(1);
    
    
  }

  public void draw(Graphics2D g2){
    g1 = g2;
    //at = new AffineTransform();
  
    //at.scale(xScale,yScale); 
    //at.translate(xpos*(1/xScale)+tttttt,ypos*(1/yScale));
    
    //System.out.println(""hi2""+tttttt);
    //g2.setTransform(at);
    g2.drawImage(image,xpos,ypos,xScale>0?xScale:image.getWidth(),yScale>0?yScale:image.getHeight(),null);
  }

  public void nextFrame(int sidething, Graphics2D g2){
    //ring2 = new Ellipse2D.Double(sidething, 75, 250, 250);
    //g1.setColor(Color.BLACK);
    //g1.fill(ring2);
    //draw(g2);
    //ring2.x = tttttt;
  }

  public void setPos(int x,int y){xpos=x>-1?x:xpos;ypos=y>-1?y:ypos;}
  public void setScale(int xS,int yS){xScale=xS>0?xS:xScale;yScale=yS>0?yS:yScale;}
  public void changePos(int x,int y){xpos+=x;ypos+=y;}
  public void changeScale(int xS,int yS){xScale=xScale+xS>1?xScale+xS:1;yScale=yScale+yS>1?yScale+yS:1;}
  public int getXPos(){return xpos;}
  public int getYPos(){return ypos;}
  public int getXScale(){return xScale;}
  public int getYScale(){return yScale;}
}