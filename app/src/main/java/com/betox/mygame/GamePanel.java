package com.betox.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Betox on 19-Nov-15.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    //background image size
    public static final int WIDTH = 768;
    public static final int HEIGHT = 768;


    public static int CanvasWidth;
    public static int CanvasHeight;
    public static final int MOVESPEED=5;
    private GameLoop thread;
    private Background bg;
    private Player player;
    private Blokade blokade1, blokade2, blokade3;
    private int time=0;
    private ArrayList<GameObject> items;
    int lives;


    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new GameLoop(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter=0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        lives=3;
        items=new ArrayList<GameObject>();

        CanvasHeight=getHeight();
        CanvasWidth=getWidth();

        //create background
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.road));

        //create game objects
        player=new Player(BitmapFactory.decodeResource(getResources(), R.drawable.car));

        //create 3 blokades: 1 for each row
        blokade1=new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 1);
        blokade2=new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 2);
        blokade3=new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 3);

        //add object to array for collision detection
        items.add(player);
        items.add(blokade1);
        items.add(blokade2);
        items.add(blokade3);

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            float tempX=event.getX();
            if(tempX>player.getX())
                player.setX(player.getX()+25);
            else
                player.setX(player.getX()-25);
        }


        return super.onTouchEvent(event);
    }



    public void update()
    {
        bg.update();
        player.update();
        blokade1.update();
        blokade2.update();
        blokade3.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        //print score every 100 frames
        time++;
        if(time==100){
            System.out.println("Score: "+player.getScore());
            time=0;
        }


        final float scaleFactorX = (CanvasWidth/(WIDTH*1.0f));
        final float scaleFactorY = (CanvasHeight/(HEIGHT*1.0f));
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            canvas.restoreToCount(savedState);

            player.draw(canvas);


            //blokade1.draw(canvas);
            blokade2.draw(canvas);
            //blokade3.draw(canvas);

            //collision
            for (GameObject item:items){
                if(item !=player){
                    if(player.getRectangle().intersect(item.getRectangle())){

                        //Insert code HERE


                        System.out.println("Hit");
                    }else{}

                }
            }


            //blokade reach end of bottom screen
            //we dont need to draw it
            /*
            if(blokade1.getY()>CanvasHeight)
            {
                System.out.println("Outttttttttttttttttttttttt");
            }else{
                System.out.println(blokade1.getY()+" "+CanvasHeight);
                blokade1.draw(canvas);
            }
            */



        }

    }

}
