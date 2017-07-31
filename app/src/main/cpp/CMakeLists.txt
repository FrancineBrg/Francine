cmake_minimum_required(VERSION 3.4.1)

add_library(otpjni SHARED
            otpjni.cpp
            otp.c
            otp.h)

# Include libraries needed for otpjni lib
target_link_libraries(otpjni
                      android
                      log)