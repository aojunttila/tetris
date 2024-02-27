import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;

public class JFrameImgQuad extends JComponent
{
  Polygon poly;
  //int[]arrayX,arrayY;
  Color linecolor,fillcolor;
  int[]arrayX,arrayY;
  float linewidth;
  BufferedImage OGimage;
  BufferedImage FNimage;
  int xScale;
  int yScale;
  int rotation;
  //AffineTransform at;


/*
    for(i=0; i<r.width; i++)
    for(j=0; j<r.height; j++)
      if(face1.contains(r.x+i, r.y+j)) {
        int ix=r.x+i, jy=r.y+j;
        if (ix>0&&ix<framewidth&&jy>0&&jy<frameheight) {
        int a1=areaTriangle(ix, jy, face1.xpoints[0], face1.ypoints[0], face1.xpoints[1], face1.ypoints[1]);
        int a2=areaTriangle(ix, jy, face1.xpoints[0], face1.ypoints[0], face1.xpoints[2], face1.ypoints[2]);
        int a3=areaTriangle(ix, jy, face1.xpoints[1], face1.ypoints[1], face1.xpoints[2], face1.ypoints[2]);
        int[] ch=new int[3];
    //      for(l=0; l<3; l++) c[l]=(int)((1-1.0*a1/a)*c1[l]+(1-1.0*a2/a)*c2[l]+(1-1.0*a3/a)*c3[l]);
        for(l=0; l<3; l++) ch[l]=(int)((1.0*a1/a)*c3[l]+(1.0*a2/a)*c2[l]+(1.0*a3/a)*c1[l]);
        b.setRGB(ix, jy, 0xff000000|(ch[0]<<16)|(ch[1]<<8)|ch[2]);
 */

/*does not work
 */
  public JFrameImgQuad(int[]Xarray,int[]Yarray,Color Lcolor,float Lwidth,BufferedImage image){
    OGimage=image;
    arrayX=Xarray;arrayY=Yarray;
    poly=new Polygon(arrayX,arrayY,arrayX.length);
    FNimage=new BufferedImage((int)(poly.getBounds2D().getMaxX()-poly.getBounds2D().getMinX()),(int)(poly.getBounds2D().getMaxY()-poly.getBounds2D().getMinY()),BufferedImage.TYPE_INT_ARGB);
    linecolor=Lcolor;linewidth=Lwidth;
  }

  public void draw(Graphics2D g2){
    calculateImage();
    g2.drawImage(FNimage,(int)poly.getBounds2D().getMinX(),(int)poly.getBounds2D().getMinY(),null);
    g2.setColor(linecolor);
    g2.setStroke(new BasicStroke(linewidth));
    g2.drawPolygon(poly);
    g2.setColor(linecolor);
    g2.fillPolygon(poly);}

  public void calculateImage(){
    FNimage=new BufferedImage((int)(poly.getBounds2D().getMaxX()-poly.getBounds2D().getMinX()),(int)(poly.getBounds2D().getMaxY()-poly.getBounds2D().getMinY()),BufferedImage.TYPE_INT_ARGB);
    for(int x=0;x<FNimage.getWidth();x++){
      for(int y=0;y<FNimage.getHeight();y++){
        FNimage.setRGB(x,y,Color.RED.getRGB());
      }
    }
  }

  public void calculateImageDim(){
    FNimage=new BufferedImage((int)(poly.getBounds2D().getMaxX()-poly.getBounds2D().getMinX()),(int)(poly.getBounds2D().getMaxY()-poly.getBounds2D().getMinY()),BufferedImage.TYPE_INT_ARGB);
    calculateImage();
  }


  public void movePos(int x,int y){
    poly.translate(x,y);}
  public void setPos(int newX,int newY){
    int oldX=(int)poly.getBounds2D().getMinX();
    int oldY=(int)poly.getBounds2D().getMinY();
    poly.translate(newX-oldX,newY-oldY);}
  public void setPoint(int index,int x,int y){
    arrayX[index]=x;
    arrayY[index]=y;
    poly=new Polygon(arrayX,arrayY,arrayX.length);
    //calculateImageDim();
  }
}