## Specify phone tech before including full_phone
$(call inherit-product, vendor/cm/config/gsm.mk)

# Inherit some common CM stuff.
$(call inherit-product, vendor/cm/config/tiny.mk)

# Inherit device configuration
$(call inherit-product, device/samsung/tass/full_tass.mk)

# Release name and versioning
PRODUCT_RELEASE_NAME := GalaxyMini
PRODUCT_VERSION_DEVICE_SPECIFIC := -GT-S5570
-include vendor/cm/config/common_versions.mk

## Device identifier. This must come after all inclusions
PRODUCT_DEVICE := tass
PRODUCT_NAME := cm_tass

## Bootanimation
TARGET_BOOTANIMATION_NAME := vertical-320x480
