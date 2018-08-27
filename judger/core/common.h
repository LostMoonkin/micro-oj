//
// Created by anchun on 2018/8/23.
//

#ifndef JUDGER_COMMON_H
#define JUDGER_COMMON_H

#include <sys/types.h>
#include <stdio.h>

#define MAX_ARGS 128
#define UNLIMITED -1
#define DEFAULT_UID 65535
#define DEFAULT_GID 65535
#define second 1000
#define COMPILE 1
#define JUDGE 2
#define KB 1 << 10
#define MB KB << 10
#define COMMON_MEMORY_LIMIT MB << 9

struct config {
    int cpu_time_limit;
    int real_time_limit;
    long memory_limit;
    long stack_limit;
    long output_limit;
    char * exec;
    char * args[MAX_ARGS];
    char * env[MAX_ARGS];
    char * input_file;
    char * output_file;
    char * error_file;
    char * rule;
    uid_t uid;
    gid_t gid;
};

struct result {
    int cpu_time;
    int real_time;
    long memory;
    int code;
    int status;
};

struct timeout_config {
    int pid;
    int interval;
};

enum {
    SUCCESS = 0,
    COMPILE_ERROR = 11,
    RUNTIME_ERROR = 12,
    PRESENTATION_ERROR = 13,
    CPU_TIME_OUT = 14,
    REAL_TIME_OUT = 15,
    OUT_OF_MEMORY = 16,
    INTERVAL_ERROR = 255,
    ARGS_ERROR = 254,
    RULE_ERROR = 253,
    FORK_ERROR = 252,
    DUP2_ERROR = 251,
    SET_R_LIMIT_ERROR = 250,
    MONITOR_ERROR = 249,
    WAIT4_ERROR = 248,
    USER_ERROR = 247,
    WORK_RUN_ERROR = 246,
    RULE_NAME_ERROR = 245
};

void setCommon(const struct config * config);

void initial_config(struct config * config);

void initial_result(struct result * result);

void *timeout_monitor(void *timeout_config);

#endif //JUDGER_COMMON_H
