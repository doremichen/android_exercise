#define LOG_TAG "UsbJNI"
//#include "utils/Log.h"

#include <pthread.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <assert.h>

#include <sys/stat.h>
#include <semaphore.h>
#include <fcntl.h>
#include <errno.h>
#include <termios.h>
#include <time.h>

#include "jni.h"
//#include "JNIHelp.h"
#include <android/log.h>

#define __DEBUG__

#ifdef __DEBUG__
#define LOGV(...) __android_log_print( ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__ )
#define LOGD(...) __android_log_print( ANDROID_LOG_DEBUG,  LOG_TAG, __VA_ARGS__ )
#define LOGI(...) __android_log_print( ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__ )
#define LOGW(...) __android_log_print( ANDROID_LOG_WARN,  LOG_TAG, __VA_ARGS__ )
#define LOGE(...) __android_log_print( ANDROID_LOG_ERROR,  LOG_TAG, __VA_ARGS__ )
#else
#define LOGV(...)
#define LOGD(...)
#define LOGI(...)
#define LOGW(...)
#define LOGE(...)
#endif

//for erro condition
#define ERR_REOOPEN         0
#define ERR_OPENUSBFAIL     1
#define ERR_OPENBMGFAIL     2
#define ERR_OPENCOMMFAIL    3
#define ERR_PC2BMGTHRFAIL   4
#define ERR_BMG2PCTHRFAIL   5
#define ERR_PC2COMMTHRFAIL  6
#define ERR_COMM2PCTHRFAIL  7
#define ERR_NONINITCONFIG   8
#define ERR_NO              9


#define THREAD_NUM 2

#define JNI_FALSE  0 
#define JNI_TRUE   1

#define USBCDC_DEV      "/dev/ttyGS0"
#define COMMS_DEV       "/dev/ttyMT0"
#define BGM_DEV         "/dev/ttyMT1"

static const char* const kClassName =
        "com/altek/app/usbtodevicecomm/UsbSvr";


pthread_t pc2bmgthrd_id;
pthread_t bmg2pcthrd_id;
pthread_t pc2commthrd_id;
pthread_t comm2pcthrd_id;

//adjust whether thread create or not
jbyte isCreateMemThrd = JNI_FALSE;
jbyte isCreateCommThrd = JNI_FALSE;

//adjust init config: baudrate, flowcontrol
jbyte isbaudrate = JNI_FALSE;
jbyte isflowcontrol = JNI_FALSE;


jbyte isThreadActive = JNI_FALSE;

volatile jint gbgm_fd = -1;
volatile jint gcomms_fd = -1;
volatile jint gusbcdc_fd = -1;

jint bgm_baudrate = B9600;
jint comms_baudrate = B9600;
jint usbpc_baudrate = B9600;

// for SetBaudrate, SetFlowControl
jint baudrate = 0;
jint checkFlowControl = 0;

volatile jbyte gPC2BMG = JNI_FALSE;
volatile jbyte gBMG2PC = JNI_FALSE;
volatile jbyte gPC2COMM = JNI_FALSE;
volatile jbyte gCOMM2PC = JNI_FALSE;

jint count = 0; //for finish thread number

sem_t wait_event;   //for create next thread time when finish current thread

jint speed_arr[]={B1200,B2400,B4800,B9600,B19200,B38400,B57600,B115200,B230400,B921600};

//Common function
void SetUARTConfig(int fd, int isfc, unsigned int baudrate)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    
    LOGV(" [%s] isfc = %d\n", __FUNCTION__, isfc);
    LOGV(" [%s] baudrate = %d\n", __FUNCTION__, baudrate);
    
    //terminal I/O interfaces
    struct termios termOptions;
    
    //file control
    fcntl(fd, F_SETFL, 0);
    
    // Set 8bit data, No parity, stop 1 bit (8N1):
    termOptions.c_cflag &= ~PARENB;
    termOptions.c_cflag &= ~CSTOPB;
    termOptions.c_cflag &= ~CSIZE;
    termOptions.c_cflag |= CS8 | CLOCAL | CREAD;
    
    // Raw mode
    termOptions.c_iflag &= ~(INLCR | ICRNL | IXON | IXOFF | IXANY);
    termOptions.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);  /*raw input*/
    termOptions.c_oflag &= ~OPOST;  /*raw output*/
    
    tcflush(fd, TCIFLUSH);//clear input buffer
    termOptions.c_cc[VTIME] = 100; /* inter-character timer unused */
    termOptions.c_cc[VMIN] = 0; /* blocking read until 0 character arrives */
    
    // Set baudrate to 9600 bps
    cfsetispeed(&termOptions, (speed_t)baudrate);
    cfsetospeed(&termOptions, (speed_t)baudrate);
    
    termOptions.c_cflag |= CLOCAL;
    if(isfc)
        termOptions.c_cflag |= CRTSCTS;        // Enable hardware flow control
    else    
        termOptions.c_cflag &= ~CRTSCTS;       // Disable hardware flow control

    
    /*
    * Set the new options for the port...
    */
    if(tcsetattr(fd, TCSANOW, &termOptions)<0)
	 {
	  LOGV("tcsetattr fail !\n");
	 }
    
    tcflush(fd, TCIOFLUSH);
}    

