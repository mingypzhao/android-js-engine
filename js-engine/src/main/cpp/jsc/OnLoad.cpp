#include <string>

#include <fbjni/fbjni.h>

#include "JSCRuntimeHolder.h"

using namespace facebook::jni;

namespace jsengine {

    __attribute__((visibility("default"))) extern "C" JNIEXPORT jint
    JNI_OnLoad(JavaVM *vm, void *reserved) {
        return initialize(vm, [] {
            JSCRuntimeHolder::registerNatives();
        });
    }

}
