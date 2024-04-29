#include <string.h>
#include <stdlib.h>
#include <sys/errno.h>

#include "exceptions.h"
#include "common.h"

jint throwNoClassDefError(JNIEnv *env, const char *message) {
    const char *className = "java/lang/NoClassDefFoundError";
    jclass exClass = env->FindClass(className);
    return env->ThrowNew(exClass, message);
}

jint throwOutOfMemoryError(JNIEnv *env, const char *message) {
    const char *className = "java/lang/OutOfMemoryError";
    jclass exClass = getClass(env, className);
    return env->ThrowNew(exClass, message);
}

jint throwNoSuchMethodError(
        JNIEnv *env, const char *className, const char *methodName, const char *signature) {
    char *msgBuf;
    jint retCode;
    size_t msgBufSize;
    const char *exClassName = "java/lang/NoSuchMethodError" ;
    jclass exClass = getClass(env, exClassName);


    msgBufSize = strlen(className)
                 + strlen(methodName)
                 + strlen(signature) + 8;

    msgBuf = (char *)malloc(msgBufSize);
    if (msgBuf == NULL) {
        return throwOutOfMemoryError
                (env, "throwNoSuchMethodError: allocating msgBuf");
    }
    memset(msgBuf, 0, msgBufSize);

    strcpy(msgBuf, className);
    strcat(msgBuf, ".");
    strcat(msgBuf, methodName);
    strcat(msgBuf, ".");
    strcat(msgBuf, signature);

    retCode = env->ThrowNew(exClass, msgBuf);
    free (msgBuf);
    return retCode;
}

jint throwErrnoException(JNIEnv *env, const char *functionName, int errNo) {
    jint retCode;
    const char *javaExceptionClassName = "com/github/evermindzz/osext/system/ErrnoException";
    const char *ctorName = "<init>";
    const char *ctorSignature = "(Ljava/lang/String;I)V";

    jclass clazz = getClass(env, javaExceptionClassName);
    jmethodID ctor = env->GetMethodID(clazz, ctorName, ctorSignature);

    if (ctor == NULL) {
        return throwNoSuchMethodError(env, javaExceptionClassName, ctorName, ctorSignature);
    }

    jobject obj = env->NewObject(
            clazz, ctor, env->NewStringUTF(functionName), static_cast<jint>(errNo));

    retCode = env->Throw(static_cast<jthrowable>(obj));
    return retCode;
}