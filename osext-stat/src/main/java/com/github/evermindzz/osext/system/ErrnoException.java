package com.github.evermindzz.osext.system;

public final class ErrnoException extends Exception {
    private final String functionName;

    /**
     * The errno value.
     */
    public final int errno;

    /**
     * Constructs an instance with the given function name and errno value.
     */
    public ErrnoException(String functionName, int errno) {
        this.functionName = functionName;
        this.errno = errno;
    }

    /**
     * Constructs an instance with the given function name, errno value and cause.
     */
    public ErrnoException(String functionName, int errno, Throwable cause) {
        super(cause);
        this.functionName = functionName;
        this.errno = errno;
    }

    @Override public String getMessage() {
        String errnoName = "errno " + errno;
        return functionName + " failed: " + errnoName;
    }
}
