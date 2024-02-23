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
import java.util.Random;


public class JFrameCompBase extends JComponent{
    JPanel panel;
    int sidething=0;
    JFrameImage img;
    Graphics2D g2;
    BufferedImage image;
    BufferedImage bufferImage;
    Graphics2D bufferG;
    int width;
    int height;
    JFrameImage[]elementList=new JFrameImage[100];
    public JFrameCompBase(JPanel panel2,int w,int h){
        Random rand=new Random();
        width=w;height=w;
        bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferG = (Graphics2D) bufferImage.getGraphics();

        panel = panel2;
        g2=(Graphics2D)panel.getGraphics();
        //System.out.println(""""hi"""");
        try {
            image = ImageIO.read(new File("apple.jpg"));
        } catch (Exception e) {}
        
        for(int i=0;i<elementList.length;i++){
            elementList[i]=new JFrameImage(rand.nextInt(1500), rand.nextInt(1000), (float)rand.nextInt(100)/1000, (float)rand.nextInt(100)/1000, rand.nextInt(200), image); 
            //elementList[i]=new JFrameImage(500, 500, 100, 100, 10, image); 
        }

        for(int i=0;i<elementList.length;i++){
            if(elementList[i]!=null){panel.add(elementList[i]);}}
        
    }


   @Override
   public void paintComponent(Graphics g)
   {
    bufferG.clearRect(0,0,width, height);
    render((Graphics2D)g);
    
    Graphics g3 = g;          
    
    g3.setColor(Color.black);
    g3.fillRect(0, 0, 100, 100);              
    g3.drawImage(bufferImage, 0, 0, null);
    g3.dispose(); 
    


    //g2=(Graphics2D)g;
    
    //img.paintImmediately(getVisibleRect());
    

    //panel.add(img);
    //JFrameImage = new JFrameImage(10,10, 100, 100, 100)
    
   }

   public void render(Graphics2D gb){
    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){elementList[i].draw(bufferG);}}
   }



   public void nextFrame(){
    //System.out.println(sidething);
    sidething+=1;
    //img.nextFrame(sidething,g2);
    //img.draw(g2);
    //img.repaint();
   }

}