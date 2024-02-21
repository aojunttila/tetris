import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class JFrameCompBase extends JComponent{
    JPanel panel;
    int sidething=0;
    JFrameImage img;
    Graphics2D g2;
    BufferedImage image;
    public JFrameCompBase(JPanel panel2){
        panel = panel2;
        //System.out.println("hi");
        try {
            image = ImageIO.read(new File("apple.jpg"));
        } catch (Exception e) {}
        
        img = new JFrameImage(1, 100, 10, 10, 0, image);
        panel.add(img);
    }


   @Override
   public void paintComponent(Graphics g)
   {
    g2 = (Graphics2D) g;  // cast to Graphics2D object
    
    //img.paintImmediately(getVisibleRect());
    img.draw(g2);

    //panel.add(img);
    //JFrameImage = new JFrameImage(10,10, 100, 100, 100)
    
   }

   public void nextFrame(){
    //System.out.println(sidething);
    sidething+=1;
    img.nextFrame(sidething,g2);
    //img.draw(g2);
    //img.repaint();
   }

}