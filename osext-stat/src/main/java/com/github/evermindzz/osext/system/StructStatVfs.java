package com.github.evermindzz.osext.system;

public final class StructStatVfs {
    /**
     * File system block size (used for block counts).
     */
    public final long f_bsize; /*unsigned long*/
    /**
     * Fundamental file system block size.
     */
    public final long f_frsize; /*unsigned long*/
    /**
     * Total block count.
     */
    public final long f_blocks; /*fsblkcnt_t*/
    /**
     * Free block count.
     */
    public final long f_bfree; /*fsblkcnt_t*/
    /**
     * Free block count available to non-root.
     */
    public final long f_bavail; /*fsblkcnt_t*/
    /**
     * Total file (inode) count.
     */
    public final long f_files; /*fsfilcnt_t*/
    /**
     * Free file (inode) count.
     */
    public final long f_ffree; /*fsfilcnt_t*/
    /**
     * Free file (inode) count available to non-root.
     */
    public final long f_favail; /*fsfilcnt_t*/
    /**
     * File system id.
     */
    public final long f_fsid; /*unsigned long*/
    /**
     * Bit mask of ST_* flags.
     */
    public final long f_flag; /*unsigned long*/
    /**
     * Maximum filename length.
     */
    public final long f_namemax; /*unsigned long*/

    /**
     * Constructs an instance with the given field values.
     */
    public StructStatVfs(long f_bsize, long f_frsize, long f_blocks, long f_bfree, long f_bavail,
                         long f_files, long f_ffree, long f_favail,
                         long f_fsid, long f_flag, long f_namemax) {
        this.f_bsize = f_bsize;
        this.f_frsize = f_frsize;
        this.f_blocks = f_blocks;
        this.f_bfree = f_bfree;
        this.f_bavail = f_bavail;
        this.f_files = f_files;
        this.f_ffree = f_ffree;
        this.f_favail = f_favail;
        this.f_fsid = f_fsid;
        this.f_flag = f_flag;
        this.f_namemax = f_namemax;
    }
}