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

public class JFrameCompBase extends JComponent{
    public JFrameCompBase(){
        System.out.println("hi");
    }


   @Override
   public void paintComponent(Graphics g)
   {  
    Graphics2D g2 = (Graphics2D) g;  // cast to Graphics2D object
    JFrameImage img = new JFrameImage(1, 100, 10, 10, 0);
    img.draw(g2);
    //JFrameImage = new JFrameImage(10,10, 100, 100, 100)
    
   }

}