//thread body
void* PC2Bmgthread(void* ptr)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    char testpcstr[] = "hello pc usb com\n";
    unsigned char pcbuffer[1024] = {0};
    int nusbcdcRead = 0, nusbcdcWrite = 0;
    char testbgmstr[] = "hello bgm com\n";
    unsigned char bgmbuffer[1024] = {0};
    int nbgmRead = 0, nbgmWrite = 0;
    
    //send "welcome" message to usb com
//    write(gusbcdc_fd, testpcstr, sizeof(testpcstr));

    //send "welcome" message to bgm com
//    write(gbgm_fd, testbgmstr, sizeof(testbgmstr));
    
    while(gPC2BMG) {
        
      if(gusbcdc_fd != -1 && gbgm_fd != -1) {
        
        // Read data from usb vcom            
        nusbcdcRead = read(gusbcdc_fd, &pcbuffer, sizeof(pcbuffer));        
   
        if(nusbcdcRead == -1) {
            LOGE("[%s]: read USB COM port error! errno = %d\n", __FUNCTION__, errno);
            if(!gPC2BMG)
                goto Exit;
            else {    
                sleep(1);
        	    continue;
    		}   
        }
        
        
        if(nusbcdcRead > 0) {
                    
            LOGV("[%s]: USB COM port have data<%d>: %c\n", __FUNCTION__, nusbcdcRead, pcbuffer);
            nbgmWrite = write(gbgm_fd, &pcbuffer, nusbcdcRead);
        }
        
        if(nbgmWrite == -1) {
            LOGE("[%s]: write Comm COM port error! errno = %d\n", __FUNCTION__, errno); 
            sleep(1);
        	continue;  
        }
        
      }
      else {    
            LOGE("[%s]: com port type invalid\n", __FUNCTION__); 
            goto Exit;
      }         
      

    }
Exit:    
    LOGV(" [%s] finish\n", __FUNCTION__);
    
    if(count < THREAD_NUM) {
        count++;   
    } else {
        count = 0;
        close(gusbcdc_fd);
        close(gbgm_fd);
        sem_post(&wait_event);  
    }       
    
    return NULL;
}

void* Bmg2PCthread(void* ptr)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    char testpcstr[] = "hello pc usb com\n";
    unsigned char pcbuffer[1024] = {0};
    int nusbcdcRead = 0, nusbcdcWrite = 0;
    char testbgmstr[] = "hello bmg com\n";
    unsigned char bgmbuffer[1024] = {0};
    int nbgmRead = 0, nbgmWrite = 0;
    
    //send "welcome" message to usb com
//    write(gusbcdc_fd, testpcstr, sizeof(testpcstr));

    //send "welcome" message to bmg com
//    write(gbgm_fd, testbgmstr, sizeof(testbgmstr));
    
    while(gBMG2PC) {
        
      if(gusbcdc_fd != -1 && gbgm_fd != -1) {
        
        // Read data from bmg com            
        nbgmRead = read(gbgm_fd, &bgmbuffer, sizeof(bgmbuffer));        
   
        if(nbgmRead == -1) {
            LOGE("[%s]: read USB COM port error! errno = %d\n", __FUNCTION__, errno);
            if(!gBMG2PC)
                goto Exit;
            else {    
                sleep(1);
        	    continue;
    		}   
        }
        
        
        if(nbgmRead > 0) {
                    
            LOGV("[%s]: USB COM port have data<%d>: %c\n", __FUNCTION__, nusbcdcRead, pcbuffer);
            nusbcdcWrite = write(gusbcdc_fd, &bgmbuffer, nbgmRead);
        }
        
        if(nusbcdcWrite == -1) {
            LOGE("[%s]: write USB VCOM port error! errno = %d\n", __FUNCTION__, errno); 
            sleep(1);
        	continue;  
        }
        
      }
      else {    
            LOGE("[%s]: com port type invalid\n", __FUNCTION__); 
            goto Exit;
      }         
      

    }
