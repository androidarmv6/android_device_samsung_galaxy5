# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

## Inherit vendor proprietary files
$(call inherit-product, vendor/samsung/tass/vendor_blobs.mk)
$(call inherit-product, vendor/google/gapps_armv6_tiny.mk)

include device/samsung/msm7x27-common/common.mk

## Device specific overlay
DEVICE_PACKAGE_OVERLAYS := device/samsung/tass/overlay

## Wifi
PRODUCT_PACKAGES += \
    abtfilt \
    wlan_tool \
    wmiconfig

## Ramdisk
PRODUCT_COPY_FILES += \
    device/samsung/tass/ramdisk/init.gt-s5570board.rc:root/init.gt-s5570board.rc \
    device/samsung/tass/ramdisk/init.local.rc:root/init.local.rc \
    device/samsung/tass/ramdisk/ueventd.gt-s5570board.rc:root/ueventd.gt-s5570board.rc \
    device/samsung/tass/ramdisk/TASS.rle:root/TASS.rle

## LDPI assets
PRODUCT_AAPT_PREF_CONFIG := hdpi
