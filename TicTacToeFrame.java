import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame
{
    JPanel mainPnl;
    JPanel btnPnl;

    JOptionPane optPane;

    TicTacToeTile[][] Board = new TicTacToeTile[3][3];

    private static final int ROW = 3;
    private static final int COL = 3;
    private static String[][] board = new String[ROW][COL];

    JButton quitBtn;

    boolean finished = false;
    boolean playing = true;
    String player = "X";
    int moveCount = 0;
    int row = -1;
    int col = -1;
    final int MOVES_TO_WIN = 5;
    final int MOVES_TO_TIE = 7;

    public TicTacToeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(750, 750);
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        quitBtn.setFont(new Font("Serif", Font.PLAIN, 24));

        createBtnPnl();
        initializeBoard();

        mainPnl.add(quitBtn, BorderLayout.SOUTH);
        mainPnl.add(btnPnl, BorderLayout.CENTER);
        add(mainPnl);
        setVisible(true);

    }

        private void createBtnPnl()
        {
            btnPnl = new JPanel();
            btnPnl.setLayout(new GridLayout(3,3));
            btnPnl.setBorder(new TitledBorder(new EtchedBorder(),""));
            for( int r = 0; r < 3; r++)
                for(int c= 0; c < 3; c++)
                {
                    Board[r][c] = new TicTacToeTile(r, c);
                    Board[r][c].setText(" ");
                    Board[r][c].setFont(new Font("Arial", Font.BOLD, 48));
                    btnPnl.add(Board[r][c]);

                    Board[r][c].addActionListener((ActionEvent ae)->
                    {
                        // ae.getSource returns which button got pressed
                        TicTacToeTile tile = (TicTacToeTile) ae.getSource();
                        row = tile.getRow();
                        col = tile.getCol();
                        // get the row and col, use the tictactoetile methods
                        // display the row and col to test the program
                        // is valid move?
                        // once a button is pushed, disable it so that it can't be used for the rest of the game
                        Board[row][col].setEnabled(false);
                        Board[row][col].setBackground(new Color(128, 128, 178));
                        // record the move on the internal board
                        board[row][col] = player;
                        //update the gui, button now shows player
                        Board[row][col].setText(player);
                        //update move counter
                        moveCount++;
                        System.out.println(moveCount);
                        //if enough moves check for win, if there is a win display the win dialog and ask the player if they want to play again
                        if (moveCount >= MOVES_TO_WIN)
                        {
                            if(isWin(player))
                            {
                                disableButtons();
                                int result = JOptionPane.showConfirmDialog(optPane, "Do you want to play again?", "Player: "+ player +" Wins!", JOptionPane.YES_NO_OPTION);
                                if (result == JOptionPane.YES_OPTION)
                                {
                                    initializeBoard();
                                } else if(result == JOptionPane.NO_OPTION)
                                {
                                    System.exit(0);
                                }
                            }
                        }
                        //if enough moves check for tie, if there is a tie display the tie dialog and ask the player if they want to play again
                        if (moveCount >= MOVES_TO_TIE) {
                            if(isTie())
                            {
                                disableButtons();
                                int result = JOptionPane.showConfirmDialog(optPane, "It's a Tie, Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
                                if (result == JOptionPane.YES_OPTION)
                                {
                                    initializeBoard();
                                } else if(result == JOptionPane.NO_OPTION)
                                {
                                    System.exit(0);
                                }
                            }
                        }
                        //toggle the player
                        if(player.equals("X"))
                        {
                            player = "O";
                        }
                        else
                        {
                            player = "X";
                        }
                    });
                }
        }

        private static boolean isWin(String player)
        {
            if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
            {
                return true;
            }

            return false;
        }
        private static boolean isColWin(String player)
        {
            // checks for a col win for specified player
            for(int col=0; col < COL; col++)
            {
                if(board[0][col].equals(player) &&
                        board[1][col].equals(player) &&
                        board[2][col].equals(player))
                {
                    return true;
                }
            }
            return false; // no col win
        }
        private static boolean isRowWin(String player)
        {
            // checks for a row win for the specified player
            for(int row=0; row < ROW; row++)
            {
                if(board[row][0].equals(player) &&
                        board[row][1].equals(player) &&
                        board[row][2].equals(player))
                {
                    return true;
                }
            }
            return false; // no row win
        }
        private static boolean isDiagnalWin(String player)
        {
            // checks for a diagonal win for the specified player

            if(board[0][0].equals(player) &&
                    board[1][1].equals(player) &&
                    board[2][2].equals(player) )
            {
                return true;
            }
            if(board[0][2].equals(player) &&
                    board[1][1].equals(player) &&
                    board[2][0].equals(player) )
            {
                return true;
            }
            return false;
        }

    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals("X") ||
                    board[row][1].equals("X") ||
                    board[row][2].equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].equals("O") ||
                    board[row][1].equals("O") ||
                    board[row][2].equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals("X") ||
                    board[1][col].equals("X") ||
                    board[2][col].equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].equals("O") ||
                    board[1][col].equals("O") ||
                    board[2][col].equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].equals("X") ||
                board[1][1].equals("X") ||
                board[2][2].equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].equals("O") ||
                board[1][1].equals("O") ||
                board[2][2].equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].equals("X") ||
                board[1][1].equals("X") ||
                board[2][0].equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].equals("O") ||
                board[1][1].equals("O") ||
                board[2][0].equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

    public void initializeBoard()
    {
        player = "X";
        playing = true;
        moveCount = 0;
        clearBoard();
    }

    private void clearBoard() {
        // sets all the board elements to a space
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }

        // reset display board as well
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                Board[row][col].setText(" ");
                Board[row][col].setEnabled(true);
                Board[row][col].setBackground(new Color(0, 0, 0));
            }
        }

    }

    private void disableButtons()
    {
        for(int row=0; row < ROW; row++)
        {
            for(int col=0; col < COL; col++)
            {
                Board[row][col].setEnabled(false);
                Board[row][col].setBackground(new Color(0, 0, 0));

            }
        }
    }

    }

