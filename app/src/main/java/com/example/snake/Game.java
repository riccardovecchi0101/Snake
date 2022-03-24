package com.example.snake;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.*;

public class Game extends AppCompatActivity {
    private String move = ""; //tells where snake is going to move
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** LAYOUT **/
        ConstraintLayout background = (ConstraintLayout) findViewById(R.id.bordo);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        setContentView(R.layout.activity_game);

        /** VIEWS **/
        ImageView apple = (ImageView) findViewById(R.id.apple);
        apple.setVisibility(View.VISIBLE);
        ImageView snake = (ImageView) findViewById(R.id.Serpente);
       // Deque<ImageView> snake_pieces = new ArrayDeque<>((Collection<? extends ImageView>) snake); ImageView Queue doesn't work??
        Button left = (Button)findViewById(R.id.left);
        Button right = (Button)findViewById(R.id.right);
        Button up = (Button)findViewById(R.id.up);
        Button down = (Button)findViewById(R.id.down);

        /** HANDLERS AND RUNNABLES **/

        //*** APPLE HANDLER ************************************************************************************//
        final Handler apples_handler = new Handler();
        final Runnable apples_spawner = new Runnable() {
            /*** spawns an apple in a random position
             *
             */
            @Override
            public void run() {
                Random random = new Random();
                int apple_x, apple_y;
                apple_x = random.nextInt(720);
                apple_y = random.nextInt(1020);
                apple.setX(apple_x);
                apple.setY(apple_y);

            }
        };
        //******************************************************************************************************//


        //*** SNAKE HANDLER ************************************************************************************//
        final Handler handler = new Handler();
        final Runnable movement = new Runnable() {
            /** handles the movement of the snake
             *
             */
            @Override
            public void run() {
                handler.removeCallbacks(this); //remove looped threads
                if ((snake.getX() >= apple.getX() - 50 && snake.getX() <= apple.getX() + 50) && (snake.getY() >= apple.getY() - 50 && snake.getY() <= apple.getY() + 50))
                {
                    /**this stuff needs to be fixed**/
                    /*ImageView snake_piece = new ImageView(Game.this);
                    snake_piece.setLayoutParams(params);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        snake_piece.setForeground(snake.getForeground());
                    }
                    snake_piece.setVisibility(View.VISIBLE);
                    // ImageView last_snake = snake;
                    snake_piece.setX(snake.getX() - 50);
                    snake_piece.setY(snake.getY() - 50);
                   // snake_pieces.push(snake_piece);*/
                    apples_handler.post(apples_spawner); // starts apple hander --> apple eaten.
                }
                switch(move){
                    case "up":
                    {
                        up.setEnabled(false);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        down.setEnabled(true);

                        snake.setY(snake.getY() - 50);
                        if (snake.getY() > 0)handler.postDelayed(this, 250);
                        else {snake.setY(1020);handler.postDelayed(this, 250);}
                        break;
                    }

                    case "down":
                    {
                        up.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        down.setEnabled(false);

                        snake.setY(snake.getY() + 50);
                        if (snake.getY() < 1020)handler.postDelayed(this, 250);
                        else {snake.setY(0);handler.postDelayed(this, 250);}
                        break;
                    }

                    case "left":
                    {
                        up.setEnabled(true);
                        left.setEnabled(false);
                        right.setEnabled(true);
                        down.setEnabled(true);

                        snake.setX(snake.getX() - 50);
                        if (snake.getX() > 0)handler.postDelayed(this, 250);
                        else {snake.setX(720);handler.postDelayed(this, 250);}
                        break;
                    }

                    case "right":
                    {
                        up.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(false);
                        down.setEnabled(true);

                        snake.setX(snake.getX() + 50);
                        if (snake.getX() < 720)handler.postDelayed(this, 250);
                        else {snake.setX(0);handler.postDelayed(this, 250);}
                        break;
                    }
                }

            }
        };
        //******************************************************************************************************//


       //** EVENT HANDLERS *************************************************************************************//
        /** ONCLICK LISTENERS **/
        /** every listener start the snake handler and set the movement string **/


        //-----------------------------------------------------------------------//
        //upbtn
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move = "up";
                handler.post(movement);
            }
        });

        //rightbtn
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move = "right";
                handler.post(movement);
            }
        });

        //leftbtn
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move = "left";
                handler.post(movement);
            }
        });

        //down btn
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move = "down";
                handler.post(movement);
            }
        });
        //-------------------------------------------------------------------------//
        //*******************************************************************************************************//

        apples_handler.post(apples_spawner); //starts snake handler --> enables movement.
    }
}