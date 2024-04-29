//
// Created by evermind-zz on 30.04.24.
//

#include "common.h"
#include "system/exceptions.h"

jclass getClass(JNIEnv *env, const char *className){
    jclass clazz;

    clazz = env->FindClass(className);
    if (clazz == NULL) {
        throwNoClassDefError(env, className);
    }

    return clazz;
}
