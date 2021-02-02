package com.altek.app.usbtodevicecomm;

import android.util.Log;

public class UsbSvr {
    
    private static final String TAG = UsbSvr.class.getSimpleName();
    
    private static final boolean isDebug = true;
    
    private int open_ok = 0;
    
    private void PrintI(String str) {
    	Log.i(TAG, str);
    }
    
	
	static {
        System.loadLibrary("usb_to_dev_comm_jni");
    }
	
	public UsbSvr() {
		PrintI("UsbSvr");
	    open_ok = _OpenDevice();
	}    
	
	public int isOpenDeviceStatus() {
	    return open_ok;   
	}    
	
	
	public int PC_to_COMM() {
		PrintI("PC_to_COMM");
	    return _PC_to_COMM();
	}    
	
	public int PC_to_MEM() {
		PrintI("PC_to_MEM");
	    return _PC_to_MEM();
	}
	
	public void SetBaudrate(int index) {
		PrintI("SetBaudrate");
	    _SetBaudrate(index);
	}
	
	public void SetFlowControl(int isFC) {
		PrintI("SetFlowControl");
	    _SetFlowControl(isFC);
	}
	
	public void PC_Exit() {
		PrintI("PC_Exit");
	    _PC_Exit();
	}
	
	
	public static native int _OpenDevice ();
	public static native int _PC_to_COMM ();
	public static native int _PC_to_MEM ();
	public static native void _SetBaudrate (int index);
	public static native void _SetFlowControl (int isFC);
	public static native void _PC_Exit ();
	

}
