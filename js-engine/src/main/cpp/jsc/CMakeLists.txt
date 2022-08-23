cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)

find_package(fbjni REQUIRED CONFIG)

add_library(jsc-runtime
        SHARED
        JSCRuntime.cpp
        OnLoad.cpp)

target_link_libraries(jsc-runtime
        fbjni::fbjni
        jsc
        javascript-lib)

target_include_directories(jsc-runtime
        PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}
        ${root_dir}/node_modules/jsc-android/dist/include)

target_compile_options(jsc-runtime PRIVATE -fexceptions -frtti -Oz -fvisibility=hidden -fvisibility-inlines-hidden -g)

