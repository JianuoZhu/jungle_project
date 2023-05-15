import controller.GameController;
import model.Chessboard;
import view.BeginGameFrame;
import view.ChessGameFrame;
import javax.swing.*;
import java.awt.*;

public class Main {    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
//            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
//            mainFrame.current_currentPlayer_JLabel = mainFrame.addCurrentPlayers();
            BeginGameFrame mainFrame = new BeginGameFrame(600,800);
            mainFrame.setVisible(true);
        });
    }
}
