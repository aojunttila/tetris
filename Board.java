import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class Board{
    Random rand=new Random();
    int w;int h;int w2;int h2;BufferedImage[]images=new BufferedImage[9];
    int blockSize=20;int blockSpacing=20;
    int[][]mainList;int[][]piecePoints;int[][]ghostPiece;
    Piece currentPiece;
    JFrameImage[][]imageList;
    JFrameImage[]holdPiece=new JFrameImage[4];int holdPieceIndex=1;
    JFrameImage[][]nextPieces=new JFrameImage[5][4];int[]nextPiecesIndex=new int[5];
    public Board(int width,int height,int width2,int height2){
        try{
            images[0]=ImageIO.read(new File("tetris1.png"));
            images[1]=ImageIO.read(new File("tetris2.png"));
            images[8]=ImageIO.read(new File("glow.png"));
        }catch(Exception e){}
        w=width;h=height;w2=width2;h2=height2;
        mainList=new int[w][];imageList=new JFrameImage[w][];
        for(int i=0;i<mainList.length;i++){mainList[i]=new int[h+4];imageList[i]=new JFrameImage[h+4];}
        currentPiece=new Piece(2,0,3,2);
    }

    public void nextFrame(int[]moveAmounts){
        //clear last position
        eraseIndex(8);
        if(piecePoints!=null){for(int i=0;i<piecePoints.length;i++){mainList[piecePoints[i][0]][piecePoints[i][1]]=0;}}


        rotateWithKick(moveAmounts[2]);

        moveWithCheck(moveAmounts[0],moveAmounts[1]);
        drawPiece();
        clearLines(detectFilledLines());
        calculateGhost();
        drawGhost();
        //System.out.println("hi");
    }

    public boolean[]detectFilledLines(){
        boolean[]tempArray=new boolean[h];
        for(int i=0;i<h;i++){
            if(isRowFilled(i+4)){tempArray[i]=true;}
        }
        
        return tempArray;
    }

    public void drawPiece(){
        piecePoints=currentPiece.getAdjustedArray();
        for(int i=0;i<piecePoints.length;i++){
            mainList[piecePoints[i][0]][piecePoints[i][1]]=1;
        }
    }

    public boolean calculateGhost(){
        ghostPiece=currentPiece.getAdjustedArray();
        for(int j=0;j<h+4;j++){
            if(!testValidPosition(ghostPiece)){
                for(int i=0;i<ghostPiece.length;i++){
                    ghostPiece[i][1]-=1;
                }
                return true;}
            for(int i=0;i<ghostPiece.length;i++){
                ghostPiece[i][1]+=1;
            }
        }
        return false;
    }

    public void drawGhost(){
        for(int i=0;i<ghostPiece.length;i++){
            if(mainList[ghostPiece[i][0]][ghostPiece[i][1]]==0){
                mainList[ghostPiece[i][0]][ghostPiece[i][1]]=8;
            }
        }
    }

    public void eraseIndex(int index){
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                if(mainList[x][y]==index){mainList[x][y]=0;}
            }
        }
    }


    public boolean isRowFilled(int row){
        boolean temp=true;
        for(int i=0;i<w;i++){
            if(listWithoutCurrent()[i][row]==0){temp=false;}
        }
        return temp;
    }


    public void clearLine(int row){
        for(int x=0;x<w;x++){
            for(int y=0;y<row+4;y++){
                //System.out.println(((h-row+2)-y)+row);
                mainList[x][row-y+4]=listWithoutCurrent()[x][row-y-1+4];
            }
        }
        drawPiece();
    }

    public void clearLines(boolean[]filledLines){
        for(int i=0;i<filledLines.length;i++){
            if(filledLines[i]){clearLine(i);}
        }
    }

    public void lockIn(){
        int[][]modifyArray=currentPiece.getAdjustedArray();
        for(int i=0;i<modifyArray.length;i++){
            mainList[modifyArray[i][0]][modifyArray[i][1]]=1;
        }
        currentPiece=new Piece(rand.nextInt(7),0,3,2);
    }

    public int[][]listWithoutCurrent(){
        int[][]tempArray=currentPiece.getAdjustedArray();
        int[][]tempArray2=new int[w][h+4];
        for(int x=0;x<w;x++){for(int y=0;y<h+4;y++){tempArray2[x][y]=mainList[x][y];}}
        for(int i=0;i<currentPiece.getAdjustedArray().length;i++){
            tempArray2[tempArray[i][0]][tempArray[i][1]]=0;
        }
        return tempArray2;
    }

    public void moveWithCheck(int x,int y){
        boolean lockIn=false;
        for(int i=0;i<y;i++){
        int[][]tempArray2=currentPiece.getMovedArray(0,1);
        if(testValidPosition(tempArray2)){currentPiece.move(0,1);}else{lockIn=true;}
        }
        int[][]tempArray=currentPiece.getMovedArray(x,0);
        if(testValidPosition(tempArray)){currentPiece.move(x,0);}
        if(lockIn){lockIn();}
    }

    public boolean rotateWithCheck(int rotate){
        int[][]tempArray=currentPiece.getMovedArray2(0,0,currentPiece.getRotatedArray(rotate));
        //System.out.println(tempArray[0][1]);
        if(testValidPosition(tempArray)){currentPiece.rotate(rotate);return true;}return false;

    }

    public boolean rotateWithKick(int rotate){
        int[][]activeKick=currentPiece.getKickArray(rotate);
        if(!rotateWithCheck(rotate)){
            for(int i=0;i<activeKick.length;i++){
                int[][]tempArray=currentPiece.getMovedArray2(0,0,currentPiece.getRotatedArrayKick(currentPiece.rotation,currentPiece.rotation+rotate,i));
                //tempArray=currentPiece.getRotatedArrayKick(currentPiece.rotation,currentPiece.rotation+rotate,i);
                System.out.println(tempArray[0][1]+" "+tempArray[1][1]+" "+tempArray[2][1]+" "+tempArray[3][1]);
                if(testValidPosition(tempArray)){
                    currentPiece.move(activeKick[i][0],-activeKick[i][1]);
                    currentPiece.rotate(rotate);
                    
                    System.out.println("valid kick");
                    return true;
                    //currentPiece.getRotatedArrayKick(currentPiece.rotation,currentPiece.rotation+rotate,0);
                    //currentPiece.rotate(rotate);
                }
            }
        }
        return false;
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
            if(tempArray[i][1]>=h+4||tempArray[i][1]<0){temp=false;}
            if(temp&&mainList[tempArray[i][0]][tempArray[i][1]]!=0&&!coordsInPiece(tempArray[i][0],tempArray[i][1])){temp=false;}
        }
        
        return temp;
    }

    public boolean coordsInPiece(int x,int y){
        boolean temp=false;
        
        int[][]tempArray=currentPiece.getAdjustedArray();
        if(tempArray!=null){
        for(int i=0;i<tempArray.length;i++){
            if(tempArray[i][0]==x&&tempArray[i][1]==y){temp=true;}
        }}
        return temp;
    }


    public JFrameImage[]updateHold(){
        //JFrameImage[]tempArray=new JFrameImage[4];
        int[][]tempArray=Piece.rotations[holdPieceIndex][0];
        for(int i=0;i<holdPiece.length;i++){
            holdPiece[i].setPos((int)(tempArray[i][0]*blockSpacing+imageList[0][0].xpos-100),(int)(tempArray[i][1]*blockSpacing+imageList[0][0].ypos+blockSpacing));
        }
        return holdPiece;
    }

    public JFrameImage[]populateHold(){
        //JFrameImage[]tempArray=new JFrameImage[4];
        for(int y=0;y<holdPiece.length;y++){
            holdPiece[y]=new JFrameImage(rand.nextInt(500), rand.nextInt(500), blockSize, blockSize,0,images[0]);
        }
        return holdPiece;
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
