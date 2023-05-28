package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
//>>>>>>> origin/JnZ

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener,Serializable {
    public static GameController current_controller;
    public int turn=1;

    javax.swing.Timer timer = null;

    public Timer getTimer() {
        return timer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private Chessboard model=null;
    private ChessboardComponent view=null;
    private PlayerColor currentPlayer=null;
    private ChessGameFrame gameFrame=null;

    private int leftChess[] = new int[2];

    public int[] getLeftChess() {
        return leftChess;
    }

    public void setLeftChess(int[] leftChess) {
        this.leftChess = leftChess;
    }

    public Chessboard getModel() {
        return model;
    }

    public ChessboardComponent getView() {
        return view;
    }
    String playerColor = null;

    final int[] _time = {20};
    public  PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }


    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public void setSelectedComponent(ChessComponent selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    public ChessComponent getSelectedComponent() {
        return selectedComponent;
    }

    private ChessComponent selectedComponent;
    public GameController(ChessboardComponent view, Chessboard model, ChessGameFrame frame) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.gameFrame = frame;
        this.leftChess[0] = this.leftChess[1] = 8;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
        saveChessBoardStep();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }
    // after a valid move swap the player
    private void swapColor() throws InterruptedException {
        String currentDir = System.getProperty("user.dir");
        String musicPath=null;
        musicPath = currentDir + "\\resource\\eatmusic.wav";
        try {
            File audioFile = new File(musicPath); // 指定要播放的音乐文件路径
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(currentPlayer == gameFrame.AIColor) TimeUnit.SECONDS.sleep(1);
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        gameFrame.remove(ChessGameFrame.current_currentPlayer_JLabel);
        ChessGameFrame.current_currentPlayer_JLabel = gameFrame.addCurrentPlayers();
        gameFrame.remove(ChessGameFrame.current_turn_JLabel);
        ChessGameFrame.current_turn_JLabel = gameFrame.addCurrentTurns();
        //gameFrame.remove(ChessGameFrame.current_timer_JLabel);
        //ChessGameFrame.current_timer_JLabel = gameFrame.addCurrentTimer();
        _time[0] = 20;
        gameFrame.repaint();
        saveChessBoardStep();
        view.paintComponents(view.getGraphics());
        if(currentPlayer == gameFrame.AIColor){
            AIMove();
        }
    }

    private void win(PlayerColor winnerColor) {
        String currentDir = System.getProperty("user.dir");
        String musicPath=null;
        musicPath = currentDir + "\\resource\\musicwin.wav";
        try {
            File audioFile = new File(musicPath); // 指定要播放的音乐文件路径
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Winning!");

        // Set the size and location of the frame
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a new JLabel object with some text
        JLabel label = new JLabel("Winner:"+winnerColor);
        label.setLocation(150, 100);
        label.setFont(new Font("Rockwell", Font.BOLD, 40));
        // Add the label to the frame's content pane
        frame.getContentPane().add(label);

        // Set the frame to be visible
        frame.setVisible(true);
    }
    public JLabel setTimer(){
        JLabel timerLabel = new JLabel();
        timerLabel.setForeground(Color.YELLOW);
        javax.swing.Timer finalTimer = timer;
        ActionListener timerListener = new ActionListener() {
            @Override
            // Update the label text with the current time
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText("Timer: " + _time[0]);
                timerLabel.setForeground(Color.YELLOW);
                timerLabel.setFont(new Font("Rockwell", Font.BOLD, 28));
                _time[0]--;
                if (_time[0] == 0) {
                    try {
                        swapColor();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        timer = new javax.swing.Timer(1000, timerListener);
        timer.start();
        return timerLabel;
    }
//saving and loading
    int [][] ChessArray=new int[9][8];
    ArrayList<Integer> ChessBoardArray=new ArrayList<>();//可变数组用来做媒介
    public void save(){//将当前的一维可变数组转换成二维数组并保存；
        timer.stop();
        int m=0;
        m=ChessBoardArray.size()/8;
        int[][] saveChessArray=new int[m][8];
        int count=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<8;j++){
                saveChessArray[i][j]=ChessBoardArray.get(count);
                count++;
            }
        }
        saveArray(saveChessArray);
        _time[0] = 20;
        timer.start();
    }

    public void removeArrayList(){
        ChessBoardArray.clear();
    }

    public  void saveChessBoardStep() {//每走一步将棋盘存入数组
        for(int i=0;i<9;i++){
            for(int j=0;j<8;j++){
                ChessArray[i][j]=0;
            }
        }
        //
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                int NameCount=0;
                if(model.getGridAt(new ChessboardPoint(i,j)).getPiece()!=null){
                    if(model.getGridAt(new ChessboardPoint(i,j))
                            .getPiece().getRank()==0){//判断有无踩到陷阱
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Elephant")){NameCount=8;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Lion")){NameCount=7;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Tiger")){NameCount=6;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Leopard")){NameCount=5;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Wolf")){NameCount=4;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Dog")){NameCount=3;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Cat")){NameCount=2;}
                        if(model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getName().equals("Rat")){NameCount=1;}

                    }



                    if(model.getGridAt(new ChessboardPoint(i,j))
                            .getPiece().getOwner().equals(PlayerColor.BLUE)){
                        ChessArray[i][j]=NameCount*100+10+model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getRank();
                    }
                    if(model.getGridAt(new ChessboardPoint(i,j))
                            .getPiece().getOwner().equals(PlayerColor.RED)){
                        ChessArray[i][j]= NameCount*100+20+model.getGridAt(new ChessboardPoint(i,j))
                                .getPiece().getRank();
                    }


                }else ChessArray[i][j]=0;


            }
        }// add number to Array according to pieces on the chess board;
        if(currentPlayer.equals(PlayerColor.BLUE)){
            ChessArray[0][7]=1;
        }else ChessArray[0][7]=2;//set current player as number

        ChessArray[1][7]=turn;
        ChessArray[2][7]=7;
        ChessArray[3][7]=9;
        ChessArray[4][7]=getLeftChess()[0];
        ChessArray[5][7]=getLeftChess()[1];

        for(int j=0;j<9;j++) {//j代表行数
            for( int i=0;i<8;i++){//i为列数
                ChessBoardArray.add(ChessArray[j][i]);
            }
        }//将当前棋盘的二维数组存入一个可变一维数组；


    }//set array according to chesspieces and save it
    public void saveArray(int[][] array){
        //1.创建字符输出流

        File selectedFile = chooseFile();
        FileWriter writeFile = null;
        try {
            //2.数据想写入的路径及文件
            //File file = new File("Array.txt");
            File file = selectedFile;
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

    public File chooseFile(){
        JFileChooser fileChooser = new JFileChooser();
        String currentDir = System.getProperty("user.dir");
        currentDir += "\\archives";
        File defaultDir = new File(currentDir);
        fileChooser.setCurrentDirectory(defaultDir);
        //fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(gameFrame);
        // Check if a file was selected
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            selectedFile = fileChooser.getSelectedFile();

            // Process the selected file
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }


    public int[][] readArray(File selectedFile) throws FileNotFoundException {

        //1.声明一个字符输入流
        FileReader reader = null;
        //2.声明一个字符输入缓冲流
        BufferedReader readerBuf = null;
        //3.声明一个二维数组
        int[][] array = null;
            try {
                //4.指定reader的读取路径
                reader = new FileReader(selectedFile);
                //5.通过BufferedReader包装字符输入流
                readerBuf = new BufferedReader(reader);
                //6.创建一个集合，用来存放读取的文件的数据
                List<String> strList = new ArrayList<>();
                //7.用来存放一行的数据
                String lineStr;
                //8.逐行读取txt文件中的内容
                while ((lineStr = readerBuf.readLine()) != null) {
                    //9.把读取的行添加到list中
                    strList.add(lineStr);
                }
                //10.获取文件有多少行
                int lineNum = strList.size();
                //11.获取数组有多少列
                String s = strList.get(0);
                int columnNum = s.split("\\,").length;
                //12.根据文件行数创建对应的数组
                array = new int[strList.size()][columnNum];
                //13.记录输出当前行
                int count = 0;
                //14.循环遍历集合，将集合中的数据放入数组中
                for (String str : strList) {
                    //15.将读取的str按照","分割，用字符串数组来接收
                    String[] strs = str.split("\\,");
                    for (int i = 0; i < columnNum; i++) {
                        array[count][i] = Integer.valueOf(strs[i]);
                    }
                    //16.将行数 + 1
                    count++;
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                //17.关闭字符输入流
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //18.关闭字符输入缓冲流
                try {
                    if (readerBuf != null)
                        readerBuf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        return array;

    }
    int Undocount=1;

    public void UnDo() throws FileNotFoundException {
        view.initiateGridComponents();
        model.removeAllpieces();
        view.removeChessComponent(model);

        int[][] readChessBoardNowArray=new int[9][8];
        int k=0;
        int countStep=0;
        for(int m=0;m<9;m++){
            for(int n=0;n<8;n++){
                readChessBoardNowArray[m][n]=ChessBoardArray.get(0+(turn-2)*72+countStep);
                //用来记录要load的棋盘
                countStep++;
            }
        }
        Undocount=Undocount+9;


        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                int a = readChessBoardNowArray[i][j] % 100;
                if (a / 10 == 2) {//owner is red
                    if(a%10!=0){
                        model.setGridRed(i, j, a % 10, a % 10);
                    }else {model.setGridBule(i,j,readChessBoardNowArray[i][j]/100,0);}

                }
                if (a / 10 == 1) {
                    if(a%10!=0){
                        model.setGridBule(i, j, a % 10, a % 10);
                    }else {model.setGridBule(i,j,readChessBoardNowArray[i][j]/100,0);}

                }
            }
            if (readChessBoardNowArray[0][7] == 1) {
                currentPlayer = PlayerColor.BLUE;
            }
            if (readChessBoardNowArray[0][7] == 2) {
                currentPlayer = PlayerColor.RED;
            }

            turn=readChessBoardNowArray[1][7];


        }
        view.initiateChessComponent(model);
        view.paintComponents(view.getGraphics());
        gameFrame.remove(ChessGameFrame.current_currentPlayer_JLabel);
        ChessGameFrame.current_currentPlayer_JLabel = gameFrame.addCurrentPlayers();
        gameFrame.remove(ChessGameFrame.current_turn_JLabel);
        ChessGameFrame.current_turn_JLabel = gameFrame.addCurrentTurns();
        _time[0] = 20;
        gameFrame.repaint();

    }
    public void changeBg(){
        gameFrame.remove(gameFrame.getBacklabel());
//        gameFrame.setBacklabel(null);
        gameFrame.repaint();
//        gameFrame.changeBackgroundnumber();
//
//        String currentDir = System.getProperty("user.dir");
//        String ImagePath=null;
//        if(gameFrame.backgroundnumber==1){
//            ImagePath = currentDir + "\\resource\\bk2.png";
//        }
//        if(gameFrame.backgroundnumber==0){
//            ImagePath = currentDir + "\\resource\\bk1.png";
//        }
//
//        // 背景图片
//        ImageIcon background = new ImageIcon(ImagePath);
//        // 把背景图片显示在一个标签里面
//        background.setImage(background.getImage().getScaledInstance(1100, 810,Image.SCALE_DEFAULT ));
//        // 把背景图片显示在一个标签里面
//        JLabel label = new JLabel();
//        label.setSize(1100,810);
//        label.setIcon(background);
//        label.setBounds(0,0,1100,810);
//        label.setPreferredSize(new Dimension(1100, 810));
//        gameFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
//        ((JPanel)gameFrame.getContentPane()).setOpaque(false); //设置透明
//        gameFrame.setBacklabel(label);
//        gameFrame.repaint();
    }

    public Boolean CheckError(File selectedFile) throws FileNotFoundException {
        String fileName = selectedFile.getName();
        if(fileName.endsWith(".txt")) {
            System.out.println("This is a txt file.");
        } else {
            String message = "文件格式错误\n" +
                    "错误编码： 101\n";
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        ChessBoardArray.clear();

        for(int j=0;j< readArray(selectedFile).length;j++) {//j代表行数
            for( int i=0;i<8;i++){//i为列数
                ChessBoardArray.add(readArray(selectedFile)[j][i]);
            }
        }//存储的所有棋盘存入数组；
        for(int p=0;p<readArray(selectedFile).length;p=p+9){
            if(readArray(selectedFile)[3+p][1]%10>1||readArray(selectedFile)[3+p][2]%10>1||
            readArray(selectedFile)[3+p][4]%10>1||readArray(selectedFile)[3+p][5]%10>1
            ||readArray(selectedFile)[4+p][1]%10>1||readArray(selectedFile)[4+p][2]%10>1||
            readArray(selectedFile)[4+p][4]%10>1||readArray(selectedFile)[4+p][5]%10>1
            ||readArray(selectedFile)[5+p][1]%10>1||readArray(selectedFile)[5+p][2]%10>1||
                    readArray(selectedFile)[5+p][4]%10>1||readArray(selectedFile)[5+p][5]%10>1){
                String message = "非法行棋\n" +
                        "错误编码： 105\n";
                JOptionPane.showMessageDialog(null, message);
                return false;

            }//有无进河

            for(int m=0;m<9;m++){
                for (int j=0;j<7;j++){
                    if(m+9+p<readArray(selectedFile).length){
                        if(readArray(selectedFile)[m+9+p][j]%100%10!=readArray(selectedFile)[m+p][j]%100%10&&readArray(selectedFile)[m+p][j]%100%10!=8
                                &&readArray(selectedFile)[m+p][j]%100%10>readArray(selectedFile)[m+9+p][j]%100%10&&readArray(selectedFile)[m+9+p][j]!=0){
                            String message = "非法行棋\n" +
                                    "错误编码： 105\n";
                            JOptionPane.showMessageDialog(null, message);
                            return false;
                        }
                        if(readArray(selectedFile)[m+9+p][j]%100/10==readArray(selectedFile)[m+p][j]%100/10&&
                                readArray(selectedFile)[m+9+p][j]%100%10!=readArray(selectedFile)[m+p][j]%100%10
                        ){
                            String message = "非法行棋\n" +
                                    "错误编码： 105\n";
                            JOptionPane.showMessageDialog(null, message);
                            return false;
                        }
                    }

                }
            }//有无小子吃大
            for(int m=0;m<9;m++) {
                for (int j = 0; j < 7; j++) {
                    if(readArray(selectedFile)[0+p][3]%100/10==1||readArray(selectedFile)[8+p][3]%100/10==2){
                        String message = "非法行棋\n" +
                                "错误编码： 105\n";
                        JOptionPane.showMessageDialog(null, message);
                        return false;
                    }
                }
            }//棋子进自己家




        }

        int[][] CheckArray=new int[9][8];

        for(int m=0;m<9;m++){
            for(int n=0;n<8;n++){
                CheckArray[m][n]=readArray(selectedFile)[readArray(selectedFile).length-9+m][n];
                //用来记录要Check的棋盘

                }
            }
        if(CheckArray[2][7]!=7||CheckArray[3][7]!=9){
            String message = "棋盘并非7*9\n" +
                    "错误编码： 102\n";
            JOptionPane.showMessageDialog(null, message);
            return false;

        }//用来check第二个；
        for(int m=0;m<9;m++){
            for(int n=0;n<7;n++){
                int countmid1=CheckArray[m][n]%100/10;
                int countmid2=CheckArray[m][n]/100;
               if(countmid2>8) {
                   String message = "棋子错误\n" +
                           "错误编码： 103\n";
                   JOptionPane.showMessageDialog(null, message);
                   return false;
               }else if(countmid1>0&&countmid1!=1&&countmid1!=2){
                   String message = "棋子错误\n" +
                           "错误编码： 103\n";
                   JOptionPane.showMessageDialog(null, message);
                   return false;

               }

            }
        }//用来check第三个
        if(CheckArray[0][7]!=1&&CheckArray[0][7]!=2){
            String message = "缺少下一步行棋方\n" +
                    "错误编码： 104\n";
            JOptionPane.showMessageDialog(null, message);
            return false;

        }//用来check第四个；







        return true;
    }



    public  void  load(File selectedFile) throws FileNotFoundException {
        timer.stop();
        ChessBoardArray.clear();
        for(int j=0;j< readArray(selectedFile).length;j++) {//j代表行数
            for( int i=0;i<8;i++){//i为列数
                ChessBoardArray.add(readArray(selectedFile)[j][i]);
            }
        }//将当前棋盘的二维数组存入一个可变一维数组；

        //how to translate array to chessboard
        view.initiateGridComponents();
        model.removeAllpieces();
        view.removeChessComponent(model);
        int[][] readChessBoardNowArray=new int[9][8];
        int k=0;
        for(int m=0;m<9;m++){
            for(int n=0;n<8;n++){
                readChessBoardNowArray[m][n]=readArray(selectedFile)[readArray(selectedFile).length-9+m][n];
                //用来记录要load的棋盘
            }
        }

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                int a = readChessBoardNowArray[i][j] % 100;
                if (a / 10 == 2) {//owner is red
                    if(a%10!=0){
                        model.setGridRed(i, j, a % 10, a % 10);
                    }else {model.setGridBule(i,j,readChessBoardNowArray[i][j]/100,0);}

                }
                if (a / 10 == 1) {
                    if(a%10!=0){
                        model.setGridBule(i, j, a % 10, a % 10);
                    }else {model.setGridBule(i,j,readChessBoardNowArray[i][j]/100,0);}

                }
            }
            if (readChessBoardNowArray[0][7] == 1) {
                currentPlayer = PlayerColor.BLUE;
            }
            if (readChessBoardNowArray[0][7] == 2) {
                currentPlayer = PlayerColor.RED;
            }

            turn=readChessBoardNowArray[1][7];
            setLeftChess(new int[]{readChessBoardNowArray[4][7], readChessBoardNowArray[5][7]});


        }
        view.initiateChessComponent(model);
        view.paintComponents(view.getGraphics());
        gameFrame.remove(ChessGameFrame.current_currentPlayer_JLabel);
        ChessGameFrame.current_currentPlayer_JLabel = gameFrame.addCurrentPlayers();
        gameFrame.remove(ChessGameFrame.current_turn_JLabel);
        ChessGameFrame.current_turn_JLabel = gameFrame.addCurrentTurns();
        gameFrame.repaint();
        _time[0] = 20;
        timer.start();
    }

//玩家vs电脑
    int []dirx={1,0,0,-1};
    int []diry= {0,1,-1,0};//方向数组
    int AIdifficultynumber=0;
    public void settingAId(int count){
        AIdifficultynumber=count;
    }
    public int  gettingAId(){
        return  AIdifficultynumber;
    }
    public  void ChangeAIdifficulty(){
        if(gettingAId()==1){
            settingAId(0);
        }
        if ((gettingAId()==0)){
            settingAId(1);
        }
    }

    public void ChessGameReplay(File selectedFile) throws FileNotFoundException, InterruptedException {//棋局回放
        for(int j=0;j< readArray(selectedFile).length;j++) {//j代表行数
            for( int i=0;i<8;i++){//i为列数
                ChessBoardArray.add(readArray(selectedFile)[j][i]);
            }
        }//将当前棋盘的二维数组存入一个可变一维数组；

        int k=0;

        int count=-9;
       label1: for(int p=readArray(selectedFile).length-9;p>=0;){
            view.initiateGridComponents();
            model.removeAllpieces();
            view.removeChessComponent(model);

            int[][] readChessBoardNowArray=new int[9][8];
            for(int m=0;m<9;m++){
                for(int n=0;n<8;n++){
                    readChessBoardNowArray[m][n]=readArray(selectedFile)[readArray(selectedFile).length-9+m-p][n];
                    //用来记录要load的棋盘
                }
            }

            for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                    int a = readChessBoardNowArray[i][j] % 100;
                    if (a / 10 == 2) {//owner is red
                        if(a%10!=0){
                            model.setGridRed(i, j, a % 10, a % 10);
                        }else {model.setGridBule(i,j,readChessBoardNowArray[i][j]/100,0);}

                    }
                    if (a / 10 == 1) {
                        if(a%10!=0){
                            model.setGridBule(i, j, a % 10, a % 10);
                        }else {model.setGridBule(i,j,readChessBoardNowArray[i][j]/100,0);}

                    }
                }
                if (readChessBoardNowArray[0][7] == 1) {
                    currentPlayer = PlayerColor.BLUE;
                }
                if (readChessBoardNowArray[0][7] == 2) {
                    currentPlayer = PlayerColor.RED;
                }

                turn=readChessBoardNowArray[1][7];
                setLeftChess(new int[]{readChessBoardNowArray[4][7], readChessBoardNowArray[5][7]});


            }
            if(3+count>0){
                if(readArray(selectedFile)[3+count][1]%10>1||readArray(selectedFile)[3+count][2]%10>1||
                        readArray(selectedFile)[3+count][4]%10>1||readArray(selectedFile)[3+count][5]%10>1
                        ||readArray(selectedFile)[4+count][1]%10>1||readArray(selectedFile)[4+count][2]%10>1||
                        readArray(selectedFile)[4+count][4]%10>1||readArray(selectedFile)[4+count][5]%10>1
                        ||readArray(selectedFile)[5+count][1]%10>1||readArray(selectedFile)[5+count][2]%10>1||
                        readArray(selectedFile)[5+count][4]%10>1||readArray(selectedFile)[5+count][5]%10>1){
                    String message = "非法行棋\n" +
                            "错误编码： 105\n";
                    JOptionPane.showMessageDialog(null, message);
                    break label1;

            }



            }//有无进河

            for(int m=0;m<9;m++){
                for (int j=0;j<7;j++){
                    if(m+9+p<readArray(selectedFile).length){
                        if(m+count>=0){
                            if(readArray(selectedFile)[m+9+count][j]%100%10!=readArray(selectedFile)[m+count][j]%100%10&&readArray(selectedFile)[m+count][j]%100%10!=8
                                    &&readArray(selectedFile)[m+count][j]%100%10>readArray(selectedFile)[m+9+count][j]%100%10&&readArray(selectedFile)[m+9+count][j]!=0){
                                String message = "非法行棋\n" +
                                        "错误编码： 105\n";
                                JOptionPane.showMessageDialog(null, message);
                                break label1;
                            }
                            if(readArray(selectedFile)[m+9+count][j]%100/10==readArray(selectedFile)[m+count][j]%100/10&&
                                    readArray(selectedFile)[m+9+count][j]%100%10!=readArray(selectedFile)[m+count][j]%100%10
                            ){
                                String message = "非法行棋\n" +
                                        "错误编码： 105\n";
                                JOptionPane.showMessageDialog(null, message);
                                break label1;
                            }

                        }

                    }

                }
            }//有无小子吃大
            for(int m=0;m<9;m++) {
                for (int j = 0; j < 7; j++) {
                    if(count>=0){
                        if(readArray(selectedFile)[0+count][3]%100/10==1||readArray(selectedFile)[8+count][3]%100/10==2){
                            String message = "非法行棋\n" +
                                    "错误编码： 105\n";
                            JOptionPane.showMessageDialog(null, message);
                            break label1;
                        }
                    }

                }
            }//棋子进自己家

            count=count+9;
            view.initiateChessComponent(model);
            view.paintComponents(view.getGraphics());
            /*gameFrame.remove(ChessGameFrame.current_currentPlayer_JLabel);
            ChessGameFrame.current_currentPlayer_JLabel = gameFrame.addCurrentPlayers();
            gameFrame.remove(ChessGameFrame.current_turn_JLabel);
            ChessGameFrame.current_turn_JLabel = gameFrame.addCurrentTurns();
            //gameFrame.paintComponents(ChessGameFrame.current_currentPlayer_JLabel.getGraphics());
           //gameFrame.paintComponents(ChessGameFrame.current_timer_JLabel.getGraphics());
           //gameFrame.paintComponents(ChessGameFrame.current_turn_JLabel.getGraphics());
           gameFrame.repaint();
            p=p-9;*/

            Thread.currentThread().sleep(500);


        }



    }

    public void AIMove() throws InterruptedException {
        if(gettingAId()==1){
            MediumAIMove(currentPlayer);
        }
        if(gettingAId()==0){
            EasyAIMove(currentPlayer);
        }

    }
    public void MediumAIMove(PlayerColor playerColor) throws InterruptedException {//AI操作
        ArrayList<ChessboardPoint> availableChessPosition = new ArrayList<ChessboardPoint>();
        for(int i=0; i < CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j=0; j < CHESSBOARD_COL_SIZE.getNum(); j++){
                ChessboardPoint point = new ChessboardPoint(i, j);
                if(model.getGridAt(point).getPiece() != null && model.getChessPieceOwner(point) == playerColor){
                    availableChessPosition.add(point);
                }
            }
        }
        boolean eatable = false;

        int p=0, x=0, y=0;
        ChessboardPoint src = null;
        boolean flag = false;
        ChessboardPoint dest = null;

        Random random = new Random();
        for(int i=0; i<availableChessPosition.size(); i++){
            p = i;
            x = availableChessPosition.get(p).getRow();
            y = availableChessPosition.get(p).getCol();
            src = availableChessPosition.get(p);
            dest = new ChessboardPoint(x, y);
            for (int k = 0; k < 4; k++) {

                int px = x + dirx[k];
                int py = y + diry[k];
                dest = new ChessboardPoint(px, py);
                if (dest.getRow() < 0 || dest.getRow() >= CHESSBOARD_ROW_SIZE.getNum() || dest.getCol() < 0 || dest.getCol() >= CHESSBOARD_COL_SIZE.getNum())
                    continue;
                if (model.getGridAt(dest).getPiece() == null) continue;
                if (model.getChessPieceOwner(dest) == playerColor) continue;
                if (model.isValidCapture(src, dest)) {
                    eatable = true;
                    break;
                }
            }
            if(eatable) break;
        }
        if(!eatable){
            p = random.nextInt(availableChessPosition.size());
            x = availableChessPosition.get(p).getRow();
            y = availableChessPosition.get(p).getCol();
            src = availableChessPosition.get(p);
            if (currentPlayer == PlayerColor.RED) dest = new ChessboardPoint(x + 1, y);
            else dest = new ChessboardPoint(x - 1, y);
        }
        while(!flag){
            if(dest.getRow() < 0 || dest.getRow() >= CHESSBOARD_ROW_SIZE.getNum() || dest.getCol() < 0 || dest.getCol() >= CHESSBOARD_COL_SIZE.getNum()) {
                dest = new ChessboardPoint(x+random.nextInt(3)-1,y+ random.nextInt(3)-1);
                continue;
            }
            if(model.getGridAt(dest).getPiece() == null){
                if(model.isValidMove(src, dest, view.isWater(dest))){
                    flag = true;
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    turn++;
                    System.out.println(turn);
                    swapColor();
                    view.repaint();
                    if(model.getTrapUsed()[dest.getRow()][dest.getCol()] && !model.getTrapRemoved()[dest.getRow()][dest.getCol()]){
                        view.getGridComponents()[dest.getRow()][dest.getCol()].setImage(null);
                        //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                        model.getTrapRemoved()[dest.getRow()][dest.getCol()] = true;
                    }
                    if(model.isHome(dest)){
                        if((dest.getRow() < 5 && !(model.getChessPieceOwner(dest) == playerColor))
                                || (dest.getRow() > 5 && model.getChessPieceOwner(dest) == playerColor)){
                            win(model.getChessPieceOwner(dest));
                        }
                    }
                }
                dest = new ChessboardPoint(x+random.nextInt(3)-1,y+ random.nextInt(3)-1);
            }
            else{
                if(model.getChessPieceOwner(dest) == playerColor) {
                    dest = new ChessboardPoint(x+random.nextInt(3)-1,y+ random.nextInt(3)-1);
                    continue;
                }
                if(model.isValidCapture(src, dest)){
                    flag = true;
                    model.captureChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    turn++;
                    minusChess();
                    swapColor();
                    view.repaint();

                    if(model.getTrapUsed()[dest.getRow()][dest.getCol()] && !model.getTrapRemoved()[dest.getRow()][dest.getCol()]){
                        view.getGridComponents()[dest.getRow()][dest.getCol()].setImage(null);
                        //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                        model.getTrapRemoved()[dest.getRow()][dest.getCol()] = true;
                    }
                }
            }
        }
        view.paintComponents(view.getGraphics());

    }
    public void EasyAIMove(PlayerColor playerColor) throws InterruptedException {//turn 为偶数时AI操作

        ArrayList<ChessboardPoint> availableChessPosition = new ArrayList<ChessboardPoint>();
        for(int i=0; i < CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j=0; j < CHESSBOARD_COL_SIZE.getNum(); j++){
                ChessboardPoint point = new ChessboardPoint(i, j);
                if(model.getGridAt(point).getPiece() != null && model.getChessPieceOwner(point) == playerColor){
                    availableChessPosition.add(point);
                }
            }
        }
        Random random = new Random();
        int p = random.nextInt(availableChessPosition.size());
        int x = availableChessPosition.get(p).getRow();
        int y = availableChessPosition.get(p).getCol();
        ChessboardPoint src = availableChessPosition.get(p);
        boolean flag = false;
        while(!flag){
            ChessboardPoint dest = new ChessboardPoint(x+random.nextInt(3)-1,y+ random.nextInt(3)-1);
            if(dest.getRow() < 0 || dest.getRow() >= CHESSBOARD_ROW_SIZE.getNum() || dest.getCol() < 0 || dest.getCol() >= CHESSBOARD_COL_SIZE.getNum()) continue;
            if(model.getGridAt(dest).getPiece() == null){
                if(model.isValidMove(src, dest, view.isWater(dest))){
                    flag = true;
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    turn++;
                    System.out.println(turn);
                    swapColor();
                    view.repaint();
                    if(model.getTrapUsed()[dest.getRow()][dest.getCol()] && !model.getTrapRemoved()[dest.getRow()][dest.getCol()]){
                        view.getGridComponents()[dest.getRow()][dest.getCol()].setImage(null);
                        //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                        model.getTrapRemoved()[dest.getRow()][dest.getCol()] = true;
                    }
                    if(model.isHome(dest)){
                        if((dest.getRow() < 5 && !(model.getChessPieceOwner(dest) == playerColor))
                                || (dest.getRow() > 5 && model.getChessPieceOwner(dest) == playerColor)){
                            win(model.getChessPieceOwner(dest));
                        }
                    }
                }
            }
            else{
                if(model.getChessPieceOwner(dest) == playerColor) continue;
                if(model.isValidCapture(src, dest)){
                    flag = true;
                    model.captureChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    turn++;
                    minusChess();
                    swapColor();
                    view.repaint();

                    if(model.getTrapUsed()[dest.getRow()][dest.getCol()] && !model.getTrapRemoved()[dest.getRow()][dest.getCol()]){
                        view.getGridComponents()[dest.getRow()][dest.getCol()].setImage(null);
                        //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                        model.getTrapRemoved()[dest.getRow()][dest.getCol()] = true;
                    }
                }
            }
        }
        view.paintComponents(view.getGraphics());

    }

    // restart the game
    public void Restart(){
        model.RestartPieces();//right;
        view.initiateGridComponents();//right;
        view.initiateChessComponent(model);
        currentPlayer=PlayerColor.BLUE;
        view.paintComponents(view.getGraphics());
        turn=1;
        view.registerController(this);
        initialize();
        gameFrame.remove(ChessGameFrame.current_currentPlayer_JLabel);
        ChessGameFrame.current_currentPlayer_JLabel = gameFrame.addCurrentPlayers();
        gameFrame.remove(ChessGameFrame.current_turn_JLabel);
        ChessGameFrame.current_turn_JLabel = gameFrame.addCurrentTurns();
        model.RestartTrap();//
        removeArrayList();
        saveChessBoardStep();
        _time[0] = 20;
        gameFrame.repaint();
        Undocount=1;
    }//easy init to be finished;

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) throws InterruptedException {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point, view.isWater(point))) {

            selectedComponent.x = point.getRow();
            selectedComponent.y = point.getCol();
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            turn++;
            System.out.println(turn);
            selectedPoint = null;
            repaintGreenCell();
            swapColor();
            view.repaint();
            /*if(model.getTrapUsed()[point.getRow()][point.getCol()] && !model.getTrapRemoved()[point.getRow()][point.getCol()]){
                view.getGridComponents()[point.getRow()][point.getCol()].setImage(null);
                //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                model.getTrapRemoved()[point.getRow()][point.getCol()] = true;
            }*/
            if(selectedPoint != null && model.isTrap(selectedPoint)){
                if(model.getGridAt(point).getPiece() != null) model.getGridAt(point).getPiece().setOnTrap(false);
            }
            if(model.isTrap(point)){
                model.getGridAt(point).getPiece().setOnTrap(true);
            }
            if(model.isHome(point)){
                if((point.getRow() < 5 && model.getChessPieceOwner(point) == PlayerColor.BLUE)
                    || (point.getRow() > 5 && model.getChessPieceOwner(point) == PlayerColor.RED)){
                    win(model.getChessPieceOwner(point));
                }
            }
            // TODO: if the chess enter Dens or Traps and so on
        }
        else{

        }
    }
    public void onPlayerEnterCell(ChessboardPoint point, ChessComponent component) throws InterruptedException {
        component.setEntered(true);
        component.repaint();
        view.paint(view.getGraphics());
    }
    public void onPlayerExitCell(ChessboardPoint point, ChessComponent component) throws InterruptedException {
        component.setEntered(false);
        component.repaint();
        view.paint(view.getGraphics());
    }
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {

    }

    // click a cell with a chess

    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) throws InterruptedException {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                selectedComponent = component;
                component.setSelected(true);
                component.repaint();
                paintAvailableCell();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            selectedComponent = null;
            component.setSelected(false);
            component.repaint();
            repaintGreenCell();
        }
        // TODO: Implement capture function
        else{
            if(view.isWater(selectedPoint) || view.isWater(point)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
                selectedComponent = null;
                repaintGreenCell();
            }
            if(model.isTrap(point)){
                /*if(model.getTrapUsed()[point.getRow()][point.getCol()] && !model.getTrapRemoved()[point.getRow()][point.getCol()]){
                    view.getGridComponents()[point.getRow()][point.getCol()].setImage(null);
                    //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                    model.getTrapRemoved()[point.getRow()][point.getCol()] = true;
                }*/
                selectedComponent.x = point.getRow();
                selectedComponent.y = point.getCol();
                model.captureChessPiece(selectedPoint, point);
                model.getGridAt(point).getPiece().setOnTrap(true);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                selectedComponent = null;
                repaintGreenCell();
                turn++;

                minusChess();
                swapColor();
                view.repaint();
            }
            if (model.getChessPieceOwner(point).equals(currentPlayer)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
                selectedComponent = null;
                repaintGreenCell();
            }
            else if(!model.isValidCapture(selectedPoint, point)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
                selectedComponent = null;
                repaintGreenCell();
            }
            else{
                selectedComponent.x = point.getRow();
                selectedComponent.y = point.getCol();
                model.captureChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                selectedComponent = null;
                repaintGreenCell();
                turn++;
                minusChess();
                swapColor();
                view.repaint();
            }
        }
    }
    private void minusChess(){
        if(currentPlayer == PlayerColor.RED) {
            leftChess[0]--;
            if(leftChess[0] == 0) win(PlayerColor.RED);
        }
        if(currentPlayer == PlayerColor.BLUE) {
            leftChess[1]--;
            if(leftChess[1] == 0) win(PlayerColor.BLUE);
        }
    }

    private void paintAvailableCell(){
        for(int i=0; i < CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j=0; j < CHESSBOARD_COL_SIZE.getNum(); j++){
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if(getModel().isValidMove(selectedPoint, dest, view.isWater(dest)) && model.getGridAt(dest).getPiece() == null){
                    view.getGridComponents()[i][j].setBackground(Color.GREEN);
                    view.paintComponents(view.getGraphics());
                }
                if(model.getGridAt(selectedPoint).getPiece().getRank() == 6 || model.getGridAt(selectedPoint).getPiece().getRank() == 7){
                    if(!model.isWaterCell(dest) && model.isValidJump(selectedPoint, dest) && model.getGridAt(dest).getPiece() == null) {
                        view.getGridComponents()[i][j].setBackground(Color.GREEN);
                        view.paintComponents(view.getGraphics());
                    }
                }
                if(model.getGridAt(dest).getPiece() == null) continue;
                if(getModel().isValidCapture(selectedPoint, dest)){
                    view.getGridComponents()[i][j].setBackground(Color.GREEN);
                    view.paintComponents(view.getGraphics());
                }
            }
        }
    }
    private void repaintGreenCell(){
        for(int i=0; i < CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j=0; j < CHESSBOARD_COL_SIZE.getNum(); j++){
                if(view.getGridComponents()[i][j].getBackground() == Color.GREEN){
                    view.getGridComponents()[i][j].setBackground(Color.LIGHT_GRAY);
                    if(model.isWaterCell(new ChessboardPoint(i, j))) view.getGridComponents()[i][j].setBackground(Color.CYAN);
                }
            }
        }
    }
}