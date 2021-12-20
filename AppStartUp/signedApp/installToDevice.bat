@echo off

color 0f

set App=AppStartUp.apk
set PAKAGE=com.adam.app.appstartup
set SYSTEM_PATH=/system/app


set UNSIGNED_APK=AppStartUp.apk
set SIGNED_APK=AppStartUp_signed.apk
set UNALIGHED_APK=AppStartUp-unaligned.apk


echo ===========================
echo signed app
echo ===========================
:: delete file if file exists
IF exist %SIGNED_APK% (del /S /Q %SIGNED_APK%)
IF exist %UNSIGNED_APK% (del /S /Q %UNSIGNED_APK%)
IF exist %UNALIGHED_APK% (del /S /Q %UNALIGHED_APK%)


:: copy file
xcopy ..\bin\%App% /Y


java -jar "%~dp0signapk.jar" "%~dp0platform.x509.pem" "%~dp0platform.pk8" %UNSIGNED_APK% %UNALIGHED_APK%
zipalign -f -v 4 %UNALIGHED_APK% %SIGNED_APK%
zipalign -c -v 4 %SIGNED_APK%


adb wait-for-device
echo ===========================
echo remount memory
echo ===========================
adb remount

echo ===========================
echo remove old app
echo ===========================
adb shell rm -rf %SYSTEM_PATH%/%SIGNED_APK%
echo ===========================
echo push %SIGNED_APK% to /system/app
echo ===========================
adb push %SIGNED_APK% %SYSTEM_PATH%
echo ===========================
echo Inatsll Complete...
echo ===========================
adb reboot

echo.&pause&goto:eof