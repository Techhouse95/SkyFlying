package zuzidev.skyflying;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;

/**
 * Created by TechH on 19.3.2018 г..
 */

public class GameView extends View {

    private int canvasWidth;
    private int canvasHeight;

    //Plane
    private Bitmap plane[] = new Bitmap[2];
    private int planeX = 10;
    private int planeY ;
    private int planeSpeed;

    //White ball
    private int whiteX;
    private int whiteY;
    private int whiteSpeed = 15;
    private Paint whitePaint = new Paint();

    //Red ball
    private int redX;
    private int redY;
    private int redSpeed = 25;
    private int baseradius = 30;
    private Paint redPaint = new Paint();

    //Background
    private Bitmap bgImage;

    //Score
    private Paint scorePaint = new Paint();
    private int score;

    //Level
    private Paint levelPaint = new Paint();
    private int level_count;

    //Life
    private Bitmap life[] = new Bitmap[2];
    private int life_count;

    //Stone
    private Bitmap stone[] = new Bitmap[4];

    //Status Check
    private boolean touch_flg = false;

    public GameView(Context context){
        super(context);

        //Plane
        plane[0] = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
        plane[1] = BitmapFactory.decodeResource( getResources(),R.drawable.spaceship_up );

        //Background
        bgImage = BitmapFactory.decodeResource( getResources(),R.drawable.stars );

        //White ball
        whitePaint.setColor( Color.WHITE );
        whitePaint.setAntiAlias( false );

        //Red ball
        redPaint.setColor( Color.RED );
        redPaint.setAntiAlias( false );

        //Score
        scorePaint.setColor( Color.WHITE );
        scorePaint.setTextSize( 48 );
        scorePaint.setTypeface( Typeface.DEFAULT_BOLD );
        scorePaint.setAntiAlias( true );

        //Level
        levelPaint.setColor( Color.WHITE );
        levelPaint.setTextSize( 48 );
        levelPaint.setTypeface( Typeface.DEFAULT_BOLD );
        levelPaint.setTextAlign( Paint.Align.CENTER );
        levelPaint.setAntiAlias( true );

        //Life
       life[0] = BitmapFactory.decodeResource( getResources(),R.drawable.heart );
       life[1] = BitmapFactory.decodeResource( getResources(),R.drawable.heart_g );

       //Stone
        stone[0] = BitmapFactory.decodeResource( getResources(),R.drawable.asteroid1 );
        stone[1] = BitmapFactory.decodeResource( getResources(),R.drawable.asteroid2 );
        stone[2] = BitmapFactory.decodeResource( getResources(),R.drawable.asteroid3 );
        stone[3] = BitmapFactory.decodeResource( getResources(),R.drawable.asteroid4 );



        //FirsPos
       planeY = 750;
       score = 0;
       life_count = 3;
    }

    @Override
    protected void onDraw(Canvas canvas){

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap( bgImage,0,0,null );

        int minPlaneY = plane[0].getHeight();
        int maxPlaneY = canvasHeight - plane[0].getHeight()*2;

        planeY += planeSpeed;
        if (planeY < minPlaneY) {
            planeY = minPlaneY;
        }

        if (planeY > maxPlaneY){
            planeY = maxPlaneY;

            life_count --;
            if (life_count <= 0){

                Context context = null;
                Intent intent = new Intent(context,StartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.GameOverActivity(intent);
            }
        }
        planeSpeed += 2;

        if (touch_flg){
            canvas.drawBitmap( plane[1],planeX, planeY, null );
            touch_flg = false;
        } else {
            canvas.drawBitmap( plane[0],planeX, planeY, null );
        }

        //white
        whiteX -= whiteSpeed;
        if (hitCheck(whiteX, whiteY)){
            score += 20;
            whiteX = -100;
        }
        if (whiteX < 0){
            whiteX = canvasWidth +20;
            whiteY = (int) Math.floor( Math.random()*(maxPlaneY - minPlaneY) ) + minPlaneY;
        }
        canvas.drawCircle( whiteX,whiteY, 20, whitePaint );

        //Red
        redX -= redSpeed;
        if (hitCheck( redX,redY )){
            redX = -100;
            life_count --;
            if (life_count <= 0){

                Context context = null;
                Intent intent = new Intent(context,StartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.GameOverActivity(intent);
            }
        }
        if (redX <0 ){
            redX = canvasWidth + 200;
            redY = (int) Math.floor( Math.random()*(maxPlaneY - minPlaneY) ) + minPlaneY;
        }

        level_count = score / 100;
        redSpeed = level_count * 2;
        baseradius = level_count * 2 + 30;



        canvas.drawCircle( redX,redY, baseradius, redPaint );

        canvas.drawText( "SCORE:"+ score, 20,60, scorePaint );

        canvas.drawText( "Lvl." + level_count, canvasWidth/2, 60,levelPaint);

        for (int i = 0; i <3; i++){
            int x = (int)(760 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < life_count){
                canvas.drawBitmap( life[0], x, y, null );
            }else {
                canvas.drawBitmap( life[1], x, y, null );
            }
        }

    }

    public boolean hitCheck(int x, int y){
        if (planeX < x && x <(planeX + plane[0].getWidth()) && planeY < y && y < (planeY + plane[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touch_flg = true;
            planeSpeed =- 30;
        }
        return true;
    }
}
