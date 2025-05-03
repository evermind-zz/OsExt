package com.github.evermindzz.osext.utils;

import java.io.Serializable;

/**
 * Represents complete file metadata from struct stat64, including symlink information.
 * Used for backup and restore operations, capturing all attributes provided by lstat64 or stat64.
 */
public class Stat implements Serializable {
    private static final long serialVersionUID = 1L;

    public long st_dev;          // Device ID
    public long st_ino;          // Inode number
    public int st_mode;          // File type and permissions
    public int st_nlink;         // Number of hard links
    public int st_uid;           // User ID
    public int st_gid;           // Group ID
    public long st_rdev;         // Device ID for special files
    public long st_size;         // File size (bytes)
    public int st_blksize;       // Block size for filesystem I/O
    public long st_blocks;       // Number of 512-byte blocks
    public long st_atime;        // Last access time (seconds)
    public long st_atimeNsec;    // Last access time (nanoseconds)
    public long st_mtime;        // Last modification time (seconds)
    public long st_mtimeNsec;    // Last modification time (nanoseconds)
    public long st_ctime;        // Last status change time (seconds)
    public long st_ctimeNsec;    // Last status change time (nanoseconds)
    public boolean isSymlink;    // True if the file is a symbolic link
    public String linkTarget;    // Target path of the symlink (null for non-symlinks)

    /**
     * Constructs a Stat object with complete metadata from struct stat64 (used by lstat64, stat64).
     *
     * @param st_dev Device ID.
     * @param st_ino Inode number.
     * @param st_mode File type and permissions (e.g., 0644).
     * @param st_nlink Number of hard links.
     * @param st_uid User ID of the file owner.
     * @param st_gid Group ID of the file.
     * @param st_rdev Device ID for special files.
     * @param st_size File size in bytes.
     * @param st_blksize Block size for filesystem I/O.
     * @param st_blocks Number of 512-byte blocks allocated.
     * @param st_atime Last access time (seconds since epoch).
     * @param st_atimeNsec Last access time (nanoseconds).
     * @param st_mtime Last modification time (seconds since epoch).
     * @param st_mtimeNsec Last modification time (nanoseconds).
     * @param st_ctime Last status change time (seconds since epoch).
     * @param st_ctimeNsec Last status change time (nanoseconds).
     * @param isSymlink True if the file is a symbolic link.
     * @param linkTarget Target path of the symlink (null for non-symlinks).
    jmethodID stat_constructor = (*env)->GetMethodID(env, stat_class, "<init>", "(JJIIIIJJIIJJJJJJZLjava/lang/String;)V");
     */
    public Stat(long st_dev, long st_ino, int st_mode, int st_nlink, int st_uid, int st_gid,
                long st_rdev, long st_size, int st_blksize, long st_blocks,
                long st_atime, long st_atimeNsec, long st_mtime, long st_mtimeNsec,
                long st_ctime, long st_ctimeNsec, boolean isSymlink, String linkTarget) {
        this.st_dev = st_dev;
        this.st_ino = st_ino;
        this.st_mode = st_mode;
        this.st_nlink = st_nlink;
        this.st_uid = st_uid;
        this.st_gid = st_gid;
        this.st_rdev = st_rdev;
        this.st_size = st_size;
        this.st_blksize = st_blksize;
        this.st_blocks = st_blocks;
        this.st_atime = st_atime;
        this.st_atimeNsec = st_atimeNsec;
        this.st_mtime = st_mtime;
        this.st_mtimeNsec = st_mtimeNsec;
        this.st_ctime = st_ctime;
        this.st_ctimeNsec = st_ctimeNsec;
        this.isSymlink = isSymlink;
        this.linkTarget = linkTarget;
    }
}