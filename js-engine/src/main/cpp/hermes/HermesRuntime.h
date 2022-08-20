#include <string>
#include <memory>

#include <fbjni/fbjni.h>

#include <hermes/hermes.h>

#include "JavaScriptRuntimeHolder.h"

using namespace facebook::jni;

namespace jsengine {
    class HermesRuntimeHolder
            : public facebook::jni::HybridClass<HermesRuntimeHolder, JavaScriptRuntimeHolder> {
    public:

        static constexpr auto kJavaDescriptor =
                "Lcom/jsengine/hermes/HermesRuntime;";

        static facebook::jni::local_ref<jhybriddata> initHybridDefaultConfig(
                facebook::jni::alias_ref<jclass>) {
            return makeCxxInstance();
        }

        std::shared_ptr<facebook::jsi::Runtime> getJavaScriptRuntime(){
            return facebook::hermes::makeHermesRuntime();
        }

        static void registerNatives() {
            registerHybrid(
                    {
                            makeNativeMethod(
                                    "initHybridDefaultConfig",
                                    HermesRuntimeHolder::initHybridDefaultConfig)
                    });
        }

    private:
        friend HybridBase;
        using HybridBase::HybridBase;
    };
}
