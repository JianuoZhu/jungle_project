package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class SettingFrame extends JFrame {
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 50;
    private  int WIDTH=0;
    private  int HEIGHT=0;


    public SettingFrame(int WIDTH, int HEIGHT) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addmidSettingButton();
        addeasySettingButton();

    }
    public void addmidSettingButton(){
        JButton beginButton = new JButton("AI难度：中等");

        beginButton.setLocation(BUTTON_WIDTH,BUTTON_HEIGHT / 10 +320);
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
                        ChessGameFrame.current_turn_JLabel = ChessGameFrame.addCurrentTurns();
                        ChessGameFrame.AIColor = PlayerColor.RED;
                        ChessGameFrame.getChessboardComponent().getGameController().settingAId(0);
                        ChessGameFrame.setVisible(true);
                        this.dispose();
                    });
                }

        );
    }
    public void addeasySettingButton(){
        JButton beginButton = new JButton("AI难度：简单");

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
                        ChessGameFrame.current_turn_JLabel = ChessGameFrame.addCurrentTurns();
                        ChessGameFrame.AIColor = PlayerColor.RED;
                        ChessGameFrame.getChessboardComponent().getGameController().settingAId(1);
                        ChessGameFrame.setVisible(true);
                        this.dispose();
                    });
                }

        );
    }






}


