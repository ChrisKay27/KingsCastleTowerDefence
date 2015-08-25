package com.kingscastle.nuzi.towerdefence.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.TextView;

import com.kingscastle.nuzi.towerdefence.framework.GameTime;
import com.kingscastle.nuzi.towerdefence.framework.Graphics;
import com.kingscastle.nuzi.towerdefence.framework.Rpg;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;




public class InfoMessage
{
	private static InfoMessage infoMessage;

	public static final String TOO_POOR = "Sorry, you cannot afford this.";

	private Message message;

	private long stopDisplayingAt;

	//private final Paint whiteCenter;

	private final ArrayList<Message> messageQueue = new ArrayList<Message>();

	private TextView tv;


	private final Activity activity;

	private InfoMessage()
	{
		activity = Rpg.getGame().getActivity();
		activity.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				tv = new TextView( activity );
				float[] direction = new float[]{ 0.0f, -1.0f, 0.7f};
				EmbossMaskFilter emf = new EmbossMaskFilter(direction, 0.6f , 20f, 2f);
				tv.setText(""); tv.getPaint().setMaskFilter( emf ); tv.setLayerType( View.LAYER_TYPE_SOFTWARE, null);

			}
		});
	}


	public static InfoMessage getInstance()
	{
		if( infoMessage == null )
			infoMessage = new InfoMessage();

		return infoMessage;
	}



	private final Runnable showInfoView = new Runnable(){
		@Override
		public void run() {
			tv.setVisibility(View.VISIBLE);
		};
	};

	private final Runnable hideInfoView = new Runnable(){
		@Override
		public void run() {
			tv.setVisibility(View.INVISIBLE);
		};
	};


	public void runOnUIThread()
	{
		if( stopDisplayingAt < GameTime.getTime() ){
			if( !messageQueue.isEmpty() )
			{
				message = messageQueue.remove( 0 );
				if( message != null ){
					stopDisplayingAt = message.forHowLong + GameTime.getTime();
					tv.setText( message.message );
					activity.runOnUiThread( showInfoView );
				}
			}
			else
			{
				message = null ;
				tv.setText( "" );
				stopDisplayingAt = Long.MAX_VALUE;
				activity.runOnUiThread( hideInfoView );
			}
		}
	}




	public String getMessage()
	{
		if( message != null )
		{
			return message.message;
		}
		return null;
	}

	public void setMessage( Message message )
	{
		this.message = message;
		stopDisplayingAt = GameTime.getTime() + message.forHowLong;
	}

	public void setMessage( String message , int forHowLong )
	{
		this.message = new Message( message , forHowLong );
		stopDisplayingAt = GameTime.getTime() + forHowLong;
	}

	public void addMessage(String message, int forHowLong )
	{
		messageQueue.add( new Message( message , forHowLong ) );
	}

	public void addMessage( Message message )
	{
		if( message == null )
		{
			throw new IllegalArgumentException(" message == null ");
		}

		messageQueue.add( message );

	}


	public void clearMessages()
	{
		try
		{
			message = null;
			messageQueue.clear();
			stopDisplayingAt = Long.MAX_VALUE;
		}
		catch( ConcurrentModificationException e)
		{

		}
	}



	public static class Message
	{
		final static int yLoc = (Rpg.getHeight()*3)/4 ;

		final String message;
		int forHowLong = 2000;
		static final Paint whiteCenter = Palette.getPaint( Align.CENTER , Color.WHITE , Rpg.getTextSize() );
		final Paint paint;


		public Message( String message2 , int forHowLong )
		{
			message = message2;
			this.forHowLong = forHowLong;
			paint = whiteCenter;
		}

		public Message( String message2 , int forHowLong , Paint paint )
		{
			message = message2;
			this.forHowLong = forHowLong;
			this.paint = paint;
		}


		public void paint( Graphics g )
		{
			if( message != null && paint != null)
			{
				g.drawString( message , g.getWidthDiv2() , yLoc , paint );
			}
		}

	}





















}
