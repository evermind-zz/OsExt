
cmake_minimum_required(VERSION 3.4.1)

add_library(native-utils
        SHARED
        src/main/native/osext/utils/native_utils.c
)

#include_directories(
#)

#find_library(log-lib
#        log)

target_link_libraries(native-utils
        ${log-lib})

## library 'osexttest' only for 'androidTest' it is excluded in packaging for release
#add_library(osexttest
#        SHARED
#        src/main/native/osext/common.cpp
#        src/androidTest/native/osext/system/exceptions_test.cpp
#)

#target_link_libraries(osexttest
#        ${log-lib})
