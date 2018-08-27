//
// Created by anchun on 2018/8/22.
//

#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <sys/types.h>

#include "work.h"
#include "common.h"
#include "rule.h"

void run(const struct config * config, const int mode) {

    setCommon(config);

    if (mode == JUDGE && config->rule != NULL) {
        if (strcmp("c", config->rule) == 0) {
            init_c_rule(config->exec);
        } else if (strcmp("common", config->rule) == 0) {
            init_common_rule(config->exec);
        } else {
            exit(RULE_NAME_ERROR);
        }
    }

    execve(config->exec, config->args, config->env);
    exit(WORK_RUN_ERROR);
}

void monitor(const struct config * config, struct result * result, const int mode) {

    struct timeval start, end;
    gettimeofday(&start, NULL);

    pid_t child = fork();

    if (child < 0) {
        exit(FORK_ERROR);
    } else if (child == 0) {
        run(config, mode);
    } else {
        pthread_t thread;
        if (config->real_time_limit != UNLIMITED) {
            struct timeout_config timeout_config;
            timeout_config.interval = config->real_time_limit;
            timeout_config.pid = child;
            if (pthread_create(&thread, NULL, timeout_monitor, (void *) &timeout_config) != 0) {
                kill(child, SIGKILL);
                exit(MONITOR_ERROR);
            }
        }

        int status, signal = 0;
        struct rusage usage;
        if (wait4(child, &status, WSTOPPED, &usage) == -1) {
            kill(child, SIGKILL);
            exit(WAIT4_ERROR);
        }

        gettimeofday(&end, NULL);
        result->real_time = (int) (end.tv_sec * 1000 + end.tv_usec / 1000 - start.tv_sec * 1000 - start.tv_usec / 1000);
        result->cpu_time = (int) (usage.ru_utime.tv_sec * 1000 + usage.ru_utime.tv_usec / 1000);
        result->memory = usage.ru_maxrss * KB;
        result->status = WEXITSTATUS(status);

        if (WIFSIGNALED(status) != 0) {
            signal = WTERMSIG(status);
        }

        if (signal == SIGUSR1) {
            result->code = INTERVAL_ERROR;
            return;
        }

        if (result->status || signal) {
            result->code = RUNTIME_ERROR;
            return;
        }

        if (config->memory_limit != UNLIMITED && result->memory > config->memory_limit) {
            result->code = OUT_OF_MEMORY;
            return;
        }

        if (config->real_time_limit != UNLIMITED && result->real_time > config->real_time_limit) {
            result->code = REAL_TIME_OUT;
            return;
        }

        if (config->cpu_time_limit != UNLIMITED && result->cpu_time > config->cpu_time_limit) {
            result->code = CPU_TIME_OUT;
            return;
        }
    }
}


