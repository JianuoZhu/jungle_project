import view.BeginGameFrame;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String currentDir = System.getProperty("user.dir");
            String musicPath=null;
            musicPath = currentDir + "\\resource\\music.wav";
            try {
                File audioFile = new File(musicPath); // 指定要播放的音乐文件路径
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
//            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
//            mainFrame.current_currentPlayer_JLabel = mainFrame.addCurrentPlayers();
            BeginGameFrame mainFrame = new BeginGameFrame(530,700);
            /*Toolkit toolkit = Toolkit.getDefaultToolkit();
            String _currentDir = System.getProperty("user.dir");
            String ImagePath;
            ImagePath = _currentDir + "\\resource\\cursor.png";
            Image image = toolkit.getImage(ImagePath);
            Cursor c = toolkit.createCustomCursor(image , new Point(mainFrame.getX(),
                    mainFrame.getY()), "img");
            mainFrame.setCursor (c);*/
            mainFrame.setVisible(true);
        });


    }




}
