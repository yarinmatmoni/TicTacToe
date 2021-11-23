package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    ImageButton[] imageButtonsArray = new ImageButton[9];
    TextView statusTv;
    Button playAgainBtn;
    Boolean firstPlayer;
    int turnCounter=0;
    int whoWin =0;
    LinearLayout line,line2,line3;
    int[] state = {0,0,0,0,0,0,0,0,0};
    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        firstPlayer=true;
        statusTv = findViewById(R.id.game_status_tv);
        playAgainBtn = findViewById(R.id.game_playAgain_btn);
        line= findViewById(R.id.game_winline1_linearL);
        line2= findViewById(R.id.game_winline2_linearL);
        line3= findViewById(R.id.game_winline3_linearL);


        for(int i=0;i<9;i++){
            String buttonID = "game_imgbtn" + (i+1);
            int rID = getResources().getIdentifier(buttonID,"id",getPackageName());
            imageButtonsArray[i] = findViewById(rID);
            imageButtonsArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getBackground() != null)
                        return;
                    String buttonNum = v.getResources().getResourceEntryName(v.getId());
                    int position =Integer.parseInt(buttonID.substring(buttonID.length()-1 , buttonID.length()));

                    if(firstPlayer){
                        state[position-1] = 1;
                        v.setBackgroundResource(R.drawable.player1);
                        statusTv.setText("Player 2 Turn");
                    }
                    else{
                        state[position-1]=2;
                        v.setBackgroundResource(R.drawable.player2);
                        statusTv.setText("Player 1 Turn");
                    }

                    if (checkWinner()){
                        statusTv.setText("The winner is Player " +  whoWin + "!");
                        stopGame();
                    }
                    else if(turnCounter==8){
                        statusTv.setText("Draw! Play again");
                    }
                    turnCounter++;
                    firstPlayer =(!firstPlayer);
                }
            });
        }

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });
    }

    public boolean checkWinner() {
        boolean win = false;
        int winPos =0;
        for(int[] winner : winningPositions){
            if(state[winner[0]]== state[winner[1]]
                    && state[winner[1]]== state[winner[2]]
                    && state[winner[0]] !=0){
                win=true;
                drawLine(winner,winPos);
                if(state[winner[0]]==1)
                    whoWin=1;
                else
                    whoWin=2;
            }
            winPos++;
        }
        return win;
    }

    public void stopGame(){
        for (int i=0;i<9;i++)
            imageButtonsArray[i]. setClickable(false);
    }

    public void playAgain(){
        for(int i=0;i<9;i++) {
            state[i] = 0;
            imageButtonsArray[i].setBackgroundResource(0);
            imageButtonsArray[i].setClickable(true);
        }
        turnCounter=0;
        whoWin =0;
        firstPlayer=true;
        statusTv.setText("Player 1 Turn");
        line3.setBackgroundColor(0);
        line2.setBackgroundColor(0);
        line.setBackgroundColor(0);
    }

    public void drawLine(int[] winner,int winPo){
        if(winPo<=2){
            line2.setBackgroundColor(Color.RED);
            if(winPo==0)
                line2.setY(imageButtonsArray[0].getY()+100);
            if(winPo==1)
                line2.setY(imageButtonsArray[3].getY()+100);
            if(winPo==2)
                line2.setY(imageButtonsArray[6].getY()+100);
        }else if(winPo>2 && winPo<=5){
            line.setBackgroundColor(Color.RED);
            if(winPo==3)
                line.setX(imageButtonsArray[3].getX()+100);
            if(winPo==4)
                line.setX(imageButtonsArray[4].getX()+100);
            if(winPo==5)
                line.setX(imageButtonsArray[5].getX()+100);
        }else{
            line3.setBackgroundColor(Color.RED);
            if(winPo==6)
                line3.setRotation(-45);
            if(winPo==7)
                line3.setRotation(45);
        }
    }
}