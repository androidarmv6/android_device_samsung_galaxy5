## Specify phone tech before including full_phone
$(call inherit-product, vendor/cm/config/gsm.mk)

# Inherit device configuration
$(call inherit-product, device/samsung/galaxy5/full_galaxy5.mk)

# Inherit some common CM stuff.
TARGET_SCREEN_HEIGHT := 320
TARGET_SCREEN_WIDTH := 240
$(call inherit-product, vendor/cm/config/mini.mk)

# Overrides
PRODUCT_NAME := cm_galaxy5
PRODUCT_DEVICE := galaxy5
PRODUCT_BRAND := Samsung
PRODUCT_MODEL := GT-I5500
PRODUCT_MANUFACTURER := Samsung
PRODUCT_CHARACTERISTICS := phone

PRODUCT_RELEASE_NAME := Galaxy5
PRODUCT_VERSION_DEVICE_SPECIFIC := -GT-I5500
