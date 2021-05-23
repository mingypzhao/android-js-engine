#include <string>

#include <fb/fbjni.h>
#include <fb/log.h>

#include "hermes/HermesRuntime.h"

using namespace facebook::jni;

namespace jsengine {

    __attribute__((visibility("default"))) extern "C" JNIEXPORT jint
    JNI_OnLoad(JavaVM *vm, void *reserved) {
        return initialize(vm, [] {
            HermesRuntimeHolder::registerNatives();
        });
    }

}
