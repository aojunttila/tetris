import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JComponent;

public class JFrameParticleImage extends JComponent
{
  Random rand=new Random();
  int xpos,ypos;
  int ogX,ogY;  
  int Xsca,Ysca;
  int Xvar,Yvar;
  int speD,angL,angV,speV;
  int lifetime,drag;
  BufferedImage image;
  int maxParticles;
  float framecount;
  float emissionRate;
  JFrameImage[]particleList;
  float[][]attributeList;
  Graphics2D g1;
  boolean fadeOut;
  //AffineTransform at;
  /**
   * Args documentation:
   * X position, Y position, X variance, Y variance, Angle, angle variance, speed, speed variance, X size, Y size, drag, lifetime, maxparticles
   * 
   * 
   * 
   */

  public JFrameParticleImage(int[]args,boolean fadeout,BufferedImage image2)
  {
    xpos=args[0];ypos=args[1];
    Xvar=args[2];Yvar=args[3];
    angL=args[4];angV=args[5];
    speD=args[6];speV=args[7];
    Xsca=args[8];Ysca=args[9];
    drag=args[10];fadeOut=fadeout;
    lifetime=args[11];maxParticles=args[12];
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
        particleList[i]=new JFrameImage(xpos+(Xvar>0?rand.nextInt(Xvar):0),ypos+(Yvar>0?rand.nextInt(Yvar):0),Xsca,Ysca,0,image);
        double angVar2=rand.nextDouble()*(double)angV-(double)angV/2;
        attributeList[i]=new float[]{(float)((speD+rand.nextDouble()*speV-speV/2)*Math.sin(Math.toRadians(angL+angVar2))),(float)((speD+rand.nextDouble()*speV-speV/2)*Math.cos(Math.toRadians(angL+angVar2))),0};
        //attributeList[i]=new float[]{angL,speD+rand.nextFloat()*speV-speV/2,0};
      }
      if(particleList[i]!=null){
        attributeList[i][2]+=1;
        if(fadeOut){particleList[i].setAlpha(1-attributeList[i][2]/lifetime);}
        attributeList[i][0]*=((float)drag/100);attributeList[i][1]*=((float)drag/100);
        particleList[i].changePos(attributeList[i][0],attributeList[i][1]);
        //particleList[i].changePos();
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