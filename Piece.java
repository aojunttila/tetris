import java.awt.Point;

public class Piece {
    int pieceNum;//I,J,L,O,S,T,Z
    int rotation;int[][]currentArray;
    int xPos;int yPos;
    int[][][][]rotations={//peiceNum,rotation,pointnum,x/y
        {{{0,1},{1,1},{2,1},{3,1}},{{2,0},{2,1},{2,2},{2,3}},{{0,2},{1,2},{2,2},{3,2}},{{1,0},{1,1},{1,2},{1,3}}},
        {{{0,0},{0,1},{1,1},{2,1}},{{1,0},{2,0},{1,1},{1,2}},{{0,1},{1,1},{2,1},{2,2}},{{1,0},{1,1},{0,2},{1,2}}},
        {{{2,0},{0,1},{1,1},{2,1}},{{1,0},{2,2},{1,1},{1,2}},{{0,1},{1,1},{2,1},{0,2}},{{0,0},{1,0},{1,1},{1,2}}},
        {{{1,0},{2,0},{1,1},{2,1}},{{1,0},{2,0},{1,1},{2,1}},{{1,0},{2,0},{1,1},{2,1}},{{1,0},{2,0},{1,1},{2,1}}},
        {{{1,0},{2,0},{0,1},{1,1}},{{1,0},{1,1},{2,1},{2,2}},{{1,1},{2,1},{0,2},{1,2}},{{0,0},{0,1},{1,1},{1,2}}},
        {{{1,0},{0,1},{1,1},{2,1}},{{1,0},{1,1},{2,1},{1,2}},{{0,1},{1,1},{2,1},{1,2}},{{1,0},{0,1},{1,1},{1,2}}},
        {{{0,0},{1,0},{1,1},{2,1}},{{2,0},{1,1},{2,1},{1,2}},{{0,1},{1,1},{1,2},{2,2}},{{1,0},{0,1},{1,1},{0,2}}}};

    public Piece(int pieceNum2,int rotation2,int x,int y){
        pieceNum=pieceNum2;rotation=rotation2;
        currentArray=rotations[pieceNum][rotation];
        xPos=x;yPos=y;
    }


    public int[][]getPointArray(){
        return currentArray;}

    public int[][]getAdjustedArray(){
        int[][]tempArray=new int[4][2];
        for(int i=0;i<4;i++){
            tempArray[i][0]=currentArray[i][0]+xPos;
            tempArray[i][1]=currentArray[i][1]+yPos;}
        return tempArray;
    }

    public int[][]getMovedArray(int x,int y){
        int[][]tempArray=new int[4][2];
        for(int i=0;i<4;i++){
            tempArray[i][0]=currentArray[i][0]+xPos+x;
            tempArray[i][1]=currentArray[i][1]+yPos+y;}
        return tempArray;
    }

    public int[][]getMovedArray2(int x,int y,int[][]intputArray){
        int[][]tempArray=new int[4][2];
        for(int i=0;i<4;i++){
            tempArray[i][0]=intputArray[i][0]+xPos+x;
            tempArray[i][1]=intputArray[i][1]+yPos+y;}
        return tempArray;
    }

    //1=clockwise;2=180;3=counterclockwise
    public void rotate(int rotateThing){
        rotation+=rotateThing;
        rotation=rotation>3?rotation-4:rotation;
        currentArray=rotations[pieceNum][rotation];
    }

    public int[][]getRotatedArray(int rotateThing){
        int temp=rotation+rotateThing;
        temp=temp>3?temp-4:temp;
        return rotations[pieceNum][temp];
    }

    public void move(int x,int y){
        xPos+=x;yPos+=y;
    }
}
