package com.github.evermindzz.osext.utils;

import java.io.IOException;

/**
 * Utility class providing native methods for file system operations on Android.
 * Uses JNI to interface with native code for low-level operations like stat, chmod, and symlink handling.
 * All methods may throw IOException on error (e.g., file not found, permission denied, native library failure).
 */
public class NativeUtils {
    static {
        try {
            System.loadLibrary("native-utils");
        } catch (UnsatisfiedLinkError e) {
            throw new RuntimeException("Failed to load native library: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves file or symlink metadata using lstat64, which does not follow symlinks.
     * Returns metadata for the symlink itself if the path is a symlink.
     *
     * @param path The absolute path to the file or symlink.
     * @return A {@link Stat} object containing file metadata (size, mode, uid, gid, timestamps, etc.).
     * @throws IOException If the operation fails (e.g., file does not exist, permission denied, null path, native library failure).
     */
    public static native Stat getFileLstat(String path) throws IOException;

    /**
     * Retrieves file metadata using stat64, which follows symlinks.
     * Returns metadata for the symlink's target if the path is a symlink.
     *
     * @param path The absolute path to the file or symlink.
     * @return A {@link Stat} object containing file metadata (size, mode, uid, gid, timestamps, etc.).
     * @throws IOException If the operation fails (e.g., file does not exist, permission denied, null path, native library failure).
     */
    public static native Stat getFileStat(String path) throws IOException;

    /**
     * Sets the permissions (mode) of a file or directory using chmod.
     *
     * @param path The absolute path to the file or directory.
     * @param mode The permission mode (e.g., 0644 for rw-r--r--).
     * @return true if the permissions were set successfully.
     * @throws IOException If the operation fails (e.g., file does not exist, permission denied, null path, native library failure).
     */
    public static native boolean setFilePermissions(String path, int mode) throws IOException;

    /**
     * Sets the access and modification timestamps of a file, directory, or symlink using utimensat.
     * Does not follow symlinks (applies to the symlink itself).
     *
     * @param path The absolute path to the file, directory, or symlink.
     * @param atimeSec Access time (seconds since epoch).
     * @param atimeNsec Access time (nanoseconds).
     * @param mtimeSec Modification time (seconds since epoch).
     * @param mtimeNsec Modification time (nanoseconds).
     * @return true if the timestamps were set successfully.
     * @throws IOException If the operation fails (e.g., file does not exist, permission denied, null path, native library failure).
     */
    public static native boolean setFileTimes(String path, long atimeSec, long atimeNsec, long mtimeSec, long mtimeNsec) throws IOException;

    /**
     * Checks if the native library is loaded successfully.
     *
     * @return true if the native library is loaded.
     * @throws IOException If the native library failed to load or the operation fails.
     */
    public static native boolean isNativeLoaded() throws IOException;

    /**
     * Reads the target of a symbolic link using readlink.
     *
     * @param path The absolute path to the symlink.
     * @return The target path of the symlink as a String.
     * @throws IOException If the operation fails (e.g., not a symlink, file does not exist, permission denied, null path, native library failure).
     */
    public static native String readLink(String path) throws IOException;

    /**
     * Creates a symbolic link using symlink.
     *
     * @param target The target path the symlink will point to.
     * @param linkPath The absolute path where the symlink will be created.
     * @return true if the symlink was created successfully.
     * @throws IOException If the operation fails (e.g., file exists, permission denied, null path, native library failure).
     */
    public static native boolean symlink(String target, String linkPath) throws IOException;

    /**
     * Checks if a path is a symbolic link using lstat64.
     *
     * @param path The absolute path to check.
     * @return true if the path is a symbolic link.
     * @throws IOException If the operation fails (e.g., file does not exist, permission denied, null path, native library failure).
     */
    public static native boolean isSymlink(String path) throws IOException;
}