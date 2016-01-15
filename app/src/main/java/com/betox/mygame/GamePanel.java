package com.betox.mygame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Betox on 19-Nov-15.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //background image size
    public static final int WIDTH = 768;
    public static final int HEIGHT = 768;


    public static int CanvasWidth;
    public static int CanvasHeight;
    public static final int MOVESPEED=5;
    private GameLoop thread;
    private Background bg;
    private Player player;
    private Blokade blokada1, blokada2, blokada3;
    private int time=0;
    private ArrayList<GameObject> items;
//    int lives;
    private int FullTime=0;
    private Context ctx;
    private Activity activity;
    Random r;

    //If player hit something
    boolean collTime = false;


    public GamePanel(Context context) {
        super(context);
        ctx = context;
        activity = (Activity)ctx;


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new GameLoop(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        r=new Random();


        items=new ArrayList<GameObject>();

        CanvasHeight=getHeight();
        CanvasWidth=getWidth();

        //create background
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.road));

        //create game objects
        player=new Player(BitmapFactory.decodeResource(getResources(), R.drawable.car));
        items.add(player);

        //create 3 blokades: 1 for each row
        blokada1=new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 1);
        blokada2=new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 2);
        blokada3=new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 3);




        //we can safely start the game loop
        thread.setRunning(true);
        if (thread.getState() == Thread.State.NEW)
        {
            thread.start();
        }else{
            System.exit(0);
        }
       // thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            float tempX=event.getX();
            if(tempX>player.getX()){
                player.setDx(10);
                //player.setX(player.getX()+25);
            }
            else if(tempX<player.getX()){
                player.setDx(-10);
                //player.setX(player.getX()-25);
            }
        }
        return super.onTouchEvent(event);
    }


    public void update() {
        if(FullTime%75==0){
            int x=r.nextInt(3);
            int y=r.nextInt(3);
            if(x!=y){
                items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), x+1));
                items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), y+1));
            }else{
                if(x==0){
                    items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), x+1));
                    items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 3));
                }if(x==1){
                    items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), x+1));
                    items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 1));
                }else{
                    items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), x+1));
                    items.add(new Blokade(BitmapFactory.decodeResource(getResources(), R.drawable.blokade), 1));
                }

            }

        }

        for(GameObject item: items){
            item.update();
        }

        bg.update();



    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //print score every 100 frames
        String s="";

        time++;
        FullTime++;
        if (time == 100) {

            System.out.println("Score: " + player.getScore());
            System.out.println(FullTime);
            time = 0;
        }

        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);




        final float scaleFactorX = (CanvasWidth / (WIDTH * 1.0f));
        final float scaleFactorY = (CanvasHeight / (HEIGHT * 1.0f));
        if(Info.life>0){
            if (canvas != null) {
                final int savedState = canvas.save();
                canvas.scale(scaleFactorX, scaleFactorY);
                bg.draw(canvas);
                canvas.restoreToCount(savedState);

                s="Score: "+player.getScore();
                canvas.drawText(s, 50, 50, paint);
                canvas.drawText("lives: "+Info.life,50, 450, paint);


                //collision
                for (GameObject item : items) {
                    if (item != player) {
                        if (player.getRectangle().intersect(item.getRectangle())) {


                            thread.setRunning(false);
                            thread.interrupt();
                            Intent i = new Intent(ctx, QuestionActivity.class);
                            ctx.startActivity(i);


                        }

                    }
                }

                for(GameObject item: items){
                    //blokade reach end of bottom screen
                    //we dont need to draw it
                    if(item.getY()>CanvasHeight){

                    }else{
                        item.draw(canvas);
                    }
                }
            }
        }else{
            //lives==0 close app

        }


    }

}





