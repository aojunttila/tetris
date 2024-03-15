import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Board{
    int w;int h;int w2;int h2;BufferedImage[]images=new BufferedImage[7];
    int blockSize=18;int blockSpacing=20;
    int[][]mainList;int[][]piecePoints;
    Piece currentPiece;
    JFrameImage[][]imageList;
    public Board(int width,int height,int width2,int height2){
        try{
            images[0]=ImageIO.read(new File("apple.jpg"));
            images[1]=ImageIO.read(new File("glow.png"));
        }catch(Exception e){}
        w=width;h=height;w2=width2;h2=height2;
        mainList=new int[w][];imageList=new JFrameImage[w][];
        for(int i=0;i<mainList.length;i++){mainList[i]=new int[h+4];imageList[i]=new JFrameImage[h+4];}
        currentPiece=new Piece(0,0,5,5);
    }

    public void nextFrame(int[]moveAmounts){
        //clear last position
        if(piecePoints!=null){for(int i=0;i<piecePoints.length;i++){mainList[piecePoints[i][0]][piecePoints[i][1]]=0;}}


        rotateWithCheck(moveAmounts[2]);

        moveWithCheck(moveAmounts[0],moveAmounts[1]);
        piecePoints=currentPiece.getAdjustedArray();
        for(int i=0;i<piecePoints.length;i++){
            mainList[piecePoints[i][0]][piecePoints[i][1]]=1;
        }
        //System.out.println("hi");
    }

    public void moveWithCheck(int x,int y){
        int[][]tempArray=currentPiece.getMovedArray(x,y);
        if(testValidPosition(tempArray)){currentPiece.move(x,y);}
    }

    public void rotateWithCheck(int rotate){
        int[][]tempArray=currentPiece.getMovedArray2(0,0,currentPiece.getRotatedArray(rotate));
        System.out.println(tempArray[0][1]);
        if(testValidPosition(tempArray)){currentPiece.rotate(rotate);}
    }

    public boolean pointsInGround(int[][]tempArray){
        boolean tempBoolean=false;
        for(int i=0;i<tempArray.length;i++){
            if(tempArray[i][1]>24){tempBoolean=true;}
        }
        return tempBoolean;
    }

    public boolean testValidPosition(int[][]tempArray){
        boolean temp=true;
        for(int i=0;i<tempArray.length;i++){
            if(tempArray[i][0]>=w||tempArray[i][0]<0){temp=false;}
            if(tempArray[i][1]>=h||tempArray[i][1]<0){temp=false;}
        }
        return temp;
    }


    public JFrameImage[][]updateList(){
        for(int x=0;x<imageList.length;x++){
            for(int y=0;y<imageList[0].length;y++){
                imageList[x][y].image=images[mainList[x][y]];
            }
        }
        return imageList;
    }

    public JFrameImage[][]populateList(){
        int boardStartX=(w2/2)-((w/2)*blockSpacing);int boardStartY=(h2/2)-(((h+8)/2)*blockSpacing);
        for(int x=0;x<imageList.length;x++){
            for(int y=0;y<imageList[0].length;y++){
                imageList[x][y]=new JFrameImage(boardStartX+blockSpacing*x,boardStartY+blockSpacing*y,blockSize,blockSize,0,images[mainList[x][y]]);
            }
        }
        return imageList;
    }
}
