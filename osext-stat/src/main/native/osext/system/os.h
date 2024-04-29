#include <jni.h>
/* Header for class com_github_evermindzz_osext_Os */

#ifndef INCLUDED_COM_GITHUB_EVERMINDZZ_OSEXT_SYSTEM_OS_H
#define INCLUDED_COM_GITHUB_EVERMINDZZ_OSEXT_SYSTEM_OS_H
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_github_evermindzz_osext_Os
 * Method:    statvfs
 * Signature: (Ljava/lang/String;)Lcom/github/evermindzz/osext/StructStatVfs;
 */
JNIEXPORT jobject JNICALL Java_com_github_evermindzz_osext_system_Os_statvfs
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_github_evermindzz_osext_Os
 * Method:    fstatvfs
 * Signature: (Ljava/io/FileDescriptor;)Lcom/github/evermindzz/osext/StructStatVfs;
 */
JNIEXPORT jobject JNICALL Java_com_github_evermindzz_osext_system_Os_fstatvfs
  (JNIEnv *, jclass, jobject);

#ifdef __cplusplus
}
#endif
#endif
