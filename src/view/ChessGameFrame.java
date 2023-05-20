package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements Serializable {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    public int backgroundnumber =0;
    private final int HEIGTH;
    public PlayerColor AIColor = null;
    public static JLabel current_currentPlayer_JLabel;
    public static JLabel current_turn_JLabel;
    public static JLabel current_timer_JLabel;
    private final int ONE_CHESS_SIZE;
    private JButton RestartBotton;
    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addChessboard();
        //addLabel();
        addRestartButton();
        addSaveButton();
        addLoadButton();
        addAImoveButton();
        addUndoButton();
       backlabel=addBackGround();
        addReplayButton();
        addChangeBk();

    }
    JLabel backlabel =null;

    public JLabel getBacklabel() {
        return backlabel;
    }

    public void setBacklabel(JLabel backlabel) {
        this.backlabel = backlabel;
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }
    public void changeBackgroundnumber(){
        if(backgroundnumber==0){
            backgroundnumber=1;
        }
        if(backgroundnumber==1){
            backgroundnumber=0;
        }

    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }


    /**
     * 在游戏面板中添加标签
     */
    public JLabel addCurrentPlayers() {
        String a="CurrentPlayer:";
        JLabel statusLabel = new JLabel();
        if(chessboardComponent.getGameController().getCurrentPlayer()==PlayerColor.BLUE){
            a+="BLUE";
            statusLabel.setForeground(Color.BLUE);
        }
        if(chessboardComponent.getGameController().getCurrentPlayer()==PlayerColor.RED){
            a+="RED";
            statusLabel.setForeground(Color.RED);
        }
        statusLabel.setText(a);
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        return statusLabel;
    }

    public JLabel addCurrentTurns() {
        String a="CurrentTurn:";
        JLabel statusLabel = new JLabel();
        a+=chessboardComponent.getGameController().turn;
        statusLabel.setText(a);
        statusLabel.setLocation(HEIGTH, HEIGTH / 7);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        return statusLabel;
    }
    public JLabel addCurrentTimer() {
        JLabel timer = chessboardComponent.getGameController().setTimer();

        timer.setLocation(HEIGTH, HEIGTH / 6);
        timer.setSize(300, 60);
        add(timer);
        return timer;
    }
    public JLabel addBackGround(){
        String currentDir = System.getProperty("user.dir");
        String ImagePath=null;
            ImagePath = currentDir + "\\resource\\bk1.png";
        // 背景图片
        ImageIcon background = new ImageIcon(ImagePath);
        // 把背景图片显示在一个标签里面
        background.setImage(background.getImage().getScaledInstance(1100, 810,Image.SCALE_DEFAULT ));
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel();
        label.setSize(1100,810);
        label.setIcon(background);
        label.setBounds(0,0,1100,810);
        label.setPreferredSize(new Dimension(1100, 810));
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        ((JPanel)this.getContentPane()).setOpaque(false); //设置透明
        return label;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

//    private void addHelloButton() {
//        JButton button = new JButton("Show Hello Here");
//        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
//        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//    }
    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH, HEIGTH / 10 +220);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e ->
            chessboardComponent.getGameController().Restart()
        );

    }
    private void addChangeBk() {
        JButton button = new JButton("更换主题");
        button.setLocation(HEIGTH-150, HEIGTH / 10 + 620);
        button.setSize(200, 60);
        button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        add(button);
        button.addActionListener(e ->
        {   remove(backlabel);
            this.repaint();
//            getChessboardComponent().getGameController().changeBg();
        });
    }
    private void addReplayButton() {
        JButton button = new JButton("棋局回放");
        button.setLocation(HEIGTH, HEIGTH / 10 +620);
        button.setSize(200, 60);
        button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        add(button);
        button.addActionListener(e ->
        {
            try {
                File selectedFile = chessboardComponent.getGameController().chooseFile();
                if (chessboardComponent.getGameController().CheckError(selectedFile)){
                    chessboardComponent.getGameController().ChessGameReplay(selectedFile);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } );

    }

    private void addAImoveButton() {
        JButton button = new JButton("AImove");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e ->
                {
                    try {
                        chessboardComponent.getGameController().AIMove();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    private void addUndoButton () {
        JButton button = new JButton("悔棋");
        button.setLocation(HEIGTH, HEIGTH / 10 + 520);
        button.setSize(200, 60);
        button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            try {
                chessboardComponent.getGameController().UnDo();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

    }


        private void addSaveButton () {
            JButton button = new JButton("Save");
            button.setLocation(HEIGTH, HEIGTH / 10 + 120);
            button.setSize(200, 60);
            button.setFont(new Font("Rockwell", Font.BOLD, 20));
            add(button);
            button.addActionListener(e -> chessboardComponent.getGameController().save());

        }
        private void addLoadButton () {
            JButton button = new JButton("Load");
            button.setLocation(HEIGTH, HEIGTH / 10 + 320);
            button.setSize(200, 60);
            button.setFont(new Font("Rockwell", Font.BOLD, 20));
            add(button);
            button.addActionListener(e -> {
                try {
                    File selectedFile = chessboardComponent.getGameController().chooseFile();
                    if (chessboardComponent.getGameController().CheckError(selectedFile)){
                        chessboardComponent.getGameController().load(selectedFile);
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });

        }


//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


    }
