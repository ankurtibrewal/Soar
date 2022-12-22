project(inferenceEngineBenchmark)

cmake_minimum_required(VERSION 3.16)

include_directories(.)

message(STATUS "Building Soar")

add_subdirectory(Core)
add_subdirectory(SoarCLI)
