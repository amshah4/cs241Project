package com.nick.aponggame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class GameMode2PActivity extends Activity
{
	private SurfaceView view;
	
	
	WifiP2pManager manager;
	Channel channel;
	BroadcastReceiver receiver;
	IntentFilter intent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        view=new StateHandler2P(this);
        view.setFocusable(true);
	   	view.setZOrderOnTop(true);
	   	view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
	   												ViewGroup.LayoutParams.MATCH_PARENT));
	   	
	   	/*
	   	 * HANDLE SETTING UP NETWORK HERE
	   	 */
	   	manager=(WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
	   	channel=manager.initialize(this,  getMainLooper(),  null);
	   	receiver=new WifiReceiver(manager, channel, this);
	   	
	   	intent=new IntentFilter();
	   	intent.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
	   	intent.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
	   	intent.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
	   	intent.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
	   	
	   	manager.discoverPeers(channel, new WifiP2pManager.ActionListener(){
	   		//@Override
	   		public void onSuccess(){
	   			System.out.println("SUCCESSful discovery of peers");
	   		}
	   		//@Override
	   		public void onFailure(int reason){
	   			System.out.println("Discovery failed. ); reason="+reason);
	   		}
	   	});
	   	
	   	isPlayer1=true;//WILL BE DYNAMIC ONCE WE HAVE NETWORK CODE
	   	
	   	if(isPlayer1)
	   		balls.add(new Ball(view.getWidth()/2, view.getHeight()/2, 3, 3, 10));
	   	
	   	handler=new StateHandler(view.getWidth(), view.getHeight(), balls, paddle, this, holder);
	   	
	   	setContentView(view);
	   	return;
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	registerReceiver(receiver, intent);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	unregisterReceiver(receiver);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_game_mode2_p, menu);
        return true;
    }
	
    @Override
	public boolean onTouchEvent(MotionEvent e)
	{
		paddle.setX((int)(e.getX()));
		return true;
	}	
}
