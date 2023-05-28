package view;
import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;



public class ChoosebackFrame extends JFrame {
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 50;
    private int WIDTH = 0;
    private int HEIGHT = 0;

    public ChoosebackFrame(int WIDTH, int HEIGHT) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addbackChooseButton();
        addbackChooseButton1();


    }

    public void addbackChooseButton() {
        JButton beginButton = new JButton("选择背景：沙漠");

        beginButton.setLocation(BUTTON_WIDTH, BUTTON_HEIGHT / 10 + 220);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);
        beginButton.addActionListener(e -> {
                    SwingUtilities.invokeLater(() -> {
                        dispose();

                        ChessGameFrame ChessGameFrame = new ChessGameFrame(1100, 810);
                        GameController gameController = new GameController
                                (ChessGameFrame.getChessboardComponent(), new Chessboard(), ChessGameFrame);

                                ChessGameFrame.current_currentPlayer_JLabel = ChessGameFrame.addCurrentPlayers();
                        ChessGameFrame.current_turn_JLabel = ChessGameFrame.addCurrentTurns();
                        ChessGameFrame.setVisible(true);
                        this.dispose();
                    });
                }

        );
    }

    public void addbackChooseButton1() {
        JButton beginButton = new JButton("选择背景：草原");

        beginButton.setLocation(BUTTON_WIDTH, BUTTON_HEIGHT / 10 + 420);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);
        beginButton.addActionListener(e -> {
                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        ChessGameFrame ChessGameFrame = new ChessGameFrame(1100, 810);
                        GameController gameController = new GameController
                                (ChessGameFrame.getChessboardComponent(), new Chessboard(), ChessGameFrame);
                        ChessGameFrame.settingbacknumber(0);
                        ChessGameFrame.current_currentPlayer_JLabel = ChessGameFrame.addCurrentPlayers();
                        ChessGameFrame.current_turn_JLabel = ChessGameFrame.addCurrentTurns();
                        ChessGameFrame.setVisible(true);
                        this.dispose();
                    });
                }

        );
    }
}








