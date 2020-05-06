package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import gameNet.GameNet_UserInterface;
import gameNet.GamePlayer;

public class MyUserInterface implements GameNet_UserInterface {
    ChessJFrame chessJFrame;
    GamePlayer myGamePlayer;
    MyGameInput myGameInput;
    ChessGame chessGame = null;

    int clientIndex = -1; // 0 for white 1 for black NOTE THIS IS BUSTED
    String activePlayer;
    String checkMessage;
    String dialogue;
    String connections;

    BoardSpace from = null;
    BoardSpace to = null;

    MyUserInterface() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChessJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChessJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChessJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChessJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.chessJFrame = new ChessJFrame();
        this.chessJFrame.setMyUserInterface(this);
        this.chessJFrame.getBoardPanel().setMyUserInterface(this);
    }

    @Override
    public void receivedMessage(Object objectReceived) {
        MyGameOutput myGameOutput = (MyGameOutput) objectReceived;
        chessGame = myGameOutput.myGame.chessGame;

        if (this.clientIndex == -1) this.clientIndex = myGameOutput.clientIndex;

        if (this.chessGame.getVictory() == 'b') {
            this.dialogue = "Checkmate! Black wins.";
            this.chessJFrame.setDialogue(this.dialogue);
            this.chessJFrame.drawBoard(this.chessGame);
            return;
        } else if (this.chessGame.getVictory() == 'w') {
            this.dialogue = "Checkmate! White wins.";
            this.chessJFrame.setDialogue(this.dialogue);
            this.chessJFrame.drawBoard(this.chessGame);
            return;
        }

        if (this.chessGame.getActivePlayer() == 'w') {
            this.activePlayer = "White";
        } else {
            this.activePlayer = "Black";
        }

        if (this.chessGame.getChecked() == 'w') {
            this.checkMessage = "White is in check.";
        } else if (this.chessGame.getChecked() == 'b') {
            this.checkMessage = "Black is in check.";
        } else {
            this.checkMessage = "";
        }
        this.dialogue = this.activePlayer + "'s move. " + this.checkMessage;
        this.chessJFrame.setDialogue(this.dialogue);
        this.chessJFrame.setIPInfo(myGameOutput.hostIPinfo);
        this.chessJFrame.drawBoard(chessGame);
    }

    @Override
    public void startUserInterface(GamePlayer player) {
        myGamePlayer = player;

        myGameInput = new MyGameInput();
        myGameInput.setName(player.getPlayerName());
        myGamePlayer.sendMessage(myGameInput);

        this.chessJFrame.setVisible(true);


    }

}
