package com.betox.mygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Betox on 26-Nov-15.
 */
public class Blokade extends GameObject {

    private int dx;
    private Bitmap sprite;
    private static final int WIDTHBlokade = 1440;
    private static final int HEIGHTBlokade = 810;


    public Blokade(Bitmap res, int row){

        dx=GamePanel.MOVESPEED;


        final float scaleFactorX = (GamePanel.CanvasWidth / (WIDTHBlokade * 1.0f));
        final float scaleFactorY = (GamePanel.CanvasHeight/(HEIGHTBlokade * 1.0f));

        int tempX=(int)(160*scaleFactorX);
        int tempY=(int)(90*scaleFactorY);

        width=tempX;
        height=tempY;

        if(row==1){
            //1 row
            super.x=(int)((GamePanel.CanvasWidth/4)+(tempX/2));

        }

        else if(row==2) {
            //2 row
            super.x = (GamePanel.CanvasWidth / 2) - (tempX / 2);

        }
        else if(row==3) {
            //3 row
            super.x = (int) (GamePanel.CanvasWidth / 5) * 3;

        }

        //scale image
        sprite=Bitmap.createScaledBitmap(res, tempX, tempY, false);








    }

    public void update(){
        super.y+=GamePanel.MOVESPEED;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(sprite, x, y, null);
    }


}
