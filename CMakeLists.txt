#cmake_minimum_required(VERSION 3.6)
project(temp_nir)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp graph.h)
add_executable(temp_nir ${SOURCE_FILES})