package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class BeginGameFrame extends JFrame {//begin the game
    private  int WIDTH=0;
    private  int HEIGHT=0;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 50;
    private JFrame BeginLabel;
    public BeginGameFrame(int WIDTH, int HEIGHT) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addBeginButton();
        addrankingListButton();
        addAIFightButton();
        addSettingtButton();

    }


    private void addBeginButton() {
        JButton beginButton = new JButton("玩家vs玩家");

        beginButton.setLocation(BUTTON_WIDTH,BUTTON_HEIGHT / 10 +220);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);
        beginButton.addActionListener(e ->{
            SwingUtilities.invokeLater(() -> {
                dispose();
                ChessGameFrame ChessGameFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController
                        (ChessGameFrame.getChessboardComponent(),new Chessboard(),ChessGameFrame);
                ChessGameFrame.current_currentPlayer_JLabel = ChessGameFrame.addCurrentPlayers();
                ChessGameFrame.setVisible(true);
                this.dispose();
                     });
                }

        );

    }//begin button on the menu;

    private void addAIFightButton() {
        JButton beginButton = new JButton("玩家vs电脑");

        beginButton.setLocation(BUTTON_WIDTH,BUTTON_HEIGHT / 10 +320);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);

    }




    private void addrankingListButton() {
        JButton beginButton = new JButton("排行榜");

        beginButton.setLocation(BUTTON_WIDTH,BUTTON_HEIGHT / 10 +420);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);

    }

    private void addSettingtButton() {
        JButton beginButton = new JButton("设置");

        beginButton.setLocation(BUTTON_WIDTH,BUTTON_HEIGHT / 10 +520);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);

    }









}
