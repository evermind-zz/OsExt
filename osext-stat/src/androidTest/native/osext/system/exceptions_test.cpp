#include <jni.h>
#include "system/exceptions.cpp"

extern "C"
JNIEXPORT void JNICALL
Java_com_github_evermindzz_osext_system_ExceptionsTest_throwNoClassDefError(JNIEnv *env, jclass clazz) {
    throwNoClassDefError(env,"test");
}

extern "C"
JNIEXPORT void JNICALL
Java_com_github_evermindzz_osext_system_ExceptionsTest_throwOutOfMemoryError(JNIEnv *env, jclass clazz) {
    throwOutOfMemoryError(env, "test");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_github_evermindzz_osext_system_ExceptionsTest_throwNoSuchMethodError(JNIEnv *env, jclass clazz) {
    throwNoSuchMethodError(env, "testClass", "testMethod", "testSignature");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_github_evermindzz_osext_system_ExceptionsTest_throwErrnoException(JNIEnv *env, jclass clazz) {
    throwErrnoException(env,"testFunction", 11);
}