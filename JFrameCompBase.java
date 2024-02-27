import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

import java.io.File;
import java.nio.Buffer;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.util.Random;


public class JFrameCompBase extends JComponent{
    JPanel panel;
    int sidething=0;
    JFrameImage img;
    Graphics2D g2;
    BufferedImage image;
    BufferedImage image2;
    BufferedImage bufferImage;
    Graphics2D bufferG;
    int width;
    JFrameImgQuad testpoly;
    int height;int h2;
    Random rand=new Random();
    JFrameImage[]elementList=new JFrameImage[2000];
    public JFrameCompBase(JPanel panel2,int w,int h){
        width=w;height=h;
        bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferG = (Graphics2D) bufferImage.getGraphics();
        //bufferG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        h2=bufferImage.getHeight();

        panel = panel2;
        g2=(Graphics2D)panel.getGraphics();
        //System.out.println(""""hi"""");
        try {
            image = ImageIO.read(new File("apple.jpg"));
            image2 = ImageIO.read(new File("banana.png"));
        } catch (Exception e) {}
        
        for(int i=0;i<elementList.length;i++){
            //if(rand.nextInt(2)==0){
            elementList[i]=new JFrameImage(rand.nextInt(3000)-750,rand.nextInt(2000)-500,1+(int)Math.pow((double)i,2)/80000,1+(int)Math.pow((double)i,2)/80000,0,image); 
            //}else{elementList[i]=new JFrameImage(rand.nextInt(1500),rand.nextInt(1000),rand.nextInt(100)+1,rand.nextInt(100)+1,rand.nextInt(200),image2); }
            //elementList[i]=new JFrameImage(500, 500, 100, 100, 10, image); 
        }
        //testpoly=new JFramePolygon(new int[]{20,200,400},new int[]{30,500,100},new Color(105,0,150),new Color(10,10,50),(float)10);
        testpoly=new JFrameImgQuad(new int[]{20,200,400,300},new int[]{30,500,100,100},new Color(105,0,150),(float)10,image);


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
    
   }

   public void render(Graphics2D gb){
    //bufferG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){
            elementList[i].draw(bufferG);}
        }
    testpoly.draw(bufferG);
    
   }



   public void nextFrame(int mouseX,int mouseY,boolean mouseDown){
    int x;int y;//System.out.println(mouseX+" "+mouseY);
    testpoly.setPoint(2,mouseX,mouseY);
    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){
            x=elementList[i].getXPos();y=elementList[i].getYPos();
            elementList[i].setPos((int)(elementList[i].ogX+(mouseX-(width/2))/(1+(float)elementList[i].getXScale()/20)),(int)(elementList[i].ogY+(mouseY-(height/2))/(1+(float)elementList[i].getYScale()/20)));
            //elementList[i].changePos(rand.nextInt(3)-1,rand.nextInt(3)-1);
            //if(mouseDown){elementList[i].changePos(mouseX>x?1:-1,mouseY>y?1:-1);}
            //if(elementList[i].getYPos()>h2){elementList[i].setPos(-1,0);}
            //elementList[i].changeScale(rand.nextInt(3)-1,rand.nextInt(3)-1);
        
        }
        }
   }

}