cmake_minimum_required(VERSION 3.10)
project(judger)

set(CMAKE_C_STANDARD 11)

aux_source_directory(. DIR_SRC)

set(CMAKE_C_FLAGS_DEBUG "-g -Wall -Werror -O3 -pthread")

LINK_LIBRARIES(seccomp)

add_executable(judge ${DIR_SRC})