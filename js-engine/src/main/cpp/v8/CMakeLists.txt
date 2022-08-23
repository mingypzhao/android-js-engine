cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)

find_package(fbjni REQUIRED CONFIG)

add_library(v8-runtime SHARED
        HostProxy.cpp
        JSIV8ValueConverter.cpp
        V8PointerValue.cpp
        V8Runtime.cpp
        OnLoad.cpp)

if(${ANDROID_ABI} STREQUAL "arm64-v8a" OR ${ANDROID_ABI} STREQUAL  "x86_64")
    target_compile_options(v8-runtime PRIVATE -DV8_COMPRESS_POINTERS)
endif()


target_link_libraries(v8-runtime
        v8android
        fbjni::fbjni
        javascript-lib
        -Wl,-hash-style=sysv)

target_include_directories(
        v8-runtime
        PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}
        ${root_dir}/node_modules/v8-android-nointl/dist/include)

target_compile_options(v8-runtime PRIVATE -fexceptions -frtti -Oz -fvisibility=hidden -fvisibility-inlines-hidden -g)
