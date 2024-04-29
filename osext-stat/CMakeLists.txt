
cmake_minimum_required(VERSION 3.4.1)

add_library(osext
        SHARED
        src/main/native/osext/common.cpp
        src/main/native/osext/system/os.cpp
        src/main/native/osext/system/exceptions.cpp
)

include_directories(
        src/main/native/osext/
        src/main/native/osext/system
)

#find_library(log-lib
#        log)

target_link_libraries(osext
        ${log-lib})