Exit:    
    LOGV(" [%s] finish\n", __FUNCTION__);
    
    if(count < THREAD_NUM) {
        count++;   
    } else {
        count = 0;
        close(gusbcdc_fd);
        close(gbgm_fd);
        sem_post(&wait_event);  
    } 
    
    return NULL;
}


void* PC2COMMthread(void* ptr)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    unsigned char pcbuffer[1024] = {0};
    char teststr[] = "hello pc usb com\n";
    char testcommstr[] = "hello Comm com\n";
    unsigned char commbuffer[1024] = {0};
    int nusbcdcRead = 0, nusbcdcWrite = 0;
    int ncommRead = 0, ncommWrite = 0;

    //send "welcome" message to usb com
//    write(gusbcdc_fd, teststr, sizeof(teststr));
    
    //send "welcome" message to comm com
//    write(gcomms_fd, testcommstr, sizeof(testcommstr));
    
    while(gPC2COMM) {
        
        if(gusbcdc_fd != -1 && gcomms_fd != -1) {
            // read data from PC side      
            nusbcdcRead = read(gusbcdc_fd, &pcbuffer, sizeof(pcbuffer));
            
            if(nusbcdcRead == -1) {
                LOGE("[%s]: read USB COM port error! errno = %d\n", __FUNCTION__, errno);
                if(!gPC2COMM)
                   goto Exit;
                else {    
                    sleep(1);
        			continue;
    		    }   
            }
                     
            if(nusbcdcRead > 0) {
                    
                LOGV("[%s]: USB COM port have data<%d>: %c\n", __FUNCTION__, nusbcdcRead, pcbuffer);
                 ncommWrite = write(gcomms_fd, &pcbuffer, nusbcdcRead);
            }
            
            if(ncommWrite == -1) {
                LOGE("[%s]: write COMM COM port error! errno = %d\n", __FUNCTION__, errno); 
                sleep(1);
        	    continue;
        	}      

                 
        }
        else {    
            LOGE("[%s]: com port type invalid\n", __FUNCTION__); 
            goto Exit;
        }            

    }    

Exit:    
    
    LOGV(" [%s] finish\n", __FUNCTION__);
    
    if(count < THREAD_NUM) {
        count++;   
    } else {
        count = 0;
        close(gusbcdc_fd);
        close(gcomms_fd);
        sem_post(&wait_event);  
    } 
    
    return NULL;
}

void* COMM2PCthread(void* ptr)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    unsigned char pcbuffer[1024] = {0};
    char teststr[] = "hello pc usb com\n";
    char testcommstr[] = "hello Comm com\n";
    unsigned char commbuffer[1024] = {0};
    int nusbcdcRead = 0, nusbcdcWrite = 0;
    int ncommRead = 0, ncommWrite = 0;

    //send "welcome" message to usb com
//    write(gusbcdc_fd, teststr, sizeof(teststr));
    
    //send "welcome" message to comm com
//    write(gcomms_fd, testcommstr, sizeof(testcommstr));
    
    while(gCOMM2PC) {
        
        if(gusbcdc_fd != -1 && gcomms_fd != -1) {
            
            // read data from Comm side
            ncommRead = read(gcomms_fd, &commbuffer, sizeof(commbuffer));

            
            if(ncommRead == -1) {
                LOGE("[%s]: read Comm COM port error! errno = %d\n", __FUNCTION__, errno);
                
                if(!gCOMM2PC)
                   goto Exit;
                else {    
                    sleep(1);
        			continue;
    		    }   
            }
            
            
            if(ncommRead > 0) {
                    
                LOGV("[%s]: COMM COM port have data<%d>: %c\n", __FUNCTION__, ncommRead, commbuffer);
                 nusbcdcWrite = write(gusbcdc_fd, &commbuffer, ncommRead);
            }
            
            if(nusbcdcWrite == -1) {
                LOGE("[%s]: write USB VCOM port error! errno = %d\n", __FUNCTION__, errno); 
                sleep(1);
        	    continue;
        	}        
        }
        else {    
            LOGE("[%s]: com port type invalid\n", __FUNCTION__); 
            goto Exit;
        }            

    }    

