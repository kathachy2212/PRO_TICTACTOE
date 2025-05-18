import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TicTacToe extends JFrame implements ActionListener {
    // Constants for board size
    private final int BOARD_WIDTH = 500;
    private final int BOARD_HEIGHT = 550; // Includes 50px for the text panel on top

    // Main frame for the game
    private JFrame frame = new JFrame("Let's Play Tic-Tac-Toe");

    // UI components
    private JLabel textLabel = new JLabel(); // Label to display messages (turns, winner, etc.)
    private JPanel textPanel = new JPanel(); // Panel for the label
    private JPanel boardPanel = new JPanel(); // Panel for the Tic-Tac-Toe grid

    // 3x3 Button Grid for the game board
    private JButton[][] board = new JButton[3][3];

    // Player symbols
    private final String PLAYER_X = "X";
    private final String PLAYER_O = "O";
    private String currentPlayer = PLAYER_X; // X starts first

    // Game state tracking
    private boolean gameOver = false;
    private int turns = 0; // Counter for tracking moves

    // Constructor to initialize the game
    public TicTacToe() {
        setupFrame();
        setupTextPanel();
        setupBoardPanel();
        initializeBoard();
    }

    // Method to set up the main frame
    private void setupFrame() {
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setLocationRelativeTo(null); // Centers the window on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    // Method to set up the text panel at the top
    private void setupTextPanel() {
        textLabel.setBackground(Color.YELLOW);
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding around the text
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
    }

    // Method to set up the board panel (3x3 grid)
    private void setupBoardPanel() {
        boardPanel.setLayout(new GridLayout(3, 3)); // 3x3 grid layout
        boardPanel.setBackground(Color.YELLOW);
        frame.add(boardPanel);
    }

    // Initialize the board with buttons
    private void initializeBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                // Button styling
                tile.setBackground(Color.YELLOW);
                tile.setForeground(Color.BLACK);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.setBorder(new LineBorder(Color.BLACK, 1)); // Black border around tiles

                // Add action listener to handle clicks
                tile.addActionListener(this);
            }
        }
    }

    // Handles button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return; // Ignore clicks if the game is over

        JButton tile = (JButton) e.getSource();

        // Check if the tile is empty before placing a move
        if (tile.getText().isEmpty()) {
            tile.setText(currentPlayer);
            turns++; // Increment move counter
            checkWinner(); // Check if the game is won

            // Switch turns if game isn't over
            if (!gameOver) {
                currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
                textLabel.setText(currentPlayer + "'s turn.");
            }
        }
    }

    // Method to check for a winner or a draw
    private void checkWinner() {
        // Check rows for a win
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                highlightWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        // Check columns for a win
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                highlightWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        // Check main diagonal for a win
        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            highlightWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        // Check anti-diagonal for a win
        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            highlightWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        // Check for a draw
        if (turns == 9) {
            highlightDraw();
        }
    }

    // Method to highlight winning tiles
    private void highlightWinner(JButton tile1, JButton tile2, JButton tile3) {
        tile1.setBackground(Color.GREEN);
        tile2.setBackground(Color.GREEN);
        tile3.setBackground(Color.GREEN);

        textLabel.setBackground(Color.GREEN);
        textLabel.setForeground(Color.WHITE);
        textLabel.setText("Congrats " + currentPlayer + " is the winner! :) ");

        gameOver = true;
    }

    // Method to highlight all tiles in case of a draw
    private void highlightDraw() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setBackground(Color.RED);
                board[r][c].setForeground(Color.WHITE);
            }
        }

        textLabel.setBackground(Color.RED);
        textLabel.setForeground(Color.WHITE);
        textLabel.setText("It's a Draw! :( )");

        gameOver = true;
    }

    
}
