# About the project
This app intended to use to insure certain policies are applied to the phone and
they cannot be changed. Example of that would be user can't turn off the location. 

# How the app work
App consist of four different parts:
* Policies : Each policy is responsible for applying certain condition, like LocationPolicy to turn 
on the location or AirplanePolicy to turn off the airplane mode. Policies do not make any decisions
about when they should be run, they simply do there job when they are asked to.
* Broadcast receivers: Used in the app so the app knows when different state changes like when location
mode changed to a different one, broadcast receivers only job is to notify the PolicyManager about 
the state change.
* PolicyManager: this is the heart of the app, it receives what event happens to the system
and acts accordingly, in case any of the policies changed to a not valid state policy manger will run
all the policies and not just the one that changed to be sure that the system is in the correct state
* WorkManager: Used to schedule jobs to be run latter, After running policies we always schedule a new job
to be run latter to make sure after certain time the system gets back to the correct state. This is 
important because we don't have broadcast receiver for all state changes, for example since android 7.0
we don't get any broadcast for mobile data state change.

# Installation
This app should be installed as a system app on a rooted device with SuperSu installed.
## Building the app
To build the app simply run this
```
./gradlew clean
./gradlew assembleDebug
```

This will create the apk file in the root directory of the project.<br> 
Note: We use the debug version since we don't care about the ownership and neither we 
care about the updating with the same signature, but you can build a release apk and sign that if you
want.

## Make the app device owner
There is a `device_owner.xml` is the root directory of the project, pushing this file to to `/data/system/`
in the device and giving the right permission to it will make this app a device owner. The benefit
of this is user  can not disable the app any more. 

## Installing as a system app
To install an app as system app you have to place it inside `/system/priv-app` directory, 
give it a right permission and reboot. <br>
There is a `install.sh` file in the root directory of the project with shell commands to 
build the app, remove the previous app from the device, move the new one to the `system/priv-app`, giving it the 
right permission and reboot. This script also takes care of moving the  `device_owner` file
to the device. <br>
Note: this script will always build the app first so if you want to install it on many devices or 
you want to add custom script you should copy the content of this file to a new file and modify it as
you wish. 

# Testing the dev version
While you're developing, you can change the code and run `sh install.sh` that will build and install the app
however testing shell commands in the app or many other features don't require the app to
be a system app, So you can simply test it like any other app by normally install it on the device 
with Android Studio. You may need to uncomment activity code in the manifest file to be able to run
it inside Android Studio. Note that this will not be possible for all the features. <br>
To see the log you can simply chaned the flag `LOG_ENABLED` in logger file to true. This will help a lot with testing. 


# Contact
If you have any other question contact me at `a.ahmadi.dev@gmail.com`, I will be happy to help.
