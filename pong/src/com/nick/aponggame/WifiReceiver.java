package com.nick.aponggame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class WifiReceiver extends BroadcastReceiver{
	private WifiP2pManager manager;
    private Channel channel;
    private GameMode2PActivity activity;

    public WifiReceiver(WifiP2pManager manager, Channel channel,
            GameMode2PActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        	int state=intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,  -1);
        	if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED){
        		System.out.println("Wifi Direct enabled!");
        	} else{
        		System.out.println("Wifi Direct disabled?");
        	}
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
        	System.out.println("Wifi Direct Peers changed!");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        	System.out.println("Wifi Direct Connection changed!");
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        	System.out.println("Wifi Direct This device changed!");
        }
    }
}
