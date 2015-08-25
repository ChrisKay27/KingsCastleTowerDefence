package com.kingscastle.nuzi.towerdefence.framework.implementation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

class P2PSocket
{

    private static final String TAG = "P2PSocket";
    private final Socket s;
	private ObjectOutputStream oos;


	public P2PSocket( Socket s2 , final MessageListener msgListener ) throws UnknownHostException, IOException, ClassNotFoundException, URISyntaxException
	{
		s = s2;
		try
		{
			//Log.d( TAG , "Using myPort: " + s.getLocalPort() );

			oos = new ObjectOutputStream( s.getOutputStream() );
            //Log.d( TAG , "P2PSocket:Saved OutputStream");

			final ObjectInputStream ois = new ObjectInputStream( s.getInputStream() );
            //Log.d( TAG , "P2PSocket:Saved InputSteam");


			Thread listener = new Thread(){
				@Override
				public void run()
				{
					while( true )
					{
						String msg;
						try
						{
							msg = (String) ois.readObject();

							while( msg.equals("") )
							{
								Thread.sleep( 100 );
								msg = (String) ois.readObject();
							}
                            //Log.d( TAG , "P2PSocket: received message: " + msg);
							msgListener.receiveMessage( msg );
						}
						catch( SocketException e ){
							System.err.println("Connection to peer lost.");
							break;
						}
						catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
			};
			listener.setDaemon( true );
			listener.start();

		}
		catch( Exception e )
		{
			e.printStackTrace();
			if( s != null ) s.close();
		}
	}




	public boolean sendMessage( String msg )
	{
		if( oos == null ) throw new IllegalStateException( "Connection has not been establised." );
		if( msg == null ) throw new IllegalStateException( "Trying to send null message." );

        //Log.d( TAG , "P2PSocket: sending msg: " + msg );
		try {
			oos.writeObject( msg );
		} catch (IOException e) {
			e.printStackTrace();
			System.exit( 0 );
			return false;
		}

		return true;
	}





	public void close() {
		if( s != null )
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}














}
