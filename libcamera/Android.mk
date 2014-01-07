LOCAL_PATH := $(call my-dir)

## Make libcamera

# When zero we link against libmmcamera; when 1, we dlopen libmmcamera.
DLOPEN_LIBMMCAMERA:=1
DLOPEN_LIBCAMERA  :=0

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional

LOCAL_PRELINK_MODULE := false

LOCAL_SRC_FILES:= QualcommCameraHardware.cpp
LOCAL_SRC_FILES+= QcomCamera.cpp

LOCAL_CFLAGS:= -DDLOPEN_LIBMMCAMERA=$(DLOPEN_LIBMMCAMERA)
LOCAL_CFLAGS+= -DDLOPEN_LIBCAMERA=$(DLOPEN_LIBCAMERA)

## Can be raised to 6 to improve framerate, at the cost of allocating
## more ADSP memory. Use 0xa68000 as pool size in kernel to test
LOCAL_CFLAGS+= -DNUM_PREVIEW_BUFFERS=4 -D_ANDROID_

LOCAL_C_INCLUDES       := frameworks/base/services \
                          frameworks/av/include \
                          hardware/libhardware/include

LOCAL_C_INCLUDES+= \
    $(TARGET_OUT_HEADERS)/mm-camera \
    $(TARGET_OUT_HEADERS)/mm-still/jpeg \

LOCAL_C_INCLUDES       += $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr/include
LOCAL_ADDITIONAL_DEPENDENCIES := $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr

LOCAL_SHARED_LIBRARIES:= libutils libui libcamera_client liblog libcutils

LOCAL_SHARED_LIBRARIES+= libbinder
ifneq ($(DLOPEN_LIBMMCAMERA),1)
LOCAL_SHARED_LIBRARIES+= liboemcamera
else
LOCAL_SHARED_LIBRARIES+= libdl
endif

ifeq ($(DLOPEN_LIBCAMERA),1)
LOCAL_SHARED_LIBRARIES+= libdl
endif

ifneq ($(TARGET_QCOM_DISPLAY_VARIANT),)
    DISPLAY := display-$(TARGET_QCOM_DISPLAY_VARIANT)
else
    DISPLAY := display
endif

ifeq ($(BOARD_USES_QCOM_HARDWARE),true)
	LOCAL_CFLAGS += -DQCOM_HARDWARE
endif

ifeq ($(BOARD_USES_QCOM_LEGACY_CAM_PARAMS),true)
	LOCAL_CFLAGS += -DQCOM_LEGACY_CAM_PARAMS
	LOCAL_CFLAGS += -DUSE_GETBUFFERINFO
endif

ifeq ($(TARGET_BOARD_PLATFORM),msm7x27)
	LOCAL_CFLAGS += -DPREVIEW_MSM7K
	LOCAL_CFLAGS += -O3
endif

ifneq ($(TARGET_CAMERA_SENSOR_SIZE),)
	LOCAL_CFLAGS += -DSENSOR_SIZE_$(TARGET_CAMERA_SENSOR_SIZE)
else
	LOCAL_CFLAGS += -DSENSOR_SIZE_2MP
endif

LOCAL_C_INCLUDES       += hardware/qcom/$(DISPLAY)/libgralloc

LOCAL_MODULE_TAGS      := optional
LOCAL_MODULE_PATH      := $(TARGET_OUT_SHARED_LIBRARIES)/hw
LOCAL_MODULE           := camera.$(TARGET_BOOTLOADER_BOARD_NAME)

include $(BUILD_SHARED_LIBRARY)


