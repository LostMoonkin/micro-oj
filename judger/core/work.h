//
// Created by anchun on 2018/8/22.
//

#ifndef JUDGER_COMPILER_H
#define JUDGER_COMPILER_H

#include "common.h"

void run(const struct config * config, int mode);

void monitor(const struct config * config, struct result * result, int mode);

#endif //JUDGER_COMPILER_H
