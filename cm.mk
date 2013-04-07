## Specify phone tech before including full_phone
$(call inherit-product, vendor/cm/config/gsm.mk)

# Inherit some common CM stuff.
$(call inherit-product, vendor/cm/config/mini.mk)

# Inherit device configuration
$(call inherit-product, device/samsung/galaxy5/full_galaxy5.mk)

# Release name and versioning
PRODUCT_RELEASE_NAME := Galaxy5
PRODUCT_VERSION_DEVICE_SPECIFIC := -GT-I5500
#-include vendor/cm/config/common_versions.mk

## Device identifier. This must come after all inclusions
PRODUCT_DEVICE := galaxy5
PRODUCT_NAME := cm_galaxy5

## Bootanimation
TARGET_BOOTANIMATION_NAME := vertical-240x320
