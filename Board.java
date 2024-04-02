import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class Board{
    Random rand=new Random();boolean heldSwap=false;boolean tspin=false;
    int w;int h;int w2;int h2;BufferedImage[]images=new BufferedImage[11];
    int blockSize=20;int blockSpacing=20;int pauseFrames=0;int score=0;
    boolean[]bag=new boolean[7];int framesOnGround=0;int pieceCount;boolean lockIn2=false;
    int[][]mainList;int[][]piecePoints;int[][]ghostPiece;long timeStart2;
    Piece currentPiece;int[]nextQueue=new int[5];boolean reset;int B2B;
    JFrameImage[][]imageList;boolean touchingGround=false;int numFilledLines;
    JFrameImage[]holdPiece=new JFrameImage[4];int holdPieceIndex=-1;String scoreType;
    JFrameImage[][]nextPieces=new JFrameImage[5][4];int[]nextPiecesIndex=new int[5];
    int[][]pieceOffset;RescaleOp rescaleOp=new RescaleOp(0.99f,0,null);
    public Board(int width,int height,int width2,int height2){
        String texturePack="basic";try{
            images[0]=ImageIO.read(new File("textures/"+texturePack+"/background.png"));
            images[1]=ImageIO.read(new File("textures/"+texturePack+"/Ipiece.png"));
            images[2]=ImageIO.read(new File("textures/"+texturePack+"/Jpiece.png"));
            images[3]=ImageIO.read(new File("textures/"+texturePack+"/Lpiece.png"));
            images[4]=ImageIO.read(new File("textures/"+texturePack+"/Opiece.png"));
            images[5]=ImageIO.read(new File("textures/"+texturePack+"/Spiece.png"));
            images[6]=ImageIO.read(new File("textures/"+texturePack+"/Tpiece.png"));
            images[7]=ImageIO.read(new File("textures/"+texturePack+"/Zpiece.png"));
            images[8]=ImageIO.read(new File("textures/"+texturePack+"/ghostpiece.png"));
            images[9]=ImageIO.read(new File("textures/"+texturePack+"/topbackground.png"));
            images[10]=ImageIO.read(new File("textures/"+texturePack+"/usedhold.png"));
        }catch(Exception e){}
        pieceOffset=new int[][]{
            {0,-(blockSpacing/2)},{blockSpacing/2,0},
            {blockSpacing/2,0},{0,0},{blockSpacing/2,0},
            {blockSpacing/2,0},{blockSpacing/2,0}};
        //I,J,L,O,S,T,Z
        w=width;h=height;w2=width2;h2=height2;
        mainList=new int[w][];imageList=new JFrameImage[w][];
        for(int i=0;i<mainList.length;i++){mainList[i]=new int[h+4];imageList[i]=new JFrameImage[h+4];}
        populateQueue();
        spawnNewPiece(-1);
        //currentPiece=new Piece(2,0,3,2);
        timeStart2=System.nanoTime();
    }

    public void nextFrame(int[]moveAmounts){
        if(pauseFrames>0){pauseFrames-=1;}else{
            lockIn2=false;
            eraseIndex(8);
            if(piecePoints!=null){for(int i=0;i<piecePoints.length;i++){mainList[piecePoints[i][0]][piecePoints[i][1]]=0;}}

            if(testValidPosition(currentPiece.getMovedArray(0,1))){touchingGround=false;}else{touchingGround=true;framesOnGround+=1;}
            if(currentPiece.lockTimer<framesOnGround&&touchingGround==true){lockIn();}
            if((moveAmounts[0]!=0||moveAmounts[2]!=0)&&currentPiece.lockTimer<80&&touchingGround){currentPiece.lockTimer=framesOnGround+15;}

            if(!heldSwap&&moveAmounts[3]==1){swapHoldPiece();heldSwap=true;}
            rotateWithKick(moveAmounts[2]);

            moveWithCheck(moveAmounts[0],moveAmounts[1]);
            if(moveAmounts[1]==24){lockIn();}
            drawPiece();
            clearLines(detectFilledLines());
            calculateGhost();
            drawGhost();}
        //System.out.println("hi");
    }

    public boolean[]detectFilledLines(){
        boolean[]tempArray=new boolean[h];numFilledLines=0;
        for(int i=0;i<h;i++){if(isRowFilled(i+4)){tempArray[i]=true;numFilledLines+=1;}}
        return tempArray;}

    public void drawPiece(){
        piecePoints=currentPiece.getAdjustedArray();
        for(int i=0;i<piecePoints.length;i++){
            mainList[piecePoints[i][0]][piecePoints[i][1]]=currentPiece.pieceNum+1;}}

    public boolean calculateGhost(){
        ghostPiece=currentPiece.getAdjustedArray();
        for(int j=0;j<h+4;j++){
            if(!testValidPosition(ghostPiece)){
                for(int i=0;i<ghostPiece.length;i++){
                    ghostPiece[i][1]-=1;}
                return true;}
            for(int i=0;i<ghostPiece.length;i++){
                ghostPiece[i][1]+=1;}
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
                if(mainList[x][y]==index||index==-1){mainList[x][y]=0;}
            }
        }
    }

    public boolean isRowFilled(int row){
        boolean temp=true;
        for(int i=0;i<w;i++){if(listWithoutCurrent()[i][row]==0){temp=false;}}
        return temp;}

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
        if(numFilledLines==0&&lockIn2==true){B2B=0;}
        score+=computeScore();
    }

    public int computeScore(){
        int score2=0;int l=1;if(tspin){if(B2B<1){
            if(numFilledLines==1){score2=800*l;scoreType="T-Spin Single";}
            if(numFilledLines==2){score2=1200*l;scoreType="T-Spin Double";}
            if(numFilledLines==3){score2=1600*l;scoreType="T-Spin Triple";}}else if(B2B>0){
                if(numFilledLines==2&&(scoreType.equals("T-Spin Double")||scoreType.equals("B2B T-Spin Double")))
                {score2=1800;scoreType="B2B T-Spin Double";}
                else if(numFilledLines==3&&(scoreType.equals("T-Spin Triple")||scoreType.equals("B2B T-Spin Triple")))
                {score2=2400;scoreType="B2B T-Spin Triple";}
                else if(numFilledLines==1){score2=800;scoreType="T-Spin Single";}
                else if(numFilledLines==2){score2=1200;scoreType="T-Spin Double";}
                else if(numFilledLines==3){score2=1600;scoreType="T-Spin Triple";}
                if(score2>0){score2=(score2+50*B2B)*l;}}
            else{score2*=l;}}else{if(B2B<1){
                if(numFilledLines==1){score2=100*l;scoreType="Single";}
                if(numFilledLines==2){score2=300*l;scoreType="Double";}
                if(numFilledLines==3){score2=500*l;scoreType="Triple";}
                if(numFilledLines==4){score2=800*l;scoreType="Tetris";}}else if(B2B>0){
                if(numFilledLines==4&&(scoreType.equals("Tetris")||scoreType.equals("B2B Tetris"))){
                    scoreType="B2B Tetris";score2=1200;}
                else if(numFilledLines==1){score2=100*l;scoreType="Single";}
                else if(numFilledLines==2){score2=300*l;scoreType="Double";}
                else if(numFilledLines==3){score2=500*l;scoreType="Triple";}
                else if(numFilledLines==4){score2=800*l;scoreType="Tetris";}
                if(score2>0){score2=(score2+50*B2B)*l;}}}
        if(score2>0&&numFilledLines>0){B2B+=1;System.out.println(scoreType);}if(numFilledLines==0){score2=0;}numFilledLines=0;return score2;
    }

    public void lockIn(){
        int[][]modifyArray=currentPiece.getAdjustedArray();
        for(int i=0;i<modifyArray.length;i++){
            mainList[modifyArray[i][0]][modifyArray[i][1]]=currentPiece.pieceNum+1;}
        heldSwap=false;lockIn2=true;
        spawnNewPiece(-1);
        pieceCount+=1;
    }

    public void spawnNewPiece(int index){
        framesOnGround=0;
        if(index==-1){
            if(testValidPositionWithCurrent(new Piece(nextQueue[0],0,3,2).getAdjustedArray())){
            currentPiece=new Piece(nextQueue[0],0,3,2);advanceQueue();}else{restart();}
        }else{
            if(testValidPosition(new Piece(index,0,3,2).getAdjustedArray())){
            currentPiece=new Piece(index,0,3,2);}else{restart();}
        }
    }

    public void restart(){
        pieceCount=0;
        score=0;
        timeStart2=System.nanoTime();
        //pauseFrames=30;
        eraseIndex(-1);
        holdPieceIndex=-1;
        populateQueue();
        spawnNewPiece(-1);
    }

    public void populateQueue(){
        bag=new boolean[]{true,true,true,true,true,true,true};
        for(int i2=0;i2<nextQueue.length;i2++){
            
            for(int i=0;i<nextQueue.length-1;i++){
                nextQueue[i]=nextQueue[i+1];
            }
            nextQueue[nextQueue.length-1]=nextPiece();
        }
    }

    public int nextPiece(){
        int bagCount=0;int selection;
        for(int i=0;i<bag.length;i++){if(bag[i]){bagCount+=1;}}
        if(bagCount==0){selection=rand.nextInt(bag.length);
        bag=new boolean[]{true,true,true,true,true,true,true};}
        else{selection=rand.nextInt(bagCount);}
        bagCount=-1;
        for(int i=0;i<bag.length;i++){
            if(bag[i]){bagCount+=1;if(bagCount==selection){selection=i;bagCount=100;}}}
        bag[selection]=false;return selection;
    }

    public void advanceQueue(){
        for(int i=0;i<nextQueue.length-1;i++){
            nextQueue[i]=nextQueue[i+1];
        }
        nextQueue[nextQueue.length-1]=nextPiece();
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
        //if(lockIn){lockIn();}
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
                //System.out.println(tempArray[0][1]+" "+tempArray[1][1]+" "+tempArray[2][1]+" "+tempArray[3][1]);
                
                if(testValidPosition(tempArray)){
                    currentPiece.move(activeKick[i][0],-activeKick[i][1]);
                    currentPiece.rotate(rotate);
                    if(rotate!=0&&currentPiece.pieceNum==5&&!testValidPosition(currentPiece.getMovedArray(0,-1))){tspin=true;}
                    //System.out.println("valid kick");

                    return true;
                    //currentPiece.getRotatedArrayKick(currentPiece.rotation,currentPiece.rotation+rotate,0);
                    //currentPiece.rotate(rotate);
                }
            }
        }if(rotate!=0&&currentPiece.pieceNum==5&&!testValidPosition(currentPiece.getMovedArray(0,-1))){tspin=true;}
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
        }return temp;
    }

    public boolean testValidPositionWithCurrent(int[][]tempArray){
        boolean temp=true;
        for(int i=0;i<tempArray.length;i++){
            if(tempArray[i][0]>=w||tempArray[i][0]<0){temp=false;}
            if(tempArray[i][1]>=h+4||tempArray[i][1]<0){temp=false;}
            if(temp&&mainList[tempArray[i][0]][tempArray[i][1]]!=0){temp=false;}
        }return temp;
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


    public void swapHoldPiece(){
        int temp=currentPiece.pieceNum;
        spawnNewPiece(holdPieceIndex);
        holdPieceIndex=temp;
    }

    public JFrameImage[]updateHold(){
        //JFrameImage[]tempArray=new JFrameImage[4];
        if(holdPieceIndex>-1){
            int[][]tempArray=Piece.rotations[holdPieceIndex][0];
            for(int i=0;i<holdPiece.length;i++){
                holdPiece[i].setPos(
                    pieceOffset[holdPieceIndex][0]+(int)(tempArray[i][0]*blockSpacing+imageList[0][0].xpos-blockSpacing*5),
                    pieceOffset[holdPieceIndex][1]+(int)(tempArray[i][1]*blockSpacing+imageList[0][0].ypos+blockSpacing));
                if(heldSwap){holdPiece[i].image=images[10];}else{holdPiece[i].image=images[holdPieceIndex+1];}
            }
            
        }
        return holdPiece;
    }

    public JFrameImage[]populateHold(){
        //JFrameImage[]tempArray=new JFrameImage[4];
        for(int y=0;y<holdPiece.length;y++){
            holdPiece[y]=new JFrameImage(rand.nextInt(500), rand.nextInt(500), blockSize, blockSize,0,images[1]);
        }
        return holdPiece;
    }

    public JFrameImage[][]populateNext(){
        //JFrameImage[]tempArray=new JFrameImage[4];
        //JFrameImage[]returnArray=new JFrameImage[nextPieces.length*nextPieces[0].length];
        for(int y=0;y<nextPieces.length;y++){
            for(int x=0;x<nextPieces[0].length;x++){
                nextPieces[y][x]=new JFrameImage(rand.nextInt(500), rand.nextInt(500), blockSize, blockSize,0,images[1]);
            }
        }
        return nextPieces;
    }

    public JFrameImage[][]updateNext(){
        //JFrameImage[]tempArray=new JFrameImage[4];
        for(int a=0;a<nextPieces.length;a++){
            int[][]tempArray=Piece.rotations[nextQueue[a]][0];
            for(int b=0;b<4;b++){
                nextPieces[a][b].setPos(
                    pieceOffset[nextQueue[a]][0]+(int)(tempArray[b][0]*blockSpacing+imageList[0][0].xpos+blockSpacing*10+blockSpacing),
                    pieceOffset[nextQueue[a]][1]+(int)(tempArray[b][1]*blockSpacing+imageList[0][0].ypos+blockSpacing+a*blockSpacing*3));
                nextPieces[a][b].image=images[nextQueue[a]+1];
            }
        }
        return nextPieces;
    }

    public JFrameImage[][]updateList(){
        for(int x=0;x<imageList.length;x++){
            for(int y=0;y<imageList[0].length;y++){
                if(mainList[x][y]==0&&y<4){imageList[x][y].image=images[9];}
                else{imageList[x][y].image=images[mainList[x][y]];}
                if(mainList[x][y]!=0){
                    //rescaleOp.filter(images[mainList[x][y]],imageList[x][y].image); 
                }
                
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