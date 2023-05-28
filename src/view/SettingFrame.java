package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        addBackGround1();

    }
    private JLabel addBackGround1(){
        String currentDir = System.getProperty("user.dir");
        String ImagePath=null;
        ImagePath = currentDir + "\\resource\\bk10.jpg";
        // 背景图片
        ImageIcon background = new ImageIcon(ImagePath);
        // 把背景图片显示在一个标签里面
        background.setImage(background.getImage().getScaledInstance(530, 700,Image.SCALE_DEFAULT ));
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel();
        label.setSize(530,700);
        label.setIcon(background);
        label.setBounds(0,0,530,700);
        label.setPreferredSize(new Dimension(530, 700));
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        ((JPanel)this.getContentPane()).setOpaque(false); //设置透明
        return label;
    }
    public void addmidSettingButton(){
        JButton beginButton = new JButton("AI难度：中等");

        beginButton.setLocation(BUTTON_WIDTH-30,BUTTON_HEIGHT / 10 +320);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);
        beginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 当鼠标进入按钮时，将按钮的背景色设置为黄色
                e.getComponent().setBackground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 当鼠标离开按钮时，将按钮的背景色设置为白色
                e.getComponent().setBackground(Color.WHITE);
            }
        });
        beginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 当鼠标进入按钮时，将按钮的背景色设置为黄色
                e.getComponent().setBackground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 当鼠标离开按钮时，将按钮的背景色设置为白色
                e.getComponent().setBackground(Color.WHITE);
            }
        });
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

        beginButton.setLocation(BUTTON_WIDTH-30,BUTTON_HEIGHT / 10 +220);
        beginButton.setSize(200, 50);
        beginButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(beginButton);
        beginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 当鼠标进入按钮时，将按钮的背景色设置为黄色
                e.getComponent().setBackground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 当鼠标离开按钮时，将按钮的背景色设置为白色
                e.getComponent().setBackground(Color.WHITE);
            }
        });
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
                        System.out.println(ChessGameFrame.getChessboardComponent().getGameController().gettingAId());
                        ChessGameFrame.setVisible(true);
                        this.dispose();
                    });
                }

        );
    }






}


