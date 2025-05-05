package com.github.evermindzz.osext.utils;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Instrumentation tests for NativeUtils, validating native file system operations.
 */
@RunWith(AndroidJUnit4.class)
public class NativeUtilsTest {
    private Context context;
    private File testFile;
    private File symlink;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        testFile = new File(context.getFilesDir(), "test.txt");
        symlink = new File(context.getFilesDir(), "test_link");

        // Create test file
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write("test".getBytes());
        }

        // Create symlink
        try {
            NativeUtils.symlink(testFile.getAbsolutePath(), symlink.getAbsolutePath());
        } catch (IOException e) {
            fail("Failed to create symlink in setup: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        // Clean up test files
        if (symlink.exists()) {
            symlink.delete();
        }
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testIsNativeLoaded() {
        try {
            assertTrue("Native library should be loaded", NativeUtils.isNativeLoaded());
        } catch (IOException e) {
            fail("Unexpected IOException in isNativeLoaded: " + e.getMessage());
        }
    }

    @Test
    public void testGetFileStat() {
        try {
            Stat stat = NativeUtils.getFileStat(testFile.getAbsolutePath());
            assertNotNull("Stat should not be null for existing file", stat);
            assertEquals("File size should be 4 bytes", 4, stat.st_size);
            assertTrue("File should have read permissions", (stat.st_mode & 0400) != 0); // Owner read
            assertTrue("File should have write permissions", (stat.st_mode & 0200) != 0); // Owner write
        } catch (IOException e) {
            fail("Unexpected IOException in getFileStat: " + e.getMessage());
        }
    }

    @Test
    public void testGetFileLstat() {
        try {
            // Test on regular file
            Stat fileStat = NativeUtils.getFileLstat(testFile.getAbsolutePath());
            assertNotNull("Lstat should not be null for file", fileStat);
            assertEquals("File size should be 4 bytes", 4, fileStat.st_size);

            // Test on symlink
            Stat linkStat = NativeUtils.getFileLstat(symlink.getAbsolutePath());
            assertNotNull("Lstat should not be null for symlink", linkStat);
            assertTrue("Symlink should be identified as a link", (linkStat.st_mode & 0120000) == 0120000); // S_IFLNK
        } catch (IOException e) {
            fail("Unexpected IOException in getFileLstat: " + e.getMessage());
        }
    }

    @Test
    public void testSetFilePermissions() {
        try {
            // Set permissions to rw-r--r-- (0644)
            boolean success = NativeUtils.setFilePermissions(testFile.getAbsolutePath(), 0644);
            assertTrue("Setting permissions should succeed", success);
            Stat stat = NativeUtils.getFileStat(testFile.getAbsolutePath());
            assertEquals("Permissions should be 0644", 0644, stat.st_mode & 0777);

            // Set permissions to rw------- (0600)
            success = NativeUtils.setFilePermissions(testFile.getAbsolutePath(), 0600);
            assertTrue("Setting permissions should succeed", success);
            stat = NativeUtils.getFileStat(testFile.getAbsolutePath());
            assertEquals("Permissions should be 0600", 0600, stat.st_mode & 0777);
        } catch (IOException e) {
            fail("Unexpected IOException in setFilePermissions: " + e.getMessage());
        }
    }

    @Test
    public void testSetFileTimes() {
        try {
            // Set timestamps to a specific date (2025-05-04 12:00:00)
            Calendar calendar = Calendar.getInstance();
            calendar.set(2025, Calendar.MAY, 4, 12, 0, 0);
            long mtimeSec = calendar.getTimeInMillis() / 1000;
            long mtimeNsec = 0;
            long atimeSec = mtimeSec;
            long atimeNsec = 0;

            boolean success = NativeUtils.setFileTimes(testFile.getAbsolutePath(), atimeSec, atimeNsec, mtimeSec, mtimeNsec);
            assertTrue("Setting timestamps should succeed", success);

            Stat stat = NativeUtils.getFileStat(testFile.getAbsolutePath());
            assertEquals("Modification time should match", mtimeSec, stat.st_mtime);
            assertEquals("Modification time nanoseconds should match", mtimeNsec, stat.st_mtimeNsec);
            assertEquals("Access time should match", atimeSec, stat.st_atime);
            assertEquals("Access time nanoseconds should match", atimeNsec, stat.st_atimeNsec);
        } catch (IOException e) {
            fail("Unexpected IOException in setFileTimes: " + e.getMessage());
        }
    }

    @Test
    public void testSymlinkOperations() {
        try {
            // Verify symlink creation
            assertTrue("Symlink should exist", symlink.exists());
            assertTrue("Path should be a symlink", NativeUtils.isSymlink(symlink.getAbsolutePath()));

            // Verify symlink target
            String target = NativeUtils.readLink(symlink.getAbsolutePath());
            assertEquals("Symlink target should match", testFile.getAbsolutePath(), target);
        } catch (IOException e) {
            fail("Unexpected IOException in symlinkOperations: " + e.getMessage());
        }
    }

    @Test
    public void testNonExistentFile() {
        String nonExistentPath = context.getFilesDir() + "/nonexistent.txt";
        try {
            NativeUtils.getFileStat(nonExistentPath);
            fail("Expected IOException for non-existent file");
        } catch (IOException e) {
            assertTrue("IOException expected for getFileStat on non-existent file", e.getMessage().contains("stat64 failed"));
        }

        try {
            NativeUtils.setFilePermissions(nonExistentPath, 0644);
            fail("Expected IOException for non-existent file");
        } catch (IOException e) {
            assertTrue("IOException expected for setFilePermissions on non-existent file", e.getMessage().contains("chmod failed"));
        }
    }

    @Test
    public void testInvalidSymlink() {
        try {
            NativeUtils.readLink(testFile.getAbsolutePath());
            fail("Expected IOException for non-symlink");
        } catch (IOException e) {
            assertTrue("IOException expected for readLink on non-symlink", e.getMessage().contains("readlink failed"));
        }

        try {
            assertFalse("isSymlink should return false for non-symlink", NativeUtils.isSymlink(testFile.getAbsolutePath()));
        } catch (IOException e) {
            fail("Unexpected IOException in isSymlink for non-symlink: " + e.getMessage());
        }
    }
}