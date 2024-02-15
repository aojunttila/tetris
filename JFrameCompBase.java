import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
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
    JPanel panel;
    public JFrameCompBase(JPanel panel2){
        panel = panel2;
        System.out.println("hi");
        JFrameImage img = new JFrameImage(1, 100, 10, 10, 0);
        panel.add(img);
        
    }


   @Override
   public void paintComponent(Graphics g)
   {  
    Graphics2D g2 = (Graphics2D) g;  // cast to Graphics2D object
    JFrameImage img = new JFrameImage(1, 100, 10, 10, 0);
    //img.draw(g2);
    //panel.add(img);
    //JFrameImage = new JFrameImage(10,10, 100, 100, 100)
    
   }

}