import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.Random;


public class JFrameCompBase extends JComponent{
    JPanel panel;JFrameImage[][]imageList;JFrameImage[]holdList;JFrameImage[]nextList;
    int sidething=0;int das=10;int arr=2;
    JFrameImage img;
    long timeStart,timeSinceStart;
    long framecounter;
    Graphics2D g2;int[]keyHoldFrames=new int[100];
    BufferedImage image,image2,imageOverlay;
    BufferedImage bufferImage,textLayer;
    Graphics2D bufferG,bufferGText;
    int width;Board board;
    int currentFPS;
    boolean imageOver;
    JFramePolygon testpoly;
    JFrameText text;
    int height;int h2;
    Random rand=new Random();
    JFrameImage[]elementList=new JFrameImage[2000];
    JFramePolygon[]polyList=new JFramePolygon[2000];
    JFrameParticleImage testEmitter;
    JFrameParticlePolygon testEmitter2;
    public JFrameCompBase(JPanel panel2,int w,int h){
        width=w;height=h;
        bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        textLayer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        bufferG = (Graphics2D) bufferImage.createGraphics();
        bufferGText = (Graphics2D) textLayer.createGraphics();
        h2=bufferImage.getHeight();
        panel=panel2;board=new Board(10,20,width,height);
        timeStart=System.nanoTime();
        g2=(Graphics2D)panel.getGraphics();
        try {
            image = ImageIO.read(new File("apple.jpg"));
            image2 = ImageIO.read(new File("apple2.jpg"));
            imageOver=false;
            if(imageOver){imageOverlay = ImageIO.read(new File("vignette.png"));}
        } catch (Exception e) {}
        
        for(int i=0;i<elementList.length;i++){
            //if(rand.nextInt(2)==0){
            //elementList[i]=new JFrameImage(rand.nextInt(3000)-750,rand.nextInt(2000)-500,1+(int)Math.pow((double)i,2)/80000,1+(int)Math.pow((double)i,2)/80000,0,image); 
            int thingx=rand.nextInt(width+100);int thingy=rand.nextInt(height);
            polyList[i]=new JFramePolygon(new int[]{thingx,thingx+rand.nextInt(100),thingx+rand.nextInt(100)},new int[]{thingy,thingy+rand.nextInt(50)+25,thingy+rand.nextInt(50)+25},new Color(0,0,0),new Color(rand.nextInt(150),rand.nextInt(150),rand.nextInt(150)),(float)3); 
        }
        text=new JFrameText("FPS: 0");

        for(int i=0;i<elementList.length;i++){
            if(elementList[i]!=null){panel.add(elementList[i]);}}
        board.populateList();
        board.populateHold();
        panel.add(text.getObject());
        text.getObject().setVisible(false);
        
        //text.getObject().paint(bufferG);
        //text.getObject().setVisible(true);
        
    }


   @Override
   public void paintComponent(Graphics g)
   {
    bufferG.clearRect(0,0,width, height);
    Graphics g3 = g; 
        g3.setColor(Color.black);
        g3.fillRect(0,0,width,height);  
        bufferG.clearRect(0,0,width, height);
        render((Graphics2D)g);   
        g3.drawImage(bufferImage, 0, 0, null);
        

    bufferGText.clearRect(0,0,width, height);

    //text.draw(bufferGText);
    text.getObject().paint(bufferGText);

    g3.drawImage(textLayer,10,10,null);
    g3.dispose();
    timeSinceStart=(System.nanoTime()-timeStart)/1000;
    if(timeSinceStart>1000000){timeStart+=1000000000;text.setText("FPS: "+framecounter);framecounter=0;}
    framecounter+=1;
   }

   public void render(Graphics2D gb){
    //bufferG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    if(imageList!=null){for(int x=0;x<imageList.length;x++){
    for(int y=0;y<imageList[0].length;y++){imageList[x][y].draw(bufferG);}}}
    if(holdList!=null){for(int y=0;y<holdList.length;y++){holdList[y].draw(bufferG);}}
    if(nextList!=null){for(int y=0;y<nextList.length;y++){nextList[y].draw(bufferG);}}

    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){
            elementList[i].draw(bufferG);
            //polyList[i].draw(bufferG);
        }
        
    }
    if(imageOver){bufferG.drawImage(imageOverlay,0,0,width,height,null);}
    
    
   }



   public void nextFrame(int mouseX,int mouseY,boolean mouseDown,boolean[]pressedKeys){
    //text.setText("bigger");
    for(int i=0;i<keyHoldFrames.length;i++){if(pressedKeys[i]){keyHoldFrames[i]+=1;}else{keyHoldFrames[i]=0;}}
    //up 38, down 40, left 37, right 39, z 90, x 88, c 67, space 32
    //int x;int y;
    //testpoly.setPoint(2,mouseX,mouseY);
    //x,y,rotation
    int[]moves={0,0,0};
    if(keyHoldFrames[65]==1||(keyHoldFrames[65]>das&&(keyHoldFrames[65]%arr)==0)){moves[2]=3;}
    if(keyHoldFrames[83]==1||(keyHoldFrames[83]>das&&(keyHoldFrames[83]%arr)==0)){moves[2]=2;}
    if(keyHoldFrames[68]==1||(keyHoldFrames[68]>das&&(keyHoldFrames[68]%arr)==0)){moves[2]=1;}
    
    if(keyHoldFrames[32]==1){moves[1]=24;}
    if(keyHoldFrames[37]==1||(keyHoldFrames[37]>das&&(keyHoldFrames[37]%arr)==0)){moves[0]-=1;}
    if(keyHoldFrames[39]==1||(keyHoldFrames[39]>das&&(keyHoldFrames[39]%arr)==0)){moves[0]+=1;}
    if(keyHoldFrames[40]>0&&(keyHoldFrames[40]-1)%arr==0){moves[1]=1;}
    
    
    
    board.nextFrame(moves);
    imageList=board.updateList();
    holdList=board.updateHold();
    /*
    for(int i=0;i<elementList.length;i++){
        if(elementList[i]!=null){
            //x=elementList[i].getXPos();y=elementList[i].getYPos();
            polyList[i].movePos(rand.nextInt(5),0);
            if(polyList[i].getX()>width){polyList[i].setPos(-100,polyList[i].getY());}
            elementList[i].setPos((int)(elementList[i].ogX+(mouseX-(width/2))/(1+(float)elementList[i].getXScale()/20)),(int)(elementList[i].ogY+(mouseY-(height/2))/(1+(float)elementList[i].getYScale()/20)));
        }
        }*/
   }

}