Exit:    
    
    LOGV(" [%s] finish\n", __FUNCTION__);
    
    if(count < THREAD_NUM) {
        count++;   
    } else {
        count = 0;
        close(gusbcdc_fd);
        close(gcomms_fd);
        sem_post(&wait_event); 
    } 
    
    return NULL;
}

//Jni function implement

static jint OpenDevice(JNIEnv *env, jobject clazz)
{
	LOGV(" [%s] +++\n", __FUNCTION__);
	
	jint ret = 0;
		         
    return ret;
}

static jint PC_to_MEM(JNIEnv *env, jobject clazz)
{
	LOGV(" [%s] +++\n", __FUNCTION__);
	
//	pthread_t id;
    jint ret = ERR_NO;
    jint thrd_stat = -1;
//    int i,ret;
    char *message = (char *)"MEM thread";
    
    //check com port config
    if(!isbaudrate || !isflowcontrol) {
       LOGE("Com port config is not initial!\n"); 
       return ERR_NONINITCONFIG;
    } 
    
    
    if(isCreateMemThrd) {
       LOGE("Mem pthread is already exit!\n");
       return ERR_REOOPEN;
    }
        
    
    gPC2COMM = JNI_FALSE;
    gCOMM2PC = JNI_FALSE;
    gPC2BMG = JNI_TRUE;
    gBMG2PC = JNI_TRUE;
    
    if(isThreadActive)
        sem_wait(&wait_event);    //wait the other thread finish completedly if the other thread exist.
    
    count++;
    
    gusbcdc_fd=open(USBCDC_DEV,O_RDWR|O_NOCTTY|O_NDELAY);
    gbgm_fd=open(BGM_DEV,O_RDWR|O_NOCTTY|O_NDELAY);
    
    if(gusbcdc_fd == -1) {
	    
	    LOGE("[%s], usbcdc_fd err=%d\n", __FUNCTION__, errno);
	    ret = ERR_OPENUSBFAIL;    
	        
	}
	else {
	    
	    SetUARTConfig(gusbcdc_fd, JNI_FALSE, usbpc_baudrate);    
	}
	
	 if(gbgm_fd == -1) {
	    
	    LOGE("[%s], bgm_fd err=%d\n", __FUNCTION__, errno);    
	    ret = ERR_OPENBMGFAIL;    
	 }
	 else {
	    SetUARTConfig(gbgm_fd, checkFlowControl, baudrate);  
	 }  
	 	
    thrd_stat= pthread_create(&pc2bmgthrd_id, NULL, PC2Bmgthread,  (void *)message);
    if(thrd_stat!=0){
      LOGE("Create pthread error!\n");
      ret = ERR_PC2BMGTHRFAIL; 
    }
    
    thrd_stat= pthread_create(&bmg2pcthrd_id, NULL, Bmg2PCthread,  (void *)message);
    if(thrd_stat!=0){
      LOGE("Create pthread error!\n"); 
      ret = ERR_BMG2PCTHRFAIL;
    }
    
    isThreadActive = JNI_TRUE;
    isCreateMemThrd = JNI_TRUE;    
    
    //Reset flag
    isbaudrate = JNI_FALSE;
    isflowcontrol = JNI_FALSE;
    isCreateCommThrd = JNI_FALSE;
    
    return ret;
    
}

