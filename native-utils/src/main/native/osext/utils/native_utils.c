#include <jni.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <linux/stat.h>

/**
 * Throws a Java IOException with the given message and errno details.
 */
static void throwIOException(JNIEnv *env, const char *message) {
    jclass exceptionClass = (*env)->FindClass(env, "java/io/IOException");
    if (exceptionClass == NULL) return;
    char fullMessage[256];
    snprintf(fullMessage, sizeof(fullMessage), "%s: %s (errno: %d)", message, strerror(errno), errno);
    (*env)->ThrowNew(env, exceptionClass, fullMessage);
}

JNIEXPORT jboolean JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_isNativeLoaded(JNIEnv *env, jclass clazz) {
    return JNI_TRUE;
}

JNIEXPORT jobject JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_getFileLstat(JNIEnv *env, jclass clazz, jstring path) {
    if (path == NULL) {
        throwIOException(env, "Path is null");
        return NULL;
    }

    const char *path_str = (*env)->GetStringUTFChars(env, path, NULL);
    if (path_str == NULL) {
        throwIOException(env, "Failed to get path string");
        return NULL;
    }

    struct stat64 st;
    int result = lstat64(path_str, &st);
    if (result != 0) {
        throwIOException(env, "lstat64 failed");
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        return NULL;
    }

    jclass stat_class = (*env)->FindClass(env, "com/github/evermindzz/osext/utils/Stat");
    if (stat_class == NULL) {
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        throwIOException(env, "Failed to find Stat class");
        return NULL;
    }

    jmethodID stat_constructor = (*env)->GetMethodID(env, stat_class, "<init>", "(JJIIIIJJIJJJJJJJZLjava/lang/String;)V");
    if (stat_constructor == NULL) {
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        throwIOException(env, "Failed to find Stat constructor");
        return NULL;
    }

    jstring link_target = NULL;
    jboolean is_symlink = (st.st_mode & S_IFMT) == S_IFLNK;
    if (is_symlink) {
        char target[PATH_MAX];
        ssize_t len = readlink(path_str, target, sizeof(target) - 1);
        if (len == -1) {
            throwIOException(env, "readlink failed for symlink");
            (*env)->ReleaseStringUTFChars(env, path, path_str);
            return NULL;
        }
        target[len] = '\0';
        link_target = (*env)->NewStringUTF(env, target);
        if (link_target == NULL) {
            (*env)->ReleaseStringUTFChars(env, path, path_str);
            throwIOException(env, "Failed to create link target string");
            return NULL;
        }
    }

    jobject stat_obj = (*env)->NewObject(env, stat_class, stat_constructor,
                                         (jlong)st.st_dev,
                                         (jlong)st.st_ino,
                                         (jint)st.st_mode,
                                         (jint)st.st_nlink,
                                         (jint)st.st_uid,
                                         (jint)st.st_gid,
                                         (jlong)st.st_rdev,
                                         (jlong)st.st_size,
                                         (jlong)st.st_blocks,
                                         (jint)st.st_blksize,
                                         (jlong)st.st_atime,
                                         (jlong)st.st_atime_nsec,
                                         (jlong)st.st_mtime,
                                         (jlong)st.st_mtime_nsec,
                                         (jlong)st.st_ctime,
                                         (jlong)st.st_ctime_nsec,
                                         is_symlink,
                                         link_target);

    (*env)->ReleaseStringUTFChars(env, path, path_str);
    if (stat_obj == NULL) {
        throwIOException(env, "Failed to create Stat object");
    }
    return stat_obj;
}

JNIEXPORT jobject JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_getFileStat(JNIEnv *env, jclass clazz, jstring path) {
    if (path == NULL) {
        throwIOException(env, "Path is null");
        return NULL;
    }

    const char *path_str = (*env)->GetStringUTFChars(env, path, NULL);
    if (path_str == NULL) {
        throwIOException(env, "Failed to get path string");
        return NULL;
    }

    struct stat64 st;
    int result = stat64(path_str, &st);
    if (result != 0) {
        throwIOException(env, "stat64 failed");
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        return NULL;
    }

    jclass stat_class = (*env)->FindClass(env, "com/github/evermindzz/osext/utils/Stat");
    if (stat_class == NULL) {
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        throwIOException(env, "Failed to find Stat class");
        return NULL;
    }

    jmethodID stat_constructor = (*env)->GetMethodID(env, stat_class, "<init>", "(JJIIIIJJIJJJJJJJZLjava/lang/String;)V");
    if (stat_constructor == NULL) {
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        throwIOException(env, "Failed to find Stat constructor");
        return NULL;
    }

    jstring link_target = NULL;
    jboolean is_symlink = (st.st_mode & S_IFMT) == S_IFLNK;
    if (is_symlink) {
        char target[PATH_MAX];
        ssize_t len = readlink(path_str, target, sizeof(target) - 1);
        if (len == -1) {
            throwIOException(env, "readlink failed for symlink");
            (*env)->ReleaseStringUTFChars(env, path, path_str);
            return NULL;
        }
        target[len] = '\0';
        link_target = (*env)->NewStringUTF(env, target);
        if (link_target == NULL) {
            (*env)->ReleaseStringUTFChars(env, path, path_str);
            throwIOException(env, "Failed to create link target string");
            return NULL;
        }
    }

    jobject stat_obj = (*env)->NewObject(env, stat_class, stat_constructor,
                                         (jlong)st.st_dev,
                                         (jlong)st.st_ino,
                                         (jint)st.st_mode,
                                         (jint)st.st_nlink,
                                         (jint)st.st_uid,
                                         (jint)st.st_gid,
                                         (jlong)st.st_rdev,
                                         (jlong)st.st_size,
                                         (jlong)st.st_blocks,
                                         (jint)st.st_blksize,
                                         (jlong)st.st_atime,
                                         (jlong)st.st_atime_nsec,
                                         (jlong)st.st_mtime,
                                         (jlong)st.st_mtime_nsec,
                                         (jlong)st.st_ctime,
                                         (jlong)st.st_ctime_nsec,
                                         is_symlink,
                                         link_target);

    (*env)->ReleaseStringUTFChars(env, path, path_str);
    if (stat_obj == NULL) {
        throwIOException(env, "Failed to create Stat object");
    }
    return stat_obj;
}

