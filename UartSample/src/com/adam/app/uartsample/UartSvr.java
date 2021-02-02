package com.adam.app.uartsample;

public class UartSvr {
	
	static {
        System.loadLibrary("adamjni");
    }
	
	public int OpenUart(int i) {
		return _openUart(i);
	}
	
	public void CloseUart() {
		_closeUart();
	}
	
	public int SetUart(int i) {
		return _setUart(i);
	}
	
	public int SendMsgUart(String str) {
		return _sendMsgUart(str);
	}
	
	public String RecieveMsgUart() {
		return _recieveMsgUart();
	}
	
	
	public static native int _openUart (int i);
	public static native void _closeUart ();
	public static native int _setUart (int i);
	public static native int _sendMsgUart (String str);
	public static native String _recieveMsgUart ();
	

}
