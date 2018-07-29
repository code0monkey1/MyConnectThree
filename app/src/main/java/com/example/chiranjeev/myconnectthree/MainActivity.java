package com.example.chiranjeev.myconnectthree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        reset();
    }

    boolean lastWasYellow = false;
    boolean somebodyWon = false;

    int grid[] = new int[10];

    int gridWinIndices[][] = {{1, 2, 3}, {4, 5, 6}, {7, 9, 8}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};

    public boolean isDraw() {

        for (int i = 1; i < grid.length; i++) {

            if (grid[i] == 0) return false;
        }
        return true;
    }


    public boolean isWin() {

        for (int i = 0; i < gridWinIndices.length; i++) {
            int[] arrWinCondition = gridWinIndices[i];

            if (grid[arrWinCondition[0]] == grid[arrWinCondition[1]] && grid[arrWinCondition[1]] == grid[arrWinCondition[2]] && grid[arrWinCondition[2]] != 0)
                return true;
        }
        return false;

    }

    private void coinTrickleDown(ImageView coin) {

        //set image
        if (lastWasYellow) {
            coin.setImageResource(R.drawable.red);
            lastWasYellow = false;
        } else {
            coin.setImageResource(R.drawable.yellow);
            lastWasYellow = true;
        }

        //setAnimation
        coin.setTranslationY(-1000);
        coin.animate().alpha(1).translationYBy(1000).rotationBy(1800).setDuration(300);

    }

    public void buttonClick(View view) {
        reset();
    }

    public void onClick(View view) {
        if (somebodyWon) return;

        ImageView coin = (ImageView) view;

        coinTrickleDown(coin);

        String index = coin.getTag().toString();
        markGridIndex(index);
        TextView resultAnnouncement = (TextView) findViewById(R.id.resultsTextBox);
        Button playAgain = (Button) findViewById(R.id.playButton);

        boolean isDraw=isDraw();
        boolean isWin=isWin();

        if (isWin) {

            String winner = lastWasYellow ? "YELLOW" : "RED";

            resultAnnouncement.setText(winner + " WINS");


        } else if (isDraw) {
            resultAnnouncement.setText("DRAW !!");

        }


        if (isWin || isDraw) {
            resultAnnouncement.setAlpha(1);
            resultAnnouncement.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
            somebodyWon = true;
        }
    }

    private void setAllCoinsInvisible() {
        android.support.v7.widget.GridLayout gridLayout =  findViewById(R.id.grid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.animate().alpha(0);
        }
    }

    public void reset() {

        somebodyWon = false;
        lastWasYellow = false;
        grid = new int[10];

        TextView resultAnnouncement = (TextView) findViewById(R.id.resultsTextBox);
        Button playAgain = (Button) findViewById(R.id.playButton);

        resultAnnouncement.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        setAllCoinsInvisible();
    }

    private void markGridIndex(String string) {
        int index = Integer.parseInt(string);
        if (grid[index] != 0) return;
        grid[index] = lastWasYellow ? 1 : 2;

    }
}
