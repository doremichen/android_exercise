#define LOG_TAG "UartJNI"
//#include "utils/Log.h"

#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <assert.h>

#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>

#include "jni.h"
//#include "JNIHelp.h"
#include <android/log.h>


#define LOGV(...) __android_log_print( ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__ )
#define LOGD(...) __android_log_print( ANDROID_LOG_DEBUG,  LOG_TAG, __VA_ARGS__ )
#define LOGI(...) __android_log_print( ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__ )
#define LOGW(...) __android_log_print( ANDROID_LOG_WARN,  LOG_TAG, __VA_ARGS__ )
#define LOGE(...) __android_log_print( ANDROID_LOG_ERROR,  LOG_TAG, __VA_ARGS__ )

jmethodID gmid;
JNIEnv *gJNIenv;

jint fd = -1;
struct termios newtio,oldtio;


//Jni function implement

static jint openUart(JNIEnv *env, jobject clazz, jint i)
{
    LOGV(" +++ openUart +++");
    if(i == 1)
    {
        fd=open("/dev/ttyMT0",O_RDWR|O_NOCTTY|O_NDELAY);
    }
    else if(i == 2)
    {
        fd=open("/dev/ttyMT1",O_RDWR|O_NOCTTY|O_NDELAY);
    }

    if(fd == -1) {
        LOGV(" can not open uart...");
    }

    return fd;
}


static void closeUart(JNIEnv *env, jobject clazz)
{
    LOGV(" +++ closeUart +++");
    close(fd);
}


static jint setUart(JNIEnv *env, jobject clazz, jint i)
{
    LOGV(" +++ setUart +++");
     int  speed_arr[]={B1200,B2400,B4800,B9600,B19200,B38400,B57600,B115200,B230400,B921600};
    LOGV("[setUart]: fd = %d\n", fd);

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
        termOptions.c_cc[VTIME] = 0; /* inter-character timer unused */
        termOptions.c_cc[VMIN] = 0; /* blocking read until 0 character arrives */

        // Set baudrate to 9600 bps
        cfsetispeed(&termOptions, (speed_t)speed_arr[i]);
        cfsetospeed(&termOptions, (speed_t)speed_arr[i]);

        termOptions.c_cflag |= CLOCAL;

        termOptions.c_cflag &= ~CRTSCTS;
//        if(isfc)
//            termOptions.c_cflag |= CRTSCTS;        // Enable hardware flow control
//        else
//            termOptions.c_cflag &= ~CRTSCTS;       // Disable hardware flow control


        /*
        * Set the new options for the port...
        */
        if(tcsetattr(fd, TCSANOW, &termOptions)<0)
         {
          LOGV("tcsetattr fail !\n");
         }

        tcflush(fd, TCIOFLUSH);

//     tcgetattr(fd,&oldtio);
//     tcgetattr(fd,&newtio);
//     cfsetispeed(&newtio,speed_arr[i]);
//     cfsetospeed(&newtio,speed_arr[i]);
//     newtio.c_lflag=0;
//     newtio.c_cflag = speed_arr[i] | CS8 | CREAD | CLOCAL;
//     newtio.c_iflag= BRKINT | IGNPAR | IGNCR | IXON | IXOFF | IXANY ;
//     newtio.c_oflag=02;
//     newtio.c_line=0;
//     newtio.c_cc[7]=255;
//     newtio.c_cc[4]=0;
//     newtio.c_cc[5]=0;
//
//     if(tcsetattr(fd,TCSANOW,&newtio)<0)
//     {
//      LOGV("tcsetattr fail !\n");
//      exit(1);
//     }

    return fd;
}

static jint sendMsgUart(JNIEnv *env, jobject clazz, jstring str)
{
    LOGV(" +++ sendMsgUart +++");
    jint len = 0;
    const char *buf;
    buf= env->GetStringUTFChars(str,NULL);
    len= env->GetStringLength(str );
    write(fd,buf,len);
    env->ReleaseStringUTFChars(str, buf);
    return len;
}


static jstring recieveMsgUart(JNIEnv *env, jobject clazz)
{
    LOGV(" +++ recieveMsgUart +++");

    char buffer[255];
    char buf[255];
    int len,i=0,k=0;
    memset(buffer,0,sizeof(buffer));
    memset(buf,0,sizeof(buf));
    LOGV("\n");
    len=read(fd,buffer,255);
    if (len>0)
    {
        LOGI("len","len =%d, buffer[0]=%c,buffer[1]=%c\n",len,buffer[0],buffer[1]);
        LOGI("buffer","buffer=%s\n",buffer);
    //return env->NewStringUTF(buffer);
    }

    return NULL;
}
//=====================================================================================
/*
 * Array of methods.
 *
 * Each entry has three fields: the name of the method, the method
 * signature, and a pointer to the native implementation.
 */
static const JNINativeMethod gMethods[] = {
    {"_openUart", "(I)I", (void*)openUart},
    {"_closeUart", "()V", (void*)closeUart},
    {"_setUart", "(I)I", (void*)setUart},
    {"_sendMsgUart", "(Ljava/lang/String;)I", (void*)sendMsgUart},
    {"_recieveMsgUart", "()Ljava/lang/String;", (void*)recieveMsgUart},
};

static int registerMethods(JNIEnv* env) {
    static const char* const kClassName =
        "com/adam/app/uartsample/UartSvr";
    
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
    
    
    gJNIenv = env;

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
