# Building the apk
./gradlew clean
./gradlew assembleDebug

adb push ServiciosAdministrativos.apk sdcard
adb push device_owner.xml sdcard
adb shell "su 0 mount -o rw,remount /system"

# Remove all the perious versions of apps and device_owner file from the device
adb shell "su 0 rm /data/system/device_owner.xml"
adb shell "su 0 rm /system/priv-app/ServiciosAdministrativos.apk"

adb shell "su 0 mv /sdcard/ServiciosAdministrativos.apk /system/priv-app"
adb shell "su 0 mv /sdcard/device_owner.xml /data/system"
adb shell "su 0 chmod 644 /system/priv-app/ServiciosAdministrativos.apk"
adb shell "su 0 chown system:system /data/system/device_owner.xml"
adb shell "su 0 reboot"