JNIEXPORT jboolean JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_setFilePermissions(JNIEnv *env, jclass clazz, jstring path, jint mode) {
    if (path == NULL) {
        throwIOException(env, "Path is null");
        return JNI_FALSE;
    }

    const char *path_str = (*env)->GetStringUTFChars(env, path, NULL);
    if (path_str == NULL) {
        throwIOException(env, "Failed to get path string");
        return JNI_FALSE;
    }

    int result = chmod(path_str, mode);
    (*env)->ReleaseStringUTFChars(env, path, path_str);
    if (result != 0) {
        throwIOException(env, "chmod failed");
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

JNIEXPORT jboolean JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_setFileTimes(JNIEnv *env, jclass clazz, jstring path,
                                                                jlong atime_sec, jlong atime_nsec,
                                                                jlong mtime_sec, jlong mtime_nsec) {
    if (path == NULL) {
        throwIOException(env, "Path is null");
        return JNI_FALSE;
    }

    const char *path_str = (*env)->GetStringUTFChars(env, path, NULL);
    if (path_str == NULL) {
        throwIOException(env, "Failed to get path string");
        return JNI_FALSE;
    }

    struct timespec times[2];
    times[0].tv_sec = atime_sec;
    times[0].tv_nsec = atime_nsec;
    times[1].tv_sec = mtime_sec;
    times[1].tv_nsec = mtime_nsec;

    int result = utimensat(AT_FDCWD, path_str, times, AT_SYMLINK_NOFOLLOW);
    (*env)->ReleaseStringUTFChars(env, path, path_str);
    if (result != 0) {
        throwIOException(env, "utimensat failed");
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

JNIEXPORT jstring JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_readLink(JNIEnv *env, jclass clazz, jstring path) {
    if (path == NULL) {
        throwIOException(env, "Path is null");
        return NULL;
    }

    const char *path_str = (*env)->GetStringUTFChars(env, path, NULL);
    if (path_str == NULL) {
        throwIOException(env, "Failed to get path string");
        return NULL;
    }

    char target[PATH_MAX];
    ssize_t len = readlink(path_str, target, sizeof(target) - 1);
    if (len == -1) {
        throwIOException(env, "readlink failed");
        (*env)->ReleaseStringUTFChars(env, path, path_str);
        return NULL;
    }

    target[len] = '\0';
    jstring result = (*env)->NewStringUTF(env, target);
    (*env)->ReleaseStringUTFChars(env, path, path_str);
    if (result == NULL) {
        throwIOException(env, "Failed to create target string");
    }
    return result;
}

JNIEXPORT jboolean JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_symlink(JNIEnv *env, jclass clazz, jstring target, jstring link_path) {
    if (target == NULL || link_path == NULL) {
        throwIOException(env, "Target or link path is null");
        return JNI_FALSE;
    }

    const char *target_str = (*env)->GetStringUTFChars(env, target, NULL);
    if (target_str == NULL) {
        throwIOException(env, "Failed to get target string");
        return JNI_FALSE;
    }

    const char *link_path_str = (*env)->GetStringUTFChars(env, link_path, NULL);
    if (link_path_str == NULL) {
        (*env)->ReleaseStringUTFChars(env, target, target_str);
        throwIOException(env, "Failed to get link path string");
        return JNI_FALSE;
    }

    int result = symlink(target_str, link_path_str);
    (*env)->ReleaseStringUTFChars(env, target, target_str);
    (*env)->ReleaseStringUTFChars(env, link_path, link_path_str);
    if (result != 0) {
        throwIOException(env, "symlink failed");
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

JNIEXPORT jboolean JNICALL
Java_com_github_evermindzz_osext_utils_NativeUtils_isSymlink(JNIEnv *env, jclass clazz, jstring path) {
    if (path == NULL) {
        throwIOException(env, "Path is null");
        return JNI_FALSE;
    }

    const char *path_str = (*env)->GetStringUTFChars(env, path, NULL);
    if (path_str == NULL) {
        throwIOException(env, "Failed to get path string");
        return JNI_FALSE;
    }

    struct stat64 st;
    int result = lstat64(path_str, &st);
    (*env)->ReleaseStringUTFChars(env, path, path_str);
    if (result != 0) {
        throwIOException(env, "lstat64 failed");
        return JNI_FALSE;
    }
    return (st.st_mode & S_IFMT) == S_IFLNK;
}