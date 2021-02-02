package com.altek.app.usbtodevicecomm;

import java.lang.ref.SoftReference;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class USBToDeviceService extends Service {

	private static final String TAG = USBToDeviceService.class.getSimpleName();
    
    private static final boolean isDebug = true;
    
    
    private void PrintI(String str) {
    	if(isDebug) Log.i(TAG, str);
    }
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	static class ServiceStub extends IUSBToDeviceService.Stub {

		SoftReference<USBToDeviceService> mService;
		
		private UsbSvr usb_service =  null;
		
		ServiceStub(USBToDeviceService service) {
            mService = new SoftReference<USBToDeviceService>(service);
            usb_service = new UsbSvr();
        }
		
		
		@Override
		public int OpenDevice() throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int PC2COMM() throws RemoteException {
			// TODO Auto-generated method stub
			return usb_service.PC_to_COMM();
		}

		@Override
		public int PC2BGM() throws RemoteException {
			// TODO Auto-generated method stub
			return usb_service.PC_to_MEM();
		}

		@Override
		public void SetBaudrate(int index) throws RemoteException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void SetFlowControl(int isFC) throws RemoteException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void PCExit() throws RemoteException {
			// TODO Auto-generated method stub
			usb_service.PC_Exit();
			
		}

		@Override
		public void ResetComm() throws RemoteException {
			// TODO Auto-generated method stub

			
		}
	
	}
	
	private final IBinder mBinder = new ServiceStub(this);
	

}
