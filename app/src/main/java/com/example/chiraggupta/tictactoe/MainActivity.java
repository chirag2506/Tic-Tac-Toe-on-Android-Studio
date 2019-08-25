package com.example.chiraggupta.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    boolean turnOfCross = true;
    private int player1Points;
    private int player2Points;
    private TextView player1TextView;
    private TextView player2TextView;

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        makeMove(clickedButton);
        GameStatus gs = this.getGameStatus();
        if (gs == GameStatus.Incomplete) {
            return;
        }
        declareWinner(gs);
    }

    public static enum GameStatus {
        Incomplete, XWins, OWins, Tie
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1TextView = findViewById(R.id.text_view_p1);
        player2TextView = findViewById(R.id.text_view_p2);

        for(int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                String buttonID = "button_" + row + col;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                buttons[row][col] = findViewById(resID);
                buttons[row][col].setOnClickListener(this);

            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    private void makeMove(Button clickedButton) {
        String btntext = clickedButton.getText().toString();
        if (btntext.length() > 0) {
            return;
        } else {
            if (turnOfCross) {
                clickedButton.setText("X");
            } else {
                clickedButton.setText("O");
            }
            turnOfCross = !turnOfCross;
        }
    }

    private GameStatus getGameStatus() {
        String text1 = "", text2 = "";
        int row, col;

        // test inside rows
        row = 0;
        while (row < 3) {
            col = 0;
            while (col < 2) {
                text1 = buttons[row][col].getText().toString();
                text2 = buttons[row][col + 1].getText().toString();
                if (!text1.equals(text2) || text1.length() == 0) {
                    break;
                }
                col++;
            }
            if (col == 2) {
                if (text2.equals("X")) {
                    return GameStatus.XWins;
                } else {
                    return GameStatus.OWins;
                }
            }
            row++;
        }

        // test inside columns
        col = 0;
        while (col < 3) {
            row = 0;
            while (row < 2) {
                text1 = buttons[row][col].getText().toString();
                text2 = buttons[row + 1][col].getText().toString();
                if (!text1.equals(text2) || text1.length() == 0) {
                    break;
                }
                row++;
            }
            if (row == 2) {
                if (text2.equals("X")) {
                    return GameStatus.XWins;
                } else {
                    return GameStatus.OWins;
                }
            }
            col++;
        }

        // test inside diagonal 1
        row = 0;
        col = 0;
        while (row < 2) {
            text1 = buttons[row][col].getText().toString();
            text2 = buttons[row + 1][col + 1].getText().toString();
            if (!text1.equals(text2) || text1.length() == 0) {
                break;
            }
            row++;
            col++;
            if (row == 2) {
                if (text2.equals("X")) {
                    return GameStatus.XWins;
                } else {
                    return GameStatus.OWins;
                }
            }
        }

        // test inside diagonal 2
        row = 2;
        col = 0;
        while (row > 0) {
            text1 = buttons[row][col].getText().toString();
            text2 = buttons[row - 1][col + 1].getText().toString();
            if (!text1.equals(text2) || text1.length() == 0) {
                break;
            }
            row--;
            col++;
            if (row == 0) {
                if (text2.equals("X")) {
                    return GameStatus.XWins;
                } else {
                    return GameStatus.OWins;
                }
            }
        }

        // Incomplete and tie
        for (row = 0; row < 3; row++) {
            for (col = 0; col < 3; col++) {
                if (buttons[row][col].getText().length() == 0) {
                    return GameStatus.Incomplete;
                }
            }

        }
        return GameStatus.Tie;
    }

    private void declareWinner(GameStatus gs) {
        if (gs == GameStatus.XWins) {
            player1Points++;
            Toast.makeText(this, "Player 1 wins the game", Toast.LENGTH_SHORT).show();
            updatePoints();
            resetBoard();
        } else if (gs == GameStatus.OWins) {
            player2Points++;
            Toast.makeText(this, "Player 2 wins the game", Toast.LENGTH_SHORT).show();
            updatePoints();
            resetBoard();
        } else {
            Toast.makeText(this, "Its a tie", Toast.LENGTH_SHORT).show();
            resetBoard();
        }
    }

    private void updatePoints(){
        player1TextView.setText("Player 1: " + player1Points);
        player2TextView.setText("Player 2: " + player2Points);
    }

    private void resetBoard(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        turnOfCross = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        resetBoard();
    }
}
