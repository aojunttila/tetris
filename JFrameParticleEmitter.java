import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Graphics;

public class JFrameParticleEmitter extends JComponent
{
  Random rand=new Random();
  int xpos,ypos;
  int ogX,ogY;  
  int Xsca,Ysca;
  int Xvar,Yvar;
  int Xvel,Yvel,XveV,YveV;
  int lifetime,drag;
  BufferedImage image;
  int maxParticles;
  float framecount;
  float emissionRate;
  JFrameImage[]particleList;
  float[][]attributeList;
  Graphics2D g1;
  //AffineTransform at;
  /**
   * Args documentation:
   * X position, Y position, X variance, Y variance, X velocity, Xvel variance, Y velocity, Yvel variance, X size, Y size, drag
   * 
   * 
   * 
   */

  public JFrameParticleEmitter(int[]args,BufferedImage image2)
  {
    xpos=args[0];ypos=args[1];
    Xvar=args[2];Yvar=args[3];
    Xvel=args[4];XveV=args[5];
    Yvel=args[6];YveV=args[7];
    Xsca=args[8];Ysca=args[9];
    drag=args[10];
    lifetime=50;
    maxParticles=200;
    emissionRate=(float)lifetime/(float)maxParticles;
    particleList=new JFrameImage[maxParticles];
    //attributeList: velX,velY,timeAlive
    attributeList=new float[maxParticles][3];
    image=image2;
  }

  public void draw(Graphics2D g2){
    nextFrame(g2);
    //at.scale(xScale,yScale); 
    //at.translate(xpos*(1/xScale)+tttttt,ypos*(1/yScale));
    
    //System.out.println(""hi2""+tttttt);
    //g2.setTransform(at);

    //for(int i=0;i<maxParticles;i++){
      //if(particleList[i]!=null){
        
        //g2.drawImage(image,particleList[i].getXPos(),particleList[i].getYPos(),xScale>0?xScale:image.getWidth(),yScale>0?yScale:image.getHeight(),null);
      //}
  
    //g2.drawImage(image,(int)framecount*3,100,xScale>0?xScale:image.getWidth(),yScale>0?yScale:i][][mage.getHeight(),null);
    //Toolkit.getDefaultToolkit().sync();
}

  public void nextFrame(Graphics2D g2){
    framecount+=1;
    for(int i=0;i<maxParticles;i++){
      if((attributeList[i][2]>lifetime||particleList[i]==null)&&framecount>emissionRate){
        framecount-=emissionRate;
        particleList[i]=new JFrameImage(xpos+rand.nextInt(Xvar),ypos+rand.nextInt(Yvar),Xsca,Ysca,0,image);
        attributeList[i]=new float[]{Xvel+rand.nextInt(XveV)-(XveV/2),Yvel+rand.nextInt(YveV)-(YveV/2),0};
      }
      if(particleList[i]!=null){
        attributeList[i][2]+=1;
        attributeList[i][0]*=((float)drag/100);attributeList[i][1]*=((float)drag/100);
        particleList[i].changePos((int)attributeList[i][0],(int)attributeList[i][1]);
        particleList[i].draw(g2);
      }
    }
  }

  /*
  public void setPos(int x,int y){xpos=x;ypos=y;}
  public void setScale(int xS,int yS){s=xS>0?xS:xScale;yScale=yS>0?yS:yScale;}
  public void changePos(int x,int y){xpos+=x;ypos+=y;}
  public void changeScale(int xS,int yS){xScale=xScale+xS>1?xScale+xS:1;yScale=yScale+yS>1?yScale+yS:1;}
  public int getXPos(){return xpos;}
  public int getYPos(){return ypos;}
  public int getXScale(){return xScale;}
  public int getYScale(){return yScale;}*/
}