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

## Note: this file and all of its includes are parsed before any
## BoardConfig files. 3.0 Kernel & ath6kl configuration must be set here.

## Build the 3.0.x Kernel
#BUILD_WITH_30X_KERNEL := true

## Build the ath6kl-compat driver
BOARD_WLAN_DEVICE := ath6kl_compat

## Swap /system and /data so that we have more space for ROM installation
BOARD_SWAP_SYSTEMDATA := true

## Camera
PRODUCT_PACKAGES += \
    camera.galaxy5 \
    libcamera

## GalaxyParts support files
PRODUCT_COPY_FILES += \
    frameworks/native/data/etc/android.hardware.touchscreen.xml:system/galaxyparts/etc/permissions/android.hardware.touchscreen.xml \
    frameworks/native/data/etc/android.hardware.touchscreen.multitouch.distinct.xml:system/galaxyparts/etc/permissions/android.hardware.touchscreen.multitouch.distinct.xml

## Keypad
PRODUCT_COPY_FILES += \
    device/samsung/galaxy5/prebuilt/usr/idc/europa_keypad0.idc:system/usr/idc/europa_keypad0.idc

## Sensors
PRODUCT_PACKAGES += \
    g5sensord \
    sensors.galaxy5

## Touchscreen (GT-I5508 resistive)
PRODUCT_COPY_FILES += \
    device/samsung/galaxy5/prebuilt/usr/idc/msm_touchscreen.idc:system/usr/idc/msm_touchscreen.idc

# Inherit products (Most specific first)
# galaxy5 blobs > samsung common(device/vendor) > other blobs
$(call inherit-product, vendor/samsung/galaxy5/vendor_blobs.mk)
$(call inherit-product, device/samsung/msm7x27-common/common.mk)
$(call inherit-product, vendor/samsung/msm7x27-common/vendor.mk)

## LDPI assets
PRODUCT_AAPT_CONFIG := normal mdpi ldpi
PRODUCT_AAPT_PREF_CONFIG := ldpi
PRODUCT_LOCALES := en_US en_IN fr_FR it_IT es_ES et_EE de_DE nl_NL cs_CZ pl_PL ja_JP \
    zh_TW zh_CN zh_HK ru_RU ko_KR nb_NO es_US da_DK el_GR tr_TR pt_PT pt_BR rm_CH sv_SE \
    bg_BG ca_ES en_GB fi_FI hr_HR hu_HU in_ID lt_LT lv_LV ro_RO sk_SK sl_SI sr_RS uk_UA \
    vi_VN tl_PH sw_TZ ms_MY af_ZA zu_ZA en_XA ar_XB fr_CA mn_MN az_AZ

$(call inherit-product, device/ldpi-common/ldpi.mk)

## Inherit overlays  (Most specific last)
DEVICE_PACKAGE_OVERLAYS += device/samsung/galaxy5/overlay
