package com.nick.aponggame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StateHandler2P extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
	private int maxWidth;
	private int maxHeight;
	private SurfaceHolder holder;
	private int scoreP1;
	private int scoreP2;
	private int winningScore;
	private ArrayList<Ball> balls;
	private Paddle paddle;
	boolean isPlayer1;
	
	
	public StateHandler2P(Context context)
	{
		super(context);
		
		maxWidth=getWidth();
		maxHeight=getHeight();
		balls=new ArrayList<Ball>();
		paddle=new Paddle(maxWidth/2 - (75/*length*/ / 2), 400, 75, 10);//temporary values, I havn't mathed it out yet;
		holder=getHolder();
		holder.addCallback(this);
		scoreP1=0;
		scoreP2=0;
		winningScore=10; //MAKE CHOOSABLE LATER
	}
	
	public void update()// later change to return int reflecting who scored, if anyone
	{
		for(Ball b : ballList)
		{
			b.move();
		
			//DEATH!
			if(b.getY() > maxHeight || b.getY() < 0)
			{
				b.setX(100);//Collisions with the sides
			}
		
			if(b.getX() > maxWidth || b.getX() < 0)
			{
				b.reverseX(); 	//Collisions with the bats 
			}
		
			if(		b.getX() > paddle.getX() &&
					b.getX() < paddle.getX()+paddle.getLength() && 
					b.getY() > paddle.getY())
			{
				b.reverseY();
			}
		}
	}
	
	public void draw(Canvas canvas, Paint paint)
	{
		//Clear the screen
		canvas.drawRGB(20, 20, 20);
	
		//set the color
		paint.setARGB(200, 0, 200, 0);
	
		//draw the ball
		for(Ball b : balls)
		{
			canvas.drawRect(new Rect(	b.getX(), 				b.getY(),
										b.getX() + b.getSize(), b.getY() + b.getSize()),
										paint);
		}
		
		//draw the bats
		canvas.drawRect(new Rect(	paddle.getX(), paddle.getY(), 
									paddle.getX() + paddle.getLength(), 
									paddle.getY() + paddle.getHeight()), 
									paint); //bottom bat

	}

	public void run()
	{
		while(scoreP1<winningScore && scoreP2<winningScore)
		{
			Canvas canvas = holder.lockCanvas();
			
			//HANDLE STUFF SENT BY NETWORK	
			update();
 	 		draw(canvas, new Paint());
 	 		holder.unlockCanvasAndPost(canvas);
 	 	}	
		//HANDLE WINNER HERE	
		return;
	}
	
	
	//Implemented as part of the SurfaceHolder.Callback interface
	//@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//Mandatory, just swallowing it for this example

	}

    //Implemented as part of the SurfaceHolder.Callback interface
	//@Override
	public void surfaceCreated(SurfaceHolder holder) {
		handler.start();
	}

    //Implemented as part of the SurfaceHolder.Callback interface
	//@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        handler.stop();
	}
}
