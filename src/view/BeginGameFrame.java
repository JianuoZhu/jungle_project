package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class BeginGameFrame extends JFrame implements MouseListener {//begin the game
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

        addBackGround();//

        addBeginButton();
        addrankingListButton();
        addAIFightButton();
        addSettingtButton();

    }


    private JLabel addBackGround(){
        String currentDir = System.getProperty("user.dir");
        String ImagePath=null;
        ImagePath = currentDir + "\\resource\\bk.png";
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
                ChessGameFrame.current_turn_JLabel = ChessGameFrame.addCurrentTurns();
                ChessGameFrame.current_timer_JLabel = ChessGameFrame.addCurrentTimer();
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
        beginButton.addActionListener(e ->{
                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        SettingFrame settingFrame=new SettingFrame(500,600);
                        settingFrame.setVisible(true);
                        this.dispose();
                    });
                }

        );

    }
//    {SwingUtilities.invokeLater(() -> {
//                        dispose();
//                        ChessGameFrame ChessGameFrame = new ChessGameFrame(1100, 810);
//                        GameController gameController = new GameController
//                                (ChessGameFrame.getChessboardComponent(),new Chessboard(),ChessGameFrame);
//                        ChessGameFrame.current_currentPlayer_JLabel = ChessGameFrame.addCurrentPlayers();
//                        ChessGameFrame.current_turn_JLabel = ChessGameFrame.addCurrentTurns();
//                        ChessGameFrame.AIColor = PlayerColor.RED;
//                        ChessGameFrame.setVisible(true);
//                        this.dispose();
//                    });
//                }
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


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals("玩家vs电脑")){
            dispose();
            new SettingFrame(400, 200);

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
