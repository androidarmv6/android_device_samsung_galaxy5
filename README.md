android_device_samsung_tass
==========================

Device configuration for Samsung tass GT-S5570

Getting Started
---------------

To get started with Android for ARMv6/CyanogenMod, you'll need to get
familiar with [Git and Repo](http://source.android.com/download/using-repo).

To initialize your local repository using the CyanogenMod trees, use a command like this:

    repo init -u git://github.com/androidarmv6/android.git -b cm-10.1

Then to sync up:

    repo sync

Build your device:

    source build/envsetup.sh
    brunch tass

Flash ZIP:

    out/target/product/tass/cm-VERSION-DEVICENAME.zip


Please see the [CyanogenMod Wiki](http://wiki.cyanogenmod.org/) for building instructions.

For more information on this Github Organization and how it is structured,
please [read the wiki article](http://wiki.cyanogenmod.org/index.php/Github_Organization)

Disclaimer
--------

All of these device are not supported by CyanogenMOd since they use the old Qualcomm
Snapdragon MSM7x27 chip, and hence cut off by the CyanogenMod team. This team (androidarmv6)
is in no way, shape or form affiliated by the CyanogenMod team and this project is not
endorsed or supported by the CyanogenMod team.

