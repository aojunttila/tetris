//package Utils;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JFrameBase extends JFrame {
  Random random=new Random();int w=500;int h=300;int s1=1;double s=s1;
  BufferedImage i;
  //Canvas canvas=new Canvas(w,h);
    public JFrameBase(){
      i=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
      JFrame frame=new JFrame();
      JPanel panel=new JPanel();
      JFrameCompBase comp=new JFrameCompBase(panel);
      frame.setSize(w,h);
      //frame.add(comp);
      //p.getGraphics().drawLine(10,10, 100, 200);
      frame.setVisible(true);
      //setContentPane(p);setSize(w,h);setVisible(true);
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setLayout(null);
      panel.setVisible(true);
      panel.setBackground(Color.RED);
      panel.setLayout(new OverlayLayout(panel));
      panel.add(comp);
      comp.paintComponent((Graphics2D)frame.getGraphics());
      frame.add(panel);
      frame.setFocusable(true);
      frame.requestFocusInWindow();

      fullDraw();
      ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
      Runnable task=()->{repaint();};
      executor.scheduleAtFixedRate(task,0,30,TimeUnit.MILLISECONDS);
    }
      private void fullDraw(){
        Color c;byte v;
        //for(int y=0;y<h;y++){for(int x=0;x<w;x++){
          //v=canvas.getValue(x,y);
          //if(d[v].length==3){c=new Color(d[v][0],d[v][1],d[v][2]);}
          //else{c=new Color(d[v][0]+random.nextInt(d[v][3]),d[v][1]+random.nextInt(d[v][4]),d[v][2]+random.nextInt(d[v][5]));}
          //i.setRGB(x,y,c.getRGB());
      }

    public static void main(String[]args){
      SwingUtilities.invokeLater(()->new JFrameBase());}
}