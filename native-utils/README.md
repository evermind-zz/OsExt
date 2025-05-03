# native-utils Android library

`NativeUtils` is a Java utility class in the `com.github.evermindzz.osext.utils` package, enabling Android apps to perform low-level file system operations via JNI. It provides access to system calls like `stat`, `lstat`, `chmod`, `utimensat`, `readlink`, and `symlink` for precise control over file metadata, permissions, timestamps, and symbolic links. Compatible with Android API 19 (4.4 KitKat) and above, it operates within the app's sandbox (`/data/data/<package>/`) without requiring additional permissions.

## Features
- **File Metadata Retrieval**: Access detailed file or symlink metadata (size, mode, uid, gid, timestamps).
- **Permission Management**: Set Unix-style permissions for files and directories.
- **Timestamp Control**: Adjust access and modification timestamps with nanosecond precision.
- **Symbolic Link Handling**: Create, read, and verify symbolic links.
- **Native Integration**: Leverage JNI for performance and access to system calls unavailable in Java's `File` API.
- **Sandbox Compatibility**: Works within the app's sandbox, ensuring security and simplicity.

## Setup
### Prerequisites
- Android project targeting **API 19 (Android 4.4)** or higher.
- Gradle build system.
- Device or emulator for testing.

### Dependencies
Add the library to your `app/build.gradle`:
```gradle
dependencies {
    implementation 'com.github.evermind-zz.osext:native-utils:TAG'
}
```
Replace `TAG` with the desired version (e.g., `1.0.0`). The library includes the precompiled native library (`libnative-utils.so`) for supported architectures (`armeabi-v7a`, `arm64-v8a`, `x86`, `x86_64`).


## Usage
### Example
Use `NativeUtils` to perform file system operations. Below you'll find some
code snippets:

```java
// Check if native library is loaded
if (!NativeUtils.isNativeLoaded()) {
    Toast.makeText(this, "Native library not loaded", Toast.LENGTH_LONG).show();
    return;
}
```
```java
// Example: Get file metadata
String filePath = getFilesDir() + "/test.txt";
Stat stat = NativeUtils.getFileStat(filePath);
if (stat != null) {
    Toast.makeText(this, "File size: " + stat.getSize() + " bytes, Mode: " + Integer.toOctalString(stat.getMode()), Toast.LENGTH_LONG).show();
} else {
    Toast.makeText(this, "Failed to get file stat", Toast.LENGTH_LONG).show();
}
```
```java
// Example: Set permissions
String filePath = getFilesDir() + "/test.txt";
boolean success = NativeUtils.setFilePermissions(filePath, 0644); // rw-r--r--
Toast.makeText(this, success ? "Permissions set" : "Failed to set permissions", Toast.LENGTH_LONG).show();
});
```
```java
// Example: Create and verify symlink
String targetPath = getFilesDir() + "/test.txt";
String linkPath = getFilesDir() + "/test_link";
boolean created = NativeUtils.symlink(targetPath, linkPath);
if (created && NativeUtils.isSymlink(linkPath)) {
    String target = NativeUtils.readLink(linkPath);
    Toast.makeText(this, "Symlink created, target: " + target, Toast.LENGTH_LONG).show();
} else {
    Toast.makeText(this, "Failed to create symlink", Toast.LENGTH_LONG).show();
}
```

## Key Methods
- **getFileStat(String path)**: Retrieves metadata (size, mode, uid, gid, timestamps) for a file or symlink target using `stat64`. Returns `null` if the file doesn't exist or access is denied.
- **getFileLstat(String path)**: Retrieves metadata for a file or symlink itself using `lstat64`. Useful for symlink-specific metadata.
- **setFilePermissions(String path, int mode)**: Sets Unix permissions (e.g., `0644` for `rw-r--r--`) using `chmod`. Returns `false` on failure.
- **setFileTimes(String path, long atimeSec, long atimeNsec, long mtimeSec, long mtimeNsec)**: Sets access and modification timestamps with nanosecond precision using `utimensat`.
- **symlink(String target, String linkPath)**: Creates a symbolic link pointing to `target`. Returns `false` on failure.
- **readLink(String path)**: Returns the target path of a symlink or `null` if not a symlink.
- **isSymlink(String path)**: Checks if a path is a symbolic link using `lstat64`.
- **isNativeLoaded()**: Verifies if the native library (`libnative-utils.so`) is loaded.

## Notes
- **Compatibility**: Supports API 19+ with standard JNI and Android NDK.
- **Limitations**:
  - Limited to the app's sandbox (`/data/data/<package>/`) unless additional permissions are granted.
  - Symlink creation may fail on some Android versions due to SELinux restrictions.
  - Timestamp precision depends on the filesystem (nanoseconds may be truncated).
- **Performance**: Native calls are fast (<1ms for `stat`, `chmod`), but library loading may add startup overhead.
- **Security**: Validate paths to prevent unintended access. Native code handles errors robustly.
- **Debugging**: Check logcat for `UnsatisfiedLinkError` or use `ndk-stack` for native crashes.

## License
Provided as-is for Android projects. Ensure compliance with Android security guidelines and test thoroughly.
