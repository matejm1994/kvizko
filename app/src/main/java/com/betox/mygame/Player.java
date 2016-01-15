package com.betox.mygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.betox.mygame.Animation;

/**
 * Created by Betox on 20-Nov-15.
 */
public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation;
    private long startTime;


    //Width and height of image
    private static final int WIDTHPlayer = 810;
    private static final int HEIGHTPlayer = 1440;


    public Player(Bitmap res){
        spritesheet=res;

        dy=0;
        score=0;

        final float scaleFactorX = (GamePanel.CanvasWidth / (WIDTHPlayer * 1.0f));
        final float scaleFactorY = (GamePanel.CanvasHeight/(HEIGHTPlayer * 1.0f));

        int tempX=(int)(90*scaleFactorX);
        int tempY=(int)(160*scaleFactorY);

        width=tempY;
        height=tempX;

        //landscape mode
        x=(GamePanel.CanvasWidth/2)-(tempY/2);
        y=GamePanel.CanvasHeight-100;


        //scale image
        spritesheet=Bitmap.createScaledBitmap(spritesheet, tempY, tempX, false);



    }


    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            //increase score by 1 every 100ms
            Info.tocke++;
            startTime=System.nanoTime();
        }

        x=(int)(x+(dx));
        y=(int)(y+(dy));

        dx=(int)(dx*0.99);
        dy=(int)(dy*0.99);

        if(x<225){
            dx=0;
            x=224;
        }else if(x>520){
            dx=0;
            x=524;
        }
    }

    @Override
    public void draw(Canvas canvas){
        //draw player object
        canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void setUp(boolean b){up = b;}
    public int getScore(){return Info.tocke/10;}
    public void setScore(int n){
        score+=n;
    }
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetScore(){score = 0;}





}