import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;

public class JFramePolygon extends JComponent
{
  Polygon poly;
  //int[]arrayX,arrayY;
  Color linecolor,fillcolor;
  int[]arrayX,arrayY;
  float linewidth;
  int xScale;
  int yScale;
  int rotation;
  //AffineTransform at;

/*
 * 
 */
  public JFramePolygon(int[]Xarray,int[]Yarray,Color Lcolor,Color Fcolor,float Lwidth)
  {
    arrayX=Xarray;arrayY=Yarray;
    poly=new Polygon(arrayX,arrayY,arrayX.length);
    linecolor=Lcolor;fillcolor=Fcolor;linewidth=Lwidth;
  }

  public void draw(Graphics2D g2){
    g2.setColor(linecolor);
    g2.setStroke(new BasicStroke(linewidth));
    g2.drawPolygon(poly);
    g2.setColor(fillcolor);
    g2.fillPolygon(poly);
  }

  public void nextFrame(int sidething, Graphics2D g2){
    //ring2 = new Ellipse2D.Double(sidething, 75, 250, 250);
    //g1.setColor(Color.BLACK);
    //g1.fill(ring2);
    //draw(g2);
    //ring2.x = tttttt;
  }

  public void movePos(int x,int y){
    poly.translate(x,y);
  }
  public void setPos(int newX,int newY){
    int oldX=(int)poly.getBounds2D().getMinX();
    int oldY=(int)poly.getBounds2D().getMinY();
    poly.translate(newX-oldX,newY-oldY);
  }
  public void setPoint(int index,int x,int y){
    arrayX[index]=x;
    arrayY[index]=y;
    poly=new Polygon(arrayX,arrayY,arrayX.length);
  }
  //public void setPos(int x,int y){xpos=x;ypos=y;}
  //public void setScale(int xS,int yS){xScale=xS>0?xS:xScale;yScale=yS>0?yS:yScale;}
  //public void changePos(int x,int y){xpos+=x;ypos+=y;}
  //public void changeScale(int xS,int yS){xScale=xScale+xS>1?xScale+xS:1;yScale=yScale+yS>1?yScale+yS:1;}
  //public int getXPos(){return xpos;}
  //public int getYPos(){return ypos;}
  //public int getXScale(){return xScale;}
  //public int getYScale(){return yScale;}
}