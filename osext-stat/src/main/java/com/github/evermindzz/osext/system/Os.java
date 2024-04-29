package com.github.evermindzz.osext.system;

import java.io.FileDescriptor;

public class Os {

    static {
        try {
            System.loadLibrary("osext");
        } catch (UnsatisfiedLinkError ignored) {
            System.exit(1);
        }
    }

    private Os() {

    }

    public static native StructStatVfs statvfs(String path);

    public static native StructStatVfs fstatvfs(FileDescriptor fd);
}
