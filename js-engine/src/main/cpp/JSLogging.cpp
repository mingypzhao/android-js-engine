#include "JSLogging.h"
#include <android/log.h>
#include <fbjni/fbjni.h>

namespace jsengine {

    __attribute__((visibility("default"))) void reactAndroidLoggingHook(
            const std::string &message,
            android_LogPriority logLevel) {
        FBJNI_LOGE("javascript-lib", "%s", message.c_str());
//        FBJNI_LOGE(logLevel, "javascript-lib", "%s", message.c_str());
    }

    __attribute__((visibility("default"))) void reactAndroidLoggingHook(
            const std::string &message,
            unsigned int logLevel) {
        reactAndroidLoggingHook(
                message, static_cast<android_LogPriority>(logLevel + ANDROID_LOG_DEBUG));
    }

}
