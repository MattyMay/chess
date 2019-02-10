package chess;

import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) {
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
        ChessJFrame cjf = new ChessJFrame();
        cjf.setVisible(true);
        ChessGame cg = new ChessGame();
        // for (int i = 0; i < 8; i++) {
        // for (int j = 0; j < 8; j++) {
        // System.out.print(i + " " + j + " " + cg.board[i][j] + "==");
        // }
        // System.out.println();
        //
        // }

        for (int i = 0; i < 8; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 8; j++) {
                if (cg.board[i][j] == null) {
                    System.out.print("    |");
                } else {
                    System.out.print("" + cg.board[i][j] + " |");
                }
            }
            System.out.println();
            System.out.println("  -----------------------------------------");
        }
        System.out.println("    0    1    2    3    4    5    6    7");
        System.out.println("activePlayer: " + cg.getActivePlayer() + " Victory: " + cg.getVictory()
                + "Draw: " + cg.getDraw());

        // System.out.println(cg.board[3][0].getClass() ==
        // cg.board[1][0].getClass());
        // BoardSpace one = new BoardSpace(0, 5);
        // System.out.println(one.row + " " + one.col);
        // System.out.println(cg.getPiece(one));
        Scanner kb = new Scanner(System.in);
        while (true) {
            System.out.println("enter boardspace from and to");
            BoardSpace from = new BoardSpace(kb.nextInt(), kb.nextInt());
            BoardSpace to = new BoardSpace(kb.nextInt(), kb.nextInt());
            long startTime = System.nanoTime();
            System.out.println(cg.movePiece(from, to));
            long endTime = System.nanoTime();
            System.out.println((endTime - startTime) / 1000000);
            System.out.println("    0    1    2    3    4    5    6    7");
            System.out.println("  -----------------------------------------");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + " |");
                for (int j = 0; j < 8; j++) {
                    if (cg.board[i][j] == null) {
                        System.out.print("    |");
                    } else {
                        System.out.print("" + cg.board[i][j] + " |");
                    }
                }
                System.out.println(" " + i);
                System.out.println("  -----------------------------------------");
            }
            System.out.println("    0    1    2    3    4    5    6    7");
            System.out.println("activePlayer: " + cg.getActivePlayer() + " Victory: "
                    + cg.getVictory() + "Draw: " + cg.getDraw());
            if (kb.nextLine().equals("q")) {
                break;
            }
        }
    }

}
