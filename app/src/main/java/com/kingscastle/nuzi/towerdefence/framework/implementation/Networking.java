package com.kingscastle.nuzi.towerdefence.framework.implementation;


public class Networking
{

	private static final String TAG = "Networking";


	//	public void sendFileToPc( Level level ){
	//        new Thread(){
	//            @Override
	//            public void run()
	//            {
	//                Socket s2 = null;
	//                try {
	//                    s2 = new Socket( "192.168.0.10" , 2727 , InetAddress.getLocalHost() , 0 );
	//                    //Log.d( "NetWorkTesting", "Created socket.");
	//                    final Socket s = s2;
	//
	//                    //System.out.println("ServerSide: Reading object from input stream");
	//
	//                    final OutputStream os = s.getOutputStream();
	//
	//                    new Thread(new Runnable()
	//                    {
	//                        int byteCount = 0;
	//                        @Override
	//                        public void run()
	//                        {
	//                            //Log.d("NetWorkTesting", "writing bytes to output stream");
	//                            long endAt = GameTime.getTime() + 5000;
	//                            while( endAt > GameTime.getTime() )
	//                            {
	//                                try {
	//                                    os.write(1);
	//                                    byteCount += 4;
	//                                } catch (IOException e) {
	//                                    e.printStackTrace();
	//                                }
	//                            }
	//                            //Log.d( "NetWorkTesting", "Wrote" + byteCount + " bytes.");
	//                            //
	//                            //								try {
	//                            //									s.close();
	//                            //								} catch (IOException e) {
	//                            //									e.printStackTrace();
	//                            //								}
	//                        }
	//                    }).act();
	//
	//
	//                } catch (UnknownHostException e) {
	//                    e.printStackTrace();
	//                    if( s2 != null )
	//                        try {
	//                            s2.close();
	//                        } catch (IOException e1) {
	//                            e1.printStackTrace();
	//                        }
	//                } catch (IOException e) {
	//                    e.printStackTrace();
	//                    if( s2 != null )
	//                        try {
	//                            s2.close();
	//                        } catch (IOException e1) {
	//                            e1.printStackTrace();
	//                        }
	//                }
	//            }
	//        }.act();
	//	}
	//
	//
	//
	//    public static boolean sendLevelToPc(  final ManagerManager mm , final LevelSizes levelSize ,
	//                                          final LevelTypes levelType , final ResourceAmounts resourceAmount , final String className , final Level level ) throws IOException, InterruptedException {
	//        if( className != null && className.equals("TutorialLevel") )
	//            return false ;
	//        final Semaphore savin = new Semaphore( 1 );
	//        savin.acquire();
	//
	//        new Thread(new Runnable(){
	//            @Override
	//            public void run() {
	//                //Log.d( TAG , "Saving level " + className );
	//
	//                Socket s2 = null;
	//                ObjectOutputStream oos = null;
	//                try {
	//                    s2 = createSocket( "192.168.0.10" , 2727 );
	//
	//                    //Log.d( "NetWorkTesting", "Created socket. " );
	//                    final Socket s = s2;
	//                    final OutputStream os = s.getOutputStream();
	//                    oos = new ObjectOutputStream(os);
	//                    oos.writeObject("Level1");
	//
	//                    BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(os) );
	//
	//                    String temp = "<SavedGame levelSize=\"" + levelSize + "\" levelType=\"" + levelType + "\" resourceAmount=\"" + resourceAmount + "\" className=\"" + className + "\" >";
	//
	//                    bw.write( temp , 0 , temp.length() );
	//                    bw.newLine();
	//
	//                    level.saveYourSelf( bw );
	//
	//                    temp = "<Teams>";
	//                    bw.write( temp , 0 , temp.length() );
	//                    bw.newLine();
	//
	//                    for ( Team t : mm.getTeamManager().getTeams() )
	//                    {
	//                       //Log.d( TAG , "Saving team:" + t.getTeamName() );
	//                        TeamSaver.saveTeam(t, bw);
	//                       //Log.d( TAG , "Done saving team");
	//                    }
	//
	//                    temp = "</Teams>";
	//                    bw.write( temp , 0 , temp.length() );
	//                    bw.newLine();
	//
	//                    //Log.d( TAG , "Saving gems");
	//                    mm.saveGems( bw );
	//                    //Log.d( TAG , "Done saving gems");
	//
	//                    temp = "</SavedGame>";
	//
	//                    bw.write( temp , 0 , temp.length() );
	//                    bw.newLine();
	//
	//                    bw.close();
	//
	//                } catch (UnknownHostException e) {
	//                    e.printStackTrace();
	//                    if( s2 != null )
	//                        try {
	//                            s2.close();
	//                        } catch (IOException e1) {
	//                            e1.printStackTrace();
	//                        }
	//                } catch (IOException e) {
	//                    e.printStackTrace();
	//                    if( s2 != null )
	//                        try {
	//                            s2.close();
	//                        } catch (IOException e1) {
	//                            e1.printStackTrace();
	//                        }
	//                }
	//                finally{
	//                    savin.release();
	//                }
	//                //Log.d( TAG , "Done saving game.");
	//            }
	//        //printFile( fileIO , savedGame );
	//        }).act();
	//        savin.acquire();
	//        return true;
	//    }
	//
	//    private static final String GET_MY_BASE = "GMB";
	//    public static YourBaseLevel getMyBaseFromServer()
	//    {
	//        //Log.d( TAG , "Dling level");
	//
	//        File tempDir = Rpg.getGame().getFilesDir();
	//        ObjectOutputStream temp_oos = null;
	//        BufferedInputStream bis = null;
	//        InputStream is = null;
	//        OutputStreamWriter osw = null;
	//        Socket s2 = null;
	//
	//        try {
	//            s2 = createSocket( "192.168.0.10" , 2727 );
	//            //Log.d( TAG, "Created socket. " );
	//
	//            Socket s = s2;
	//            OutputStream os = s.getOutputStream();
	//            ObjectOutputStream oos = new ObjectOutputStream( os );
	//            oos.writeObject( GET_MY_BASE );
	//
	//
	//            File tempLevel = File.createTempFile( "tempLevel" , ".kclevel" , tempDir );
	//            osw = new OutputStreamWriter( new FileOutputStream( tempLevel ) , "utf-8" );
	//
	//            //temp_oos = new ObjectOutputStream( new FileOutputStream( tempLevel ) );
	//
	//            is = s.getInputStream();
	//
	//            InputStreamReader isr = new InputStreamReader( is , "utf-8" );
	//
	//           // byte[] bytes = new byte[10000];
	//            char[] chars = new char[10000];
	//
	//            while( true )
	//            {
	//                int bCount = isr.read( chars , 0 , 10000 );
	//               // int bCount = is.read( bytes , 0 , 10000 );
	//                if( bCount < 0 )
	//                    break;
	//                //Log.d( TAG , "Reading " + bCount + " bytes");
	//                osw.write( chars , 0 , bCount );
	//                osw.flush();
	//                //temp_oos.write(bytes, 0, bCount);
	//                //temp_oos.flush();
	//            }
	//
	//            //Log.d( TAG , "Done reading from socket." );
	//            s2.close();
	//            osw.close();
	//            //temp_oos.close();
	//
	//            /*
	//            String tempName = tempLevel.getName();
	//            tempLevel = null;
	//
	//
	//            //Log.d( TAG , "Looking for temp file." );
	//            for( File f : tempDir.listFiles() ){
	//                //Log.d( TAG , "Found temp file:" + f.getName() );
	//                if( f.getName().equals(tempName) ){
	//                    tempLevel = f;
	//                    break;
	//                }
	//            }
	//
	//
	//            if( tempLevel == null ){
	//                //Log.e(TAG, "Did not find temp file... that i just wrote");
	//                return null;
	//            }
	//
	//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempLevel) , "utf-8" ));
	//
	//
	//            while( true ){
	//                String str = br.readLine();
	//
	//                if( str == null )
	//                    break;
	//
	//                //Log.d(TAG, str);
	//            }
	//            br.close();
	//*/
	//            bis = new BufferedInputStream(new FileInputStream(tempLevel));
	//
	//            try {
	//                YourBaseLevel lvl = new YourBaseLevel();
	//                Level level = SavedGameXMLReader.readXML( bis , ManagerManager.getInstance() , lvl );
	//                //Log.d( "NetWorkTesting", "SavedGameXMLReader returned " + level );
	//                return lvl;
	//            } catch (ClassNotFoundException e) {
	//                e.printStackTrace();
	//            } catch (InstantiationException e) {
	//                e.printStackTrace();
	//            } catch (IllegalAccessException e) {
	//                e.printStackTrace();
	//            }
	//            finally{
	//                if( bis != null )
	//                    bis.close();
	//            }
	//
	//        } catch (UnknownHostException e) {
	//            e.printStackTrace();
	//            if( s2 != null )
	//                try {
	//                    s2.close();
	//                } catch (IOException e1) {
	//                    e1.printStackTrace();
	//                }
	//        } catch (IOException e) {
	//            e.printStackTrace();
	//            if( s2 != null )
	//                try {
	//                    s2.close();
	//                } catch (IOException e1) {
	//                    e1.printStackTrace();
	//                }
	//        } finally {
	//            if (temp_oos != null)
	//                try {
	//                    temp_oos.close();
	//                } catch (IOException e) {
	//                    e.printStackTrace();
	//                }
	//            if( s2 != null )
	//                try {
	//                    s2.close();
	//                } catch (IOException e) {
	//                    e.printStackTrace();
	//                }
	//            if( osw != null )
	//                try {
	//                    osw.close();
	//                } catch (IOException e) {
	//                    e.printStackTrace();
	//                }
	//
	//        }
	//
	//
	//        //Log.d( TAG , "Problem loading map from server" );
	//        return null;
	//    }
	//
	//
	//
	//    private static Socket createSocket(String s, int i) throws IOException {
	//
	//         try{
	//           return new Socket( "192.168.0.10" , 2727 );
	//         }
	//         catch( Exception e )
	//         {
	//             e.printStackTrace();
	//             return new Socket( "Chris-PC/192.168.0.10" , 2727 );
	//         }
	//    }
	//



}
