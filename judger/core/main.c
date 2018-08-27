//
// Created by anchun on 2018/8/22.
//

#include <stdbool.h>
#include <stdlib.h>
#include <unistd.h>
#include "argtable3.h"
#include "work.h"
#include "common.h"

struct arg_lit *compile_mode, *judge_mode;
struct arg_int *cpu_time_limit, *real_time_limit, *memory_limit, *stack_limit, *output_limit, *uid, *gid;
struct arg_str *exec, *args, *env, *input_file, *output_file, *error_file, *rule;
struct arg_end *end;

int main(int argc, char *argv[]) {

//    if (getuid() != 0) {
//        printf("permission denied.");
//        return RULE_ERROR;
//    }

    void *arg_table[] = {
            compile_mode = arg_litn("cC", "compile", 0, 1, "compile mode"),
            judge_mode = arg_litn("jJ", "judge", 0, 1, "judge mode"),
            cpu_time_limit = arg_intn(NULL, "cpu_time_limit", "<n>", 0, 1, "cpu time limit (ms)"),
            real_time_limit = arg_intn(NULL, "real_time_limit", "<n>", 0, 1, "real time limit (ms)"),
            memory_limit = arg_intn(NULL, "memory_limit", "<n>", 0, 1, "memory limit (byte)"),
            stack_limit = arg_intn(NULL, "stack_limit", "<n>", 0, 1, "stack limit (byte)"),
            output_limit = arg_intn(NULL, "output_limit", "<n>", 0, 1, "output file size limit (byte)"),

            exec = arg_str1(NULL, "exec", "<str>", "exec command"),
            args = arg_strn(NULL, "args", "<str>", 0, 127, "exec args"),
            env = arg_strn(NULL, "env", "<str>", 0, 127, "exec envs"),

            input_file = arg_strn(NULL, "input_file", "<str>", 0, 1, "input file"),
            output_file = arg_strn(NULL, "output_file", "<str>", 0, 1, "output file"),
            error_file = arg_strn(NULL, "error_file", "<str>", 0, 1, "error file"),

            rule = arg_strn(NULL, "rule", "<str>", 0, 1, "rule name"),

            uid = arg_intn(NULL, "uid", "<n>", 0, 1, "uid"),
            gid = arg_intn(NULL, "gid", "<n>", 0, 1, "gid"),

            end = arg_end(20),
    };

    int exit_code = 0;
    char app[] = "judge";

    if (arg_parse(argc, argv, arg_table) > 0) {
        arg_print_errors(stdout, end, app);
        exit_code = ARGS_ERROR;
        goto exit;
    }

    int is_compile = compile_mode-> count > 0;
    int is_judge = judge_mode->count > 0;

    if (!(is_compile || is_judge) || (is_compile && is_judge)) {
        printf("Must choice a mode (-m or -j)");
        exit_code = ARGS_ERROR;
        goto exit;
    }

    struct config config;
    struct result result;

    initial_config(&config);
    initial_result(&result);

    config.exec = config.args[0] = (char *) *exec->sval;
    if (cpu_time_limit->count > 0) {
        config.cpu_time_limit = *cpu_time_limit->ival;
    }
    if (real_time_limit->count > 0) {
        config.real_time_limit = *real_time_limit->ival;
    }
    if (memory_limit->count > 0) {
        config.memory_limit = *memory_limit->ival;
    }
    if (stack_limit->count > 0) {
        config.stack_limit = *stack_limit->ival;
    }
    if (output_limit->count > 0) {
        config.output_limit = *output_limit->ival;
    }

    if (input_file->count > 0) {
        config.input_file = (char *) input_file->sval[0];
    }
    if (output_file->count > 0) {
        config.output_file = (char *) output_file->sval[0];
    }
    if (error_file->count > 0) {
        config.error_file = (char *) error_file->sval[0];
    }
    if (rule->count > 0) {
        config.rule = (char *) rule->sval[0];
    }

    int i = 1;
    for (; i < args->count + 1; i++) {
        config.args[i] = (char *) args->sval[i - 1];
    }
    config.args[i] = NULL;
    for (i = 1; i < env->count + 1; i++) {
        config.env[i] = (char *) env->sval[i - 1];
    }
    config.env[i] = NULL;

    if (uid->count > 0) {
        config.uid = (uid_t) *uid->ival;
    }
    if (gid->count > 0) {
        config.gid = (gid_t) *gid->ival;
    }


    monitor(&config, &result, is_compile ? COMPILE : JUDGE);
    printf("{\n"
           "    \"cpu_time\": %d,\n"
           "    \"real_time\": %d,\n"
           "    \"memory\": %ld,\n"
           "    \"code\": %d,\n"
           "    \"status\": %d,\n"
           "}",
           result.cpu_time,
           result.real_time,
           result.memory,
           result.code,
           result.status);

    exit:
    arg_freetable(arg_table, sizeof(arg_table) / sizeof(arg_table[0]));
    return exit_code;
}