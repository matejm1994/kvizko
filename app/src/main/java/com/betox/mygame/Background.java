package com.betox.mygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Betox on 20-Nov-15.
 */
public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res){
        image=res;
        //sped for moving background image
        dx=GamePanel.MOVESPEED;
    }



    public void update()
    {
        //move background image down
        y+=dx;
        if(y>GamePanel.HEIGHT){
            y=0;
        }
    }

    public void draw(Canvas canvas)
    {
        //draw first background immage
        canvas.drawBitmap(image, x , y ,null);
        if(y>0)
        {
            //draw second background image
            canvas.drawBitmap(image,x ,y-GamePanel.HEIGHT,null);
        }

    }
}
