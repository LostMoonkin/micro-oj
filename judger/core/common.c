//
// Created by anchun on 2018/8/23.
//

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <signal.h>
#include <sys/resource.h>

#include "common.h"

void setCommon(const struct config * config) {

    // set stack limit
    if (config->stack_limit != UNLIMITED) {
        struct rlimit stack_limit;
        stack_limit.rlim_cur = stack_limit.rlim_max = (rlim_t) config->stack_limit;
        if (setrlimit(RLIMIT_STACK, &stack_limit) != 0) {
            exit(SET_R_LIMIT_ERROR);
        }
    }

    // set memory limit
    struct rlimit memory_limit;
    memory_limit.rlim_cur = memory_limit.rlim_max = (rlim_t) COMMON_MEMORY_LIMIT;
    if (setrlimit(RLIMIT_AS, &memory_limit) != 0) {
        exit(SET_R_LIMIT_ERROR);
    }


    // set cpu limit
    if (config->cpu_time_limit != UNLIMITED) {
        struct rlimit cpu_limit;
        cpu_limit.rlim_cur = cpu_limit.rlim_max = (rlim_t) config->cpu_time_limit;
        if (setrlimit(RLIMIT_CPU, &cpu_limit) != 0) {
            exit(SET_R_LIMIT_ERROR);
        }
    }

    // set output size
    if (config->output_limit != UNLIMITED) {
        struct rlimit output_limit;
        output_limit.rlim_cur = output_limit.rlim_max = (rlim_t) config->output_limit;
        if (setrlimit(RLIMIT_FSIZE, &output_limit) != 0) {
            exit(SET_R_LIMIT_ERROR);
        }
    }

    // set input file
    if (config->input_file != NULL) {
        FILE *input = fopen(config->input_file, "w");
        if (input == NULL) {
            exit(DUP2_ERROR);
        }
        if (dup2(fileno(input), STDIN_FILENO) == -1) {
            exit(DUP2_ERROR);
        }
    }

    // set output file
    if (config->output_file != NULL) {
        FILE *output = fopen(config->output_file, "w");
        if (output == NULL) {
            exit(DUP2_ERROR);
        }
        if (dup2(fileno(output), STDOUT_FILENO) == -1) {
            exit(DUP2_ERROR);
        }
    }
    // set error file
    if (config->output_file != NULL) {
        FILE *error = fopen(config->error_file, "w");
        if (error == NULL) {
            exit(DUP2_ERROR);
        }
        if (dup2(fileno(error), STDERR_FILENO) == -1) {
            exit(DUP2_ERROR);
        }
    }

    // set gid
    if (setgid(config->gid) != 0) {
        exit(USER_ERROR);
    }

    // set uid
    if (setuid(config->uid) != 0) {
        exit(USER_ERROR);
    }

}

void initial_config(struct config * config) {
    config->cpu_time_limit = UNLIMITED;
    config->real_time_limit = UNLIMITED;
    config->memory_limit = UNLIMITED;
    config->stack_limit = UNLIMITED;
    config->gid = DEFAULT_GID;
    config->uid = DEFAULT_UID;
    config->input_file = "/dev/stdin";
    config->output_file = "/dev/stdout";
    config->error_file = "/dev/stderr";
    config->exec = config->rule = NULL;
}

void initial_result(struct result * result) {
    result->memory = result->code = result->cpu_time = result->real_time = result->status = 0;
}

void *timeout_monitor(void * config) {
    pid_t pid = ((struct timeout_config *) config)->pid;
    int interval = ((struct timeout_config *) config)->interval;

    if (pthread_detach(pthread_self()) != 0) {
        kill(pid, SIGKILL);
        return NULL;
    }

    if (sleep((unsigned int) ((interval + second) / second)) != 0) {
        kill(pid, SIGKILL);
        return NULL;
    }

    kill(pid, SIGKILL);
    return NULL;
}