//
// Created by anchun on 2018/8/24.
//

#include <stdio.h>
#include <stdlib.h>
#include <seccomp.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>


#include "rule.h"
#include "common.h"

int init_c_rule(char * exec) {
    int whitelist[] = {SCMP_SYS(read), SCMP_SYS(fstat),
                       SCMP_SYS(mmap), SCMP_SYS(mprotect),
                       SCMP_SYS(munmap), SCMP_SYS(uname),
                       SCMP_SYS(arch_prctl), SCMP_SYS(brk),
                       SCMP_SYS(access), SCMP_SYS(exit_group),
                       SCMP_SYS(close), SCMP_SYS(readlink),
                       SCMP_SYS(sysinfo), SCMP_SYS(write),
                       SCMP_SYS(writev), SCMP_SYS(lseek)};

    int whitelist_length = sizeof(whitelist) / sizeof(int);
    scmp_filter_ctx ctx = NULL;
    // load whitelist
    ctx = seccomp_init(SCMP_ACT_KILL);
    if (!ctx) {
         exit(RULE_ERROR);
    }
    for (int i = 0; i < whitelist_length; i++) {
        if (seccomp_rule_add(ctx, SCMP_ACT_ALLOW, whitelist[i], 0) != 0) {
            exit(RULE_ERROR);
        }
    }
    // add extra rule for execve
    if (seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(execve), 1, SCMP_A0(SCMP_CMP_EQ, (scmp_datum_t) exec)) != 0) {
        exit(RULE_ERROR);
    }
    // do not allow "w" and "rw"
    if (seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(open), 1, SCMP_CMP(1, SCMP_CMP_MASKED_EQ, O_WRONLY | O_RDWR, 0)) != 0) {
        exit(RULE_ERROR);
    }
    if (seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(openat), 1, SCMP_CMP(2, SCMP_CMP_MASKED_EQ, O_WRONLY | O_RDWR, 0)) != 0) {
        exit(RULE_ERROR);
    }
    if (seccomp_load(ctx) != 0) {
        exit(RULE_ERROR);
    }
    seccomp_release(ctx);
    return 0;
}

int init_common_rule(char * exec) {
    int blacklist[] = {SCMP_SYS(clone),
                                SCMP_SYS(fork), SCMP_SYS(vfork),
                                SCMP_SYS(kill),
#ifdef __NR_execveat
            SCMP_SYS(execveat)
#endif
    };
    int blacklist_length = sizeof(blacklist) / sizeof(int);
    scmp_filter_ctx ctx = NULL;
    // load rules
    ctx = seccomp_init(SCMP_ACT_ALLOW);
    if (!ctx) {
        exit(RULE_ERROR);
    }
    for (int i = 0; i < blacklist_length; i++) {
        if (seccomp_rule_add(ctx, SCMP_ACT_KILL, blacklist[i], 0) != 0) {
            exit(RULE_ERROR);
        }
    }
    // use SCMP_ACT_KILL for socket, python will be killed immediately
    if (seccomp_rule_add(ctx, SCMP_ACT_ERRNO(EACCES), SCMP_SYS(socket), 0) != 0) {
        exit(RULE_ERROR);
    }
    // add extra rule for execve
    if (seccomp_rule_add(ctx, SCMP_ACT_KILL, SCMP_SYS(execve), 1, SCMP_A0(SCMP_CMP_NE, (scmp_datum_t) exec)) != 0) {
        exit(RULE_ERROR);
    }
    // do not allow "w" and "rw" using open
    if (seccomp_rule_add(ctx, SCMP_ACT_KILL, SCMP_SYS(open), 1, SCMP_CMP(1, SCMP_CMP_MASKED_EQ, O_WRONLY, O_WRONLY)) != 0) {
        exit(RULE_ERROR);
    }
    if (seccomp_rule_add(ctx, SCMP_ACT_KILL, SCMP_SYS(open), 1, SCMP_CMP(1, SCMP_CMP_MASKED_EQ, O_RDWR, O_RDWR)) != 0) {
        exit(RULE_ERROR);
    }
    // do not allow "w" and "rw" using openat
    if (seccomp_rule_add(ctx, SCMP_ACT_KILL, SCMP_SYS(openat), 1, SCMP_CMP(2, SCMP_CMP_MASKED_EQ, O_WRONLY, O_WRONLY)) != 0) {
        exit(RULE_ERROR);
    }
    if (seccomp_rule_add(ctx, SCMP_ACT_KILL, SCMP_SYS(openat), 1, SCMP_CMP(2, SCMP_CMP_MASKED_EQ, O_RDWR, O_RDWR)) != 0) {
        exit(RULE_ERROR);
    }

    if (seccomp_load(ctx) != 0) {
        exit(RULE_ERROR);
    }
    seccomp_release(ctx);
    return 0;
}