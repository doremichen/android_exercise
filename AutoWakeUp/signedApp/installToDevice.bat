@echo off

color 0f

set App=AutoWakeUp.apk
set PAKAGE=com.adam.app

set UNSIGNED_APK=AutoWakeUp.apk
set SIGNED_APK=AutoWakeUp_signed.apk
set UNALIGHED_APK=AutoWakeUp-unaligned.apk


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
adb uninstall %PAKAGE%
adb shell rm -rf data/data/%PAKAGE%
echo ===========================
echo push %SIGNED_APK% to device
echo ===========================
adb install -r %SIGNED_APK%
echo ===========================
echo Starting SIGNED_APK
echo ===========================
adb shell am start -n %PAKAGE%/.MainActivity

echo.&pause&goto:eof