LOCAL_PATH:= $(call my-dir)

#
# g5sensord - OuNao mod
# based on eCompass code by Mark Pedley / Freescale
#

include $(CLEAR_VARS)
LOCAL_SRC_FILES := g5sensord.c

LOCAL_MODULE := g5sensord

LOCAL_MODULE_TAGS := optional

LOCAL_C_INCLUDES       += $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr/include
LOCAL_ADDITIONAL_DEPENDENCIES := $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr

LOCAL_SHARED_LIBRARIES := libcutils

include $(BUILD_EXECUTABLE)
