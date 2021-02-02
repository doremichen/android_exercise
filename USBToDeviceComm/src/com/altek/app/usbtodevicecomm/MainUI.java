package com.altek.app.usbtodevicecomm;

import com.altek.com.usbtodevicecomm.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainUI extends Activity {
    
    private static final String TAG = MainUI.class.getSimpleName();
    private static final boolean DEBUG = false;

	private UsbSvr usb_service =  null;
	
	private static final int PC2BMG = 0;
	private static final int PC2COMM = 1;
	
	
	private static final int ALREADYOPEN = 0;
	
	
	private PopupMenu popupSetBaudrateMenu =  null;
	private Button setBaudrateBtn = null;
	private PopupMenu popupSetFlowcontrolMenu =  null;
	private Button setFlowControlBtn = null;
	
	
	private Message usbmsg = null;
	
	private void PrintI(String str) {
    	if(DEBUG) Log.i(TAG, str);
    }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainui);
		
		usb_service = new UsbSvr();
		
		setBaudrateBtn = (Button)this.findViewById(R.id.btn_set_baudrate);
		setFlowControlBtn = (Button)this.findViewById(R.id.btn_set_flowcontrol);
		
		//set baudrate
		popupSetBaudrateMenu = new PopupMenu(this, setBaudrateBtn);       
		popupSetBaudrateMenu.getMenuInflater().inflate(R.menu.set_baudrate_menu, popupSetBaudrateMenu.getMenu());
		popupSetBaudrateMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int index = 0;
                // TODO Auto-generated method stub
                switch(item.getItemId()) {
                    case R.id.baudrate_item_one:
                        index = 0;                        
                        break;
                    case R.id.baudrate_item_two:
                        index = 1;                        
                        break;
                    case R.id.baudrate_item_three:                        
                        index = 2;
                        break;
                    case R.id.baudrate_item_four:                        
                        index = 3;
                        break;
                    case R.id.baudrate_item_five:                        
                        index = 4;
                        break;   
                     case R.id.baudrate_item_six:                        
                        index = 5;
                        break;
                    case R.id.baudrate_item_seven:                        
                        index = 6;
                        break;
                    case R.id.baudrate_item_eight:                        
                        index = 7;
                        break;
                    case R.id.baudrate_item_nine:                        
                        index = 8;
                        break;
                    case R.id.baudrate_item_ten:                        
                        index = 9;
                        break;                                       
                    default:
                        break;
                }
                
                if(item.isChecked()) item.setChecked(false);                   
                else item.setChecked(true);
                        
                Toast.makeText(MainUI.this, MainUI.this.getString(R.string.str_baudrate_pop_ctx) + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                        
                usb_service.SetBaudrate(index);        
                return true;
            }
        });
		
		//set flowcontrol
		popupSetFlowcontrolMenu = new PopupMenu(this, setFlowControlBtn);       
		popupSetFlowcontrolMenu.getMenuInflater().inflate(R.menu.set_flowcontrol_menu, popupSetFlowcontrolMenu.getMenu());
		popupSetFlowcontrolMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
		            
		            @Override
		            public boolean onMenuItemClick(MenuItem item) {
		                int index = 0;
		                // TODO Auto-generated method stub
		                switch(item.getItemId()) {
		                    case R.id.flowcontrol_item_enble:
		                        index = 1;                        
		                        break;
		                    case R.id.flowcontrol_item_disable:
		                        index = 0;                        
		                        break;                                  
		                    default:
		                        break;
		                }
		                
		                if(item.isChecked()) item.setChecked(false);                   
		                else item.setChecked(true);
		                        
		                Toast.makeText(MainUI.this, MainUI.this.getString(R.string.str_flowcontrol_pop_ctx) + item.getTitle(),
		                        Toast.LENGTH_SHORT).show();
		                        
		                usb_service.SetFlowControl(index);        
		                return true;
		            }
		   });
		
		
		
	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.bindService(new Intent(IUSBToDeviceService.class.getName()), osc, Context.BIND_AUTO_CREATE);
	}

	
	 private ServiceConnection osc = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		 
	 };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_ui, menu);
		return true;
	}
	

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void OnSetBaudrate(View v) {
		PrintI("OnSetBaudrate");
		popupSetBaudrateMenu.show();
	}
	
	public void OnSetFlowControl(View v) {
		PrintI("OnSetFlowControl");
		popupSetFlowcontrolMenu.show();
	}

	public void OnPC2COMM(View v) {
	    PrintI("OnPC2COMM");
        SwitchAndOpen(PC2COMM);
        finish();
	}
	
	public void OnPC2MEM(View v) {
	    PrintI("OnPC2MEM");
        SwitchAndOpen(PC2BMG);
        finish();
	}

	
	public void OnPCExit(View v) {
		usb_service.PC_Exit();
		finish();
	}
	
	public void SwitchAndOpen(final int index) {
	 
        new Thread(new Runnable() {  
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                    	int isopen = 0;
                    	                  	
                        switch(index) {
                        case PC2BMG:
                        	isopen = usb_service.PC_to_MEM();
                        break;
                        case PC2COMM:
                        	isopen = usb_service.PC_to_COMM();
                        break;     
                        }                            
                        PrintI("isopen = " + isopen);
                    }
        }).start();    
	    
        
	} 
		
}