static jint PC_to_COMM(JNIEnv *env, jobject clazz)
{
	LOGV(" [%s] +++\n", __FUNCTION__);

//    pthread_t id;
    jint ret = ERR_NO;
    jint thrd_stat = -1;
//    int i,ret;
    char *message = (char *)"COMM thread";
    
    //check com port config
    if(!isbaudrate || !isflowcontrol) {
       LOGE("Com port config is not initial!\n"); 
       return ERR_NONINITCONFIG;
    }    
    
    
     if(isCreateCommThrd) {
        LOGE("Comm pthread is already exit!\n"); 
        return ERR_REOOPEN;   
    }    
    
    gPC2COMM = JNI_TRUE;
    gCOMM2PC = JNI_TRUE;
    gPC2BMG = JNI_FALSE;
    gBMG2PC = JNI_FALSE;
    
    if(isThreadActive)
        sem_wait(&wait_event);    //wait the other thread finish completedly if the other thread exist.
    
    count++;
    
    gusbcdc_fd=open(USBCDC_DEV,O_RDWR|O_NOCTTY|O_NDELAY);
    gcomms_fd=open(COMMS_DEV,O_RDWR|O_NOCTTY|O_NDELAY);
    
    if(gusbcdc_fd == -1) {
	    
	    LOGE("[%s], usbcdc_fd err=%d\n", __FUNCTION__, errno);
	    ret = ERR_OPENUSBFAIL;    
	        
	}
	else {
	    
	    SetUARTConfig(gusbcdc_fd, JNI_FALSE, usbpc_baudrate);	    
	}
	
    if(gcomms_fd == -1) {
	    
	    LOGE("[%s], comms_fd err=%d\n", __FUNCTION__, errno);    
	    ret = ERR_OPENCOMMFAIL;    
	 }
	 else {
	    SetUARTConfig(gcomms_fd, checkFlowControl, baudrate);   
	 }
		
          
    thrd_stat= pthread_create(&pc2commthrd_id, NULL, PC2COMMthread,  (void *)message);
    if(thrd_stat!=0){
      LOGE("Create pthread error!\n"); 
      ret = ERR_PC2COMMTHRFAIL; 
    }     

     thrd_stat= pthread_create(&comm2pcthrd_id, NULL, COMM2PCthread,  (void *)message);
    if(thrd_stat!=0){
      LOGE("Create pthread error!\n");
      ret = ERR_COMM2PCTHRFAIL;  
    }    

    isThreadActive = JNI_TRUE;
    
    isCreateCommThrd = JNI_TRUE;
    
    //Reset flag
    isbaudrate = JNI_FALSE;
    isflowcontrol = JNI_FALSE;
    isCreateMemThrd = JNI_FALSE;
 
    return ret;
    
}

static void SetBaudrate(JNIEnv *env, jobject clazz, jint index)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    
     baudrate = speed_arr[index]; 
     isbaudrate = JNI_TRUE;
}

static void SetFlowControl(JNIEnv *env, jobject clazz, jint isFc)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
    
    checkFlowControl = isFc;
    isflowcontrol = JNI_TRUE;
}

static void PC_Exit(JNIEnv *env, jobject clazz)
{
    LOGV(" [%s] +++\n", __FUNCTION__);
       
    gusbcdc_fd = -1;
    gcomms_fd = -1;
    gbgm_fd = -1;
    
    gPC2COMM = JNI_FALSE;
    gCOMM2PC = JNI_FALSE;
    gPC2BMG = JNI_FALSE;
    gBMG2PC = JNI_FALSE;
    
    isCreateCommThrd = JNI_FALSE;
    isCreateMemThrd = JNI_FALSE;

}    


//=====================================================================================
/*
 * Array of methods.
 *
 * Each entry has three fields: the name of the method, the method
 * signature, and a pointer to the native implementation.
 */
static const JNINativeMethod gMethods[] = {
    {"_OpenDevice", "()I", (void*)OpenDevice},
    {"_PC_to_COMM", "()I", (void*)PC_to_COMM},
    {"_PC_to_MEM", "()I", (void*)PC_to_MEM},
    {"_SetBaudrate", "(I)V", (void*)SetBaudrate},
    {"_SetFlowControl", "(I)V", (void*)SetFlowControl},
    {"_PC_Exit", "()V", (void*)PC_Exit},
};

static int registerMethods(JNIEnv* env) {
    
    jclass clazz;

    /* look up the class */
    clazz = env->FindClass(kClassName);
    if (clazz == NULL) {
        LOGE("Can't find class %s\n", kClassName);
        return -1;
    }

//    gmid = env->GetMethodID(env->FindClass("com/altek/app/JNIDemoActivity"), "nativeCallBackFunc", "()V");

    /* register all the methods */
    if (env->RegisterNatives(clazz, gMethods,
            sizeof(gMethods) / sizeof(gMethods[0])) != JNI_OK)
    {
        LOGE("Failed registering methods for %s\n", kClassName);
        return -1;
    }
    
    
    /* fill out the rest of the ID cache */
    return 0;
}

/*
 * This is called by the VM when the shared library is first loaded.
 */
jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env = NULL;
    jint result = -1;
	LOGI("JNI_OnLoad");

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("ERROR: GetEnv failed\n");
        goto fail;
    }
    assert(env != NULL);

    if (registerMethods(env) != 0) {
        LOGE("ERROR: PlatformLibrary native registration failed\n");
        goto fail;
    }

    /* success -- return valid version number */
    result = JNI_VERSION_1_4;

fail:
    return result;
}
