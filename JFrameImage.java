import java.awt.Graphics2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;

public class JFrameImage extends JComponent
{
  private int xpos;
  private int ypos;
  private int xScale;
  private int yScale;
  private int rotation;
  Ellipse2D.Double ring2;
  String filepath;
  Graphics2D g1;
  

  public JFrameImage(int posx,int posy,int xScale,int yScale,int rotation)
  {
    this.xpos = posx;
    this.ypos = posy;
  }

  public void draw(Graphics2D g2){
    g1 = g2;
    System.out.println("hi");
    Ellipse2D.Double ring3 = new Ellipse2D.Double(0, 0, 400, 400);
    Ellipse2D.Double white3 = new Ellipse2D.Double(37.5, 37.5, 325, 325);
    ring2 = new Ellipse2D.Double(75, 75, 250, 250);
    Ellipse2D.Double white2 = new Ellipse2D.Double(112.5, 112.5, 175, 175);
    Ellipse2D.Double ring1 = new Ellipse2D.Double(150, 150, 100, 100);

    g2.setColor(Color.RED);
    g2.fill(ring3);
    g2.setColor(Color.WHITE);
    g2.fill(white3);
    g2.setColor(Color.BLACK);
    g2.fill(ring2);
    g2.setColor(Color.WHITE);
    g2.fill(white2);
    g2.setColor(Color.BLACK);
    g2.fill(ring1);
  }

  public void nextFrame(int sidething){
    ring2 = new Ellipse2D.Double(sidething, 75, 250, 250);
    g1.setColor(Color.BLACK);
    g1.fill(ring2);
    draw(g1);
    
  }
}