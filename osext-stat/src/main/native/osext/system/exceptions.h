#ifndef EXCEPTIONS_H
#define EXCEPTIONS_H

#include <jni.h>

jint throwNoClassDefError(JNIEnv *env, const char *message);
jint throwErrnoException(JNIEnv* env, const char* functionName, int errNo);
#endif // EXCEPTIONS_H
