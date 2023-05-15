package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements Serializable {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    public static JLabel current_currentPlayer_JLabel;
    public static JLabel current_turn_JLabel;
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
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
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

    private void addAImoveButton() {
        JButton button = new JButton("AImove");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e ->
                chessboardComponent.getGameController().RedEasyAImove()
        );
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
            button.addActionListener(e -> chessboardComponent.getGameController().load());

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
