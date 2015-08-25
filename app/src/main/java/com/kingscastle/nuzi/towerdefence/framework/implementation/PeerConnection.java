package com.kingscastle.nuzi.towerdefence.framework.implementation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Chris on 4/5/14.
 */
public class PeerConnection {
    public static final String SERVER_ADDRESS2 = "70.77.228.114";
    private static final String SERVER_ADDRESS = "192.168.0.10";
    private static final int SERVER_PORT = 2727;


    private static final String TAG = "PeerConnection";

    private P2PSocket p2pSocket;

    private boolean connectedToPeer = false;

    public boolean findPeer( MessageListener msgListener )
    {
        Socket s = null;
        Socket s2 = null;
        try
        {
            System.out.println("Networking: Creating socket to connect with address:" + SERVER_ADDRESS + " port: " + SERVER_PORT );


            // Creates a socket with a port number >= LOCAL_PORT
            s = new Socket( SERVER_ADDRESS , SERVER_PORT  );
            System.out.println("Networking: Ended up using local port: " + s.getLocalPort() );


            ObjectInputStream ois = new ObjectInputStream( s.getInputStream() );

            System.out.println("Networking: Received response from server");

            // Reads address of peer
            SocketAddress address = (SocketAddress) ois.readObject();


            // Closes connection with server
            s.close();

            s2 =  new Socket();
            s2.bind( s.getLocalSocketAddress() ); // Bind to the same port that was connected to the server
            s2.connect( address , 5000 );		  // Connect to the address the server passed you


            System.out.println("Networking: P2PSocket used local port: " + s2.getLocalPort() );

            // Creates the P2PSocket Wrapper
            p2pSocket = new P2PSocket( s2 , msgListener );
            connectedToPeer = true;
            // Returns color to game
            return true;
        }
        catch( Exception e )
        {
            e.printStackTrace();
            if( s2 != null )
                try {
                    s2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            return false;
        }
        finally
        {
            if( s != null )
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }




    public static Socket createSocket( String address , int port , InetAddress localHost )
    {
        int mPort = 0; // Let the OS choose a free port for me to use
        while( true )
        {
            try {
                return new Socket( address , port , InetAddress.getLocalHost() , mPort );

            } catch (IOException e) {
                System.out.println( "Networking: " + mPort + " port taken");
                ++mPort;
            }
        }
    }




    public boolean sendMessage( String msg )
    {
        if( !connectedToPeer )
           return false;

        //Log.d( TAG, "Networking: sending msg: " + msg);
        if( p2pSocket != null )
            return p2pSocket.sendMessage(msg);
        else
            return false;
    }




    public void close() {
        if( p2pSocket != null ) p2pSocket.close();
    }



}
