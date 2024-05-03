package com.github.evermindzz.osext.system;

import com.github.evermindzz.osext.system.ErrnoException;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ExceptionsTest {

    static {
        try {
            System.loadLibrary("osexttest");
        } catch (UnsatisfiedLinkError ignored) {
            System.exit(1);
        }
    }

    private static native void throwNoClassDefError();

    private static native void throwOutOfMemoryError();

    private static native void throwNoSuchMethodError();

    private static native void throwErrnoException() throws ErrnoException;

    @Test
    public void throwNoClassDefErrorTest() {
        try {
            throwNoClassDefError();
        } catch (NoClassDefFoundError e) {
            assertThat("passed", true);
            return;
        }
        assertThat("failed", false);
    }

    @Test
    public void throwNoSuchMethodErrorTest() {
        try {
            throwNoSuchMethodError();
        } catch (NoSuchMethodError e) {
            assertThat("passed", true);
            return;
        }
        assertThat("failed", false);
    }

    @Test
    public void throwErrnoExceptionTest() {
        try {
            throwErrnoException();
        } catch (ErrnoException e) {
            assertThat("passed", true);
            return;
        }
        assertThat("failed", false);
    }

    @Test
    public void throwOutOfMemoryErrorTest() {
        try {
            throwOutOfMemoryError();
        } catch (OutOfMemoryError e) {
            assertThat("passed", true);
            return;
        }
        assertThat("failed", false);
    }
}