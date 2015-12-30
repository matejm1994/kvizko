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

        width=tempX;
        height=tempY;

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
            score++;
            startTime=System.nanoTime();
        }
    }

    public void draw(Canvas canvas){
        //draw player object
        canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void setUp(boolean b){up = b;}
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetScore(){score = 0;}


    //Animacion player
    /*
    public Player(Bitmap res, int w, int h, int numFrames) {
        animation=new Animation();
        x=100;
        y=GamePanel.HEIGHT/2;
        dy=0;
        score=0;
        height=h;

        //animation
        Bitmap[] image =new Bitmap[numFrames];
        spritesheet= res;
        for (int i=0; i<image.length; i++){
            image[i]= Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }


        animation.setFrames(image);
        animation.setDelay(10);
        startTime=System.nanoTime();
    }



    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            score++;
            startTime=System.nanoTime();
        }
        animation.update();

        if(up){
            dy=(int)(dya-=1.1);
        }else{
            dy=(int)(dya+=1.1);
        }

        if (dy>14)
            dy=14;

        if (dy<-14)
            dy=-14;

        y+=dy*2;
        dy=0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x,y,null);
    }
    */




}
