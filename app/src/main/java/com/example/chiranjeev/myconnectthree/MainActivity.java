package com.example.chiranjeev.myconnectthree;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetGame();
    }

    boolean lastWasYellow = false;
    boolean somebodyWon = false;

    int grid[] = new int[10];

    int gridWinIndices[][] = {{1, 2, 3}, {4, 5, 6}, {7, 9, 8}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};

    public boolean isDraw() {

        for (int i = 1; i < grid.length; i++) {

            if (grid[i] == 0) return false;
        }
        return (!isWin());
    }


    public boolean isWin() {

        for (int i = 0; i < gridWinIndices.length; i++) {
            int[] arrWinCondition = gridWinIndices[i];

            if (grid[arrWinCondition[0]] == grid[arrWinCondition[1]] && grid[arrWinCondition[1]] == grid[arrWinCondition[2]] && grid[arrWinCondition[2]] != 0)
                return true;
        }
        return false;

    }

    private void setCoinColor(ImageView coin) {
        if (lastWasYellow) {
            coin.setImageResource(R.drawable.red);
            lastWasYellow = false;
        } else {
            coin.setImageResource(R.drawable.yellow);
            lastWasYellow = true;
        }
    }

    private void performCoinTrickleDownAnimation(ImageView coin) {
        coin.setTranslationY(-1000);
        coin.animate().alpha(1).translationYBy(1000).rotationBy(1800).setDuration(300);

    }

    private void coinTrickleDown(ImageView coin) {

        setCoinColor(coin);
        performCoinTrickleDownAnimation(coin);

    }

    public void buttonClick(View view) {
        resetGame();
    }

    public boolean coinSlotFull(int index) {
        return grid[index] != 0;
    }

    public void OnCoinSlotClick(View view) {
        if (somebodyWon) return;

        ImageView coin = (ImageView) view;
        String index = coin.getTag().toString();

        if (coinSlotFull(Integer.parseInt(index))) return;

        coinTrickleDown(coin);


        markGridIndex(index);
        TextView resultAnnouncement = (TextView) findViewById(R.id.resultsTextBox);
        Button playAgain = (Button) findViewById(R.id.playButton);

        boolean isDraw = isDraw();
        boolean isWin = isWin();

        if (isWin) {

            String winner = lastWasYellow ? "YELLOW" : "RED";

            resultAnnouncement.setText(winner + " WINS");


        } else if (isDraw) {
            resultAnnouncement.setText("DRAW !!");

        }


        if (isWin || isDraw) {
            if (isDraw) resultAnnouncement.setBackgroundColor(Color.GRAY);
            else resultAnnouncement.setBackgroundColor(lastWasYellow ? Color.YELLOW : Color.RED);

            Button resetButton = (Button) findViewById(R.id.resetButton);
            setVisibilityOfButtonsAtStartOrEndOfGame(resultAnnouncement, playAgain, resetButton, false);
            somebodyWon = true;
        }
    }

    private void setVisibilityOfButtonsAtStartOrEndOfGame(TextView resultAnnouncement, Button playAgain, Button reset, boolean whenGameStarts) {

        resultAnnouncement.setVisibility(whenGameStarts ? View.INVISIBLE : View.VISIBLE);
        playAgain.setVisibility(whenGameStarts ? View.INVISIBLE : View.VISIBLE);
        reset.setVisibility(whenGameStarts ? View.VISIBLE : View.INVISIBLE);

    }

    private void setAllCoinsInvisible() {
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.grid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.animate().alpha(0);
        }
    }

    public void resetGame() {

        somebodyWon = false;
        lastWasYellow = false;
        grid = new int[10];

        TextView resultAnnouncement = (TextView) findViewById(R.id.resultsTextBox);
        Button playAgain = (Button) findViewById(R.id.playButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);

        setVisibilityOfButtonsAtStartOrEndOfGame(resultAnnouncement, playAgain, resetButton, true);
        setAllCoinsInvisible();
    }

    private void markGridIndex(String string) {
        int index = Integer.parseInt(string);

        grid[index] = lastWasYellow ? 1 : 2;

    }

    public void onResetButtonClick(View view) {
        resetGame();
    }
}
