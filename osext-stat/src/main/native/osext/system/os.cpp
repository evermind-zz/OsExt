#include <jni.h>
#include <sys/statvfs.h>
#include <sys/errno.h>

#include "os.h"
#include "common.h"
#include "system/exceptions.h"
#include "portability.h"

static int getFDFromFileDescriptor(JNIEnv * env, jobject fileDescriptor) {
    jint fd = -1;
    jclass fdClass = env->FindClass("java/io/FileDescriptor");

    if (fdClass != NULL) {
        jfieldID fdClassDescriptorFieldID = env->GetFieldID(fdClass, "descriptor", "I");
        if (fdClassDescriptorFieldID != NULL && fileDescriptor != NULL) {
            fd = env->GetIntField(fileDescriptor, fdClassDescriptorFieldID);
        }
    }

    return fd;
}

static jobject instantiateJavaStructStatVfs(JNIEnv *env, const struct statvfs &sb) {
    const char *className = "com/github/evermindzz/osext/system/StructStatVfs";
    jclass clazz = getClass(env, className);
    static jmethodID ctor = env->GetMethodID(clazz, "<init>",
            "(JJJJJJJJJJJ)V");
    if (ctor == NULL) {
        return NULL;
    }

    return env->NewObject(clazz, ctor,
                          static_cast<jlong>(sb.f_bsize),
                          static_cast<jlong>(sb.f_frsize),
                          static_cast<jlong>(sb.f_blocks),
                          static_cast<jlong>(sb.f_bfree),
                          static_cast<jlong>(sb.f_bavail),
                          static_cast<jlong>(sb.f_files),
                          static_cast<jlong>(sb.f_ffree),
                          static_cast<jlong>(sb.f_favail),
                          static_cast<jlong>(sb.f_fsid),
                          static_cast<jlong>(sb.f_flag),
                          static_cast<jlong>(sb.f_namemax));
}

JNIEXPORT jobject JNICALL
Java_com_github_evermindzz_osext_system_Os_statvfs(
        JNIEnv* env, jclass, jstring path) {
    const char *thePath = env->GetStringUTFChars(path , NULL) ;
    struct statvfs sb;
    int rc = TEMP_FAILURE_RETRY(statvfs(thePath, &sb));
    if (rc == -1) {
        throwErrnoException(env, "statvfs", errno);
        return NULL;
    }
    return instantiateJavaStructStatVfs(env, sb);
}

JNIEXPORT jobject JNICALL
Java_com_github_evermindzz_osext_system_Os_fstatvfs(
        JNIEnv* env, jclass, jobject javaFd) {
    int fd = getFDFromFileDescriptor(env, javaFd);
    struct statvfs sb;
    int rc = TEMP_FAILURE_RETRY(fstatvfs(fd, &sb));
    if (rc == -1) {
        throwErrnoException(env, "fstatvfs", errno);
        return NULL;
    }
    return instantiateJavaStructStatVfs(env, sb);
}
