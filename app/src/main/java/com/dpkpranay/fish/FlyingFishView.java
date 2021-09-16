package com.dpkpranay.fish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private Bitmap fish[]=new Bitmap[2];
    private Bitmap backImage;
    private Paint scorePoint=new Paint();
    private Bitmap life[]=new Bitmap[2];
    private int fishx=20;
    private int fishy;
    private int fishSpeed;
    private int canvaswidth;
    private int canvasheight;
    private boolean touch=false;

    private int yellowX,yellowY,yellowSpeed=07;
    private Paint yellowPaint=new Paint();

    private int greenX,greenY,greenSpeed=15;
    private Paint greenPaint=new Paint();

    private int redX,redY,redSpeed=20;
    private Paint redPaint=new Paint();
    private int score,lifeCounterOfFish;

    private MediaPlayer mp;
    private MediaPlayer mp2;


    public FlyingFishView(Context context) {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        backImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);
        scorePoint.setColor(Color.WHITE);
        scorePoint.setTextSize(40);
        scorePoint.setTypeface(Typeface.DEFAULT);
        scorePoint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishy=550;
        score=0;
        lifeCounterOfFish=3;
        mp=MediaPlayer.create(getContext(),R.raw.ahu);
        mp2=MediaPlayer.create(getContext(),R.raw.woopie);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvaswidth=canvas.getWidth();
        canvasheight=canvas.getHeight();

        canvas.drawBitmap(backImage,0,0,null);

        int minFisY=fish[0].getHeight();
        int maxFishY=canvasheight-fish[0].getHeight()*3;
        fishy=fishy+fishSpeed;
        if (fishy<minFisY){
            fishy=minFisY;
        }
        if (fishy>maxFishY){
            fishy=maxFishY;
        }
        fishSpeed=fishSpeed+2;
        if (touch){
         canvas.drawBitmap(fish[1],fishx,fishy,null);
         touch=false;

        }
        else{
            canvas.drawBitmap(fish[0],fishx,fishy,null);
        }


        yellowX=yellowX-yellowSpeed;

        if (hitBall(yellowX,yellowY)){
            mp2.start();
            score=score+10;
            yellowX=-100;
        }

        if (yellowX<0){
            yellowX=canvaswidth+21;
            yellowY=(int)Math.floor(Math.random()*(maxFishY-minFisY))+minFisY;
        }

        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);

        //yellow ends

        greenX=greenX-greenSpeed;

        if (hitBall(greenX,greenY)){
            mp2.start();
            score=score+20;
            greenX=-100;
        }

        if (greenX<0){
            greenX=canvaswidth+21;
            greenY=(int)Math.floor(Math.random()*(maxFishY-minFisY))+minFisY;
        }

        canvas.drawCircle(greenX,greenY,20,greenPaint);
//green ends


        redX=redX-redSpeed;

        if (hitBall(redX,redY)){
            mp.start();
            Toast.makeText(getContext(),"lose a life",Toast.LENGTH_SHORT).show();
            redX=-100;
            lifeCounterOfFish--;

            if (lifeCounterOfFish==0){
                Toast.makeText(getContext(),"Game Over!",Toast.LENGTH_LONG).show();
                Intent gintent=new Intent(getContext(),GameOverActivity.class);
                gintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gintent.putExtra("score",score);
                getContext().startActivity(gintent);
            }
        }

        if (redX<0){
            redX=canvaswidth+21;
            redY=(int)Math.floor(Math.random()*(maxFishY-minFisY))+minFisY;
        }

        canvas.drawCircle(redX,redY,20,redPaint);
        //red ends

        canvas.drawText("Score:"+score,20,60,scorePoint);

        for (int i=0;i<3;i++){
            int x=(int) (280+life[0].getWidth()*1.5*i);
            int y=30;
            if (i<lifeCounterOfFish){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }
    }

    public boolean hitBall(int x,int y){
        if (fishx<x&&x<(fishx+fish[0].getWidth())  &&  fishy<y&&y<(fishy+fish[0].getHeight())){
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed=-22;
        }
        return true;
    }
}
