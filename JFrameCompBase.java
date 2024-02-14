import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;

import javax.swing.JComponent;

public class JFrameCompBase extends JComponent
{  
   @Override
   public void paintComponent(Graphics g)
   {  
    Graphics2D g2 = (Graphics2D) g;  // cast to Graphics2D object
    draw(g2);
    
   }

   public void draw(Graphics2D g2){
    g2.drawLine(0,0, 200, 150);
    Ellipse2D.Double ring3 = new Ellipse2D.Double(0, 0, 400, 400);
    Ellipse2D.Double white3 = new Ellipse2D.Double(37.5, 37.5, 325, 325);
    Ellipse2D.Double ring2 = new Ellipse2D.Double(75, 75, 250, 250);
    Ellipse2D.Double white2 = new Ellipse2D.Double(112.5, 112.5, 175, 175);
    Ellipse2D.Double ring1 = new Ellipse2D.Double(150, 150, 100, 100);
  
    g2.setColor(Color.BLACK);
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
}