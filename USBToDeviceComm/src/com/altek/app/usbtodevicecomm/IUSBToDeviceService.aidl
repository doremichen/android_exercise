/*
*  
*/

package com.altek.app.usbtodevicecomm;


interface IUSBToDeviceService
{
    int OpenDevice ();
	int PC2COMM ();
	int PC2BGM ();
	void SetBaudrate (int index);
	void SetFlowControl (int isFC);
	void PCExit ();
	void ResetComm ();
}

