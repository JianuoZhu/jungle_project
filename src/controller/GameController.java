package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;
import model.Chessboard;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;


/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {
    public int turn=1;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    public Chessboard getModel() {
        return model;
    }

    public ChessboardComponent getView() {
        return view;
    }
    String playerColor = null;


    public  PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }


    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }
    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private boolean win() {
        // TODO: Check the board if there is a winner
        return false;
    }


//   saving and loading
     int [][] ChessArray=new int[9][8];

    public  void save() {
        for(int i=0;i<9;i++){
            for(int j=0;j<8;j++){
                ChessArray[i][j]=0;
            }
        }
        //
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(model.getGridAt(new ChessboardPoint(i,j)).getPiece()!=null){
                    if(model.getGridAt(new ChessboardPoint(i,j))
                        .getPiece().getOwner().equals(PlayerColor.BLUE)){
                    ChessArray[i][j]=10+model.getGridAt(new ChessboardPoint(i,j))
                            .getPiece().getRank();
                }
                    if(model.getGridAt(new ChessboardPoint(i,j))
                            .getPiece().getOwner().equals(PlayerColor.RED)){
                        ChessArray[i][j]=20+model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getRank();
                    }


                }else ChessArray[i][j]=0;


            }
        }// add number to Array according to pieces on the chess board;
        if(currentPlayer.equals(PlayerColor.BLUE)){
            ChessArray[0][7]=1;
        }else ChessArray[0][7]=2;//set current player as number
        saveArray(ChessArray);
    }//set array according to chesspieces and save it
    public static void saveArray(int[][] array){
        //1.创建字符输出流
        FileWriter writeFile = null;
        try {
            //2.数据想写入的路径及文件
            File file = new File("Array.txt");
            //3.如果该文件不存在，就创建
            if(!file.exists()) {
                file.createNewFile();
            }
            //4.给字节输出流赋予实例
            writeFile = new FileWriter(file);
            //5.通过循环将数组写入txt文件中
            for(int i = 0; i < array.length; i++) {
                //6.数据前n - 1列尾部加入","
                for(int j = 0; j < array[0].length - 1; j++) {
                    writeFile.write(array[i][j] + ",");
                }
                //7.数组最后一列后面不加","
                writeFile.write(array[i][array[0].length - 1] + "");
                //8.加上换行符
                writeFile.write("\n");
            }
            //9.把writeFile里的数据全部刷新一次，全部写入文件中
            writeFile.flush();
        } catch (Exception e) {//10.异常捕获
            e.printStackTrace();
        } finally {
            try {
                //11.如果writeFile不为空，就将其关闭
                if(writeFile != null)
                    writeFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static int[][] readArray() {
        //1.声明一个字符输入流
        FileReader reader = null;
        //2.声明一个字符输入缓冲流
        BufferedReader readerBuf = null;
        //3.声明一个二维数组
        int[][] array = null;
        try {
            //4.指定reader的读取路径
            reader = new FileReader("Array.txt");
            //5.通过BufferedReader包装字符输入流
            readerBuf = new BufferedReader(reader);
            //6.创建一个集合，用来存放读取的文件的数据
            List<String> strList = new ArrayList<>();
            //7.用来存放一行的数据
            String lineStr;
            //8.逐行读取txt文件中的内容
            while((lineStr = readerBuf.readLine()) != null) {
                //9.把读取的行添加到list中
                strList.add(lineStr);
            }
            //10.获取文件有多少行
            int lineNum = strList.size();
            //11.获取数组有多少列
            String s =  strList.get(0);
            int columnNum = s.split("\\,").length;
            //12.根据文件行数创建对应的数组
            array = new int[strList.size()][columnNum];
            //13.记录输出当前行
            int count = 0;
            //14.循环遍历集合，将集合中的数据放入数组中
            for(String str : strList) {
                //15.将读取的str按照","分割，用字符串数组来接收
                String[] strs = str.split("\\,");
                for(int i = 0; i < columnNum; i++) {
                    array[count][i] = Integer.valueOf(strs[i]);
                }
                //16.将行数 + 1
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //17.关闭字符输入流
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //18.关闭字符输入缓冲流
            try {
                if(readerBuf != null)
                    readerBuf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //19.返回稀疏数组
        return array;

    }
    public  void  load(){//how to translate array to chessboard
        view.initiateGridComponents();
        model.removeAllpieces();
        view.removeChessComponent(model);

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(readArray()[i][j]/10==2){//owner is red
                    String name;
                    if(readArray()[i][j]%20==8){name="Elephant";
                    model.setGridRed(i,j,name,8);}
                    if(readArray()[i][j]%20==7){name="Lion";
                        model.setGridRed(i,j,name,7);}
                    if(readArray()[i][j]%20==6){name="Tiger";
                        model.setGridRed(i,j,name,6);}
                    if(readArray()[i][j]%20==5){name="Leopard";
                        model.setGridRed(i,j,name,5);}
                    if(readArray()[i][j]%20==4){name="Wolf";
                        model.setGridRed(i,j,name,4);}
                    if(readArray()[i][j]%20==3){name="Dog";
                        model.setGridRed(i,j,name,3);}
                    if(readArray()[i][j]%20==2){name="Cat";
                        model.setGridRed(i,j,name,2);}
                    if(readArray()[i][j]%20==1){name="Rat";
                        model.setGridRed(i,j,name,1);}
                }
                if(readArray()[i][j]/10==1){
                    String name;
                    if(readArray()[i][j]%10==8){name="Elephant";
                        model.setGridBule(i,j,name,8);}
                    if(readArray()[i][j]%10==7){name="Lion";
                        model.setGridBule(i,j,name,7);}
                    if(readArray()[i][j]%10==6){name="Tiger";
                        model.setGridBule(i,j,name,6);}
                    if(readArray()[i][j]%10==5){name="Leopard";
                        model.setGridBule(i,j,name,5);}
                    if(readArray()[i][j]%10==4){name="Wolf";
                        model.setGridBule(i,j,name,4);}
                    if(readArray()[i][j]%10==3){name="Dog";
                        model.setGridBule(i,j,name,3);}
                    if(readArray()[i][j]%10==2){name="Cat";
                        model.setGridBule(i,j,name,2);}
                    if(readArray()[i][j]%10==1){name="Rat";
                        model.setGridBule(i,j,name,1);}

                }
            }
        }
        if(readArray()[0][7]==1) {
            currentPlayer = PlayerColor.BLUE;
        }
        if(readArray()[0][7]==2) {
            currentPlayer = PlayerColor.RED;
        }
       view.initiateChessComponent(model);
        view.repaint();
    }



// restart the game
    public void Restart(){
        model.RestartPieces();//right;
        view.initiateGridComponents();//right;
        view.initiateChessComponent(model);
        view.repaint();
        currentPlayer=PlayerColor.BLUE;
    }//easy init to be finished;

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();

            // TODO: if the chess enter Dens or Traps and so on
        }
        else{

        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {

    }

    // click a cell with a chess

    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        }
        // TODO: Implement capture function
        else{
            if (model.getChessPieceOwner(point).equals(currentPlayer) && model.getGridAt(point).getPiece().getRank() == -1){
                model.captureChessPiece(selectedPoint, point);
                ChessComponent removedChess = view.removeChessComponentAtGrid(selectedPoint);
                TrapChessComponent trap = (TrapChessComponent) view.getChessComponentAtGrid(point);
                trap.setStacked(true);
                trap.setStackedChess(removedChess);
                trap.setStackedImage(removedChess.getImage());
                selectedPoint = null;
                swapColor();
                view.repaint();
                return;
            }
            if (model.getChessPieceOwner(point).equals(currentPlayer)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
            }
            else if(!model.isValidCapture(selectedPoint, point)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
            }
            else{
                model.captureChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            }
        }
    }
}