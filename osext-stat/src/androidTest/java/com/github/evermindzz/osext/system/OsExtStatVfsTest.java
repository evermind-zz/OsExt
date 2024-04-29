package com.github.evermindzz.osext.system;

import android.content.Context;

import com.github.evermindzz.osext.system.Os;
import com.github.evermindzz.osext.system.StructStatVfs;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class OsExtStatVfsTest {

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void fstatvfsTest() throws IOException {
        OutputStreamWriter outputStream;
        FileInputStream inputStream;
        StructStatVfs stat1st;
        StructStatVfs stat2nd;
        String testfilename = "fstatvfsTestFile";
        FileDescriptor fd;
        int size1st = 100;
        int size2nd = 4096 * 40;

        // 1. create small file
        outputStream = openStream(testfilename);
        truncateAndWriteNewDataToStream(outputStream, "X", size1st);
        outputStream.close();
        inputStream = context.openFileInput(testfilename);
        fd = inputStream.getFD();
        stat1st = Os.fstatvfs(fd);
        inputStream.close();

        // 2. create bigger file
        outputStream = openStream(testfilename);
        appendDataToStream(outputStream, "y", size2nd);
        outputStream.close();
        inputStream = context.openFileInput(testfilename);
        fd = inputStream.getFD(); // remove fd = here to get the Exception
        stat2nd = Os.fstatvfs(fd);
        inputStream.close();

        // 3. compare if there are now less free blocks
        assertThat(stat2nd.f_bavail, Matchers.lessThan(stat1st.f_bavail));
    }

    @Test
    public void statvfsTest() throws IOException {
        StructStatVfs stat1st;
        StructStatVfs stat2nd;
        OutputStreamWriter outputStream;
        String testfilename = "statvfsTestFile";
        String filePath = context.getFilesDir() + "/" + testfilename;
        int size1st = 100;
        int size2nd = 4096 * 20;

        // 1. create small file
        outputStream = openStream(testfilename);
        truncateAndWriteNewDataToStream(outputStream, "X", size1st);
        outputStream.close();
        stat1st = Os.statvfs(filePath);

        // 2. create bigger file
        outputStream = openStream(testfilename);
        appendDataToStream(outputStream, "y", size2nd);
        outputStream.close();
        stat2nd = Os.statvfs(filePath);

        // 3. compare if there are now less free blocks
        assertThat(stat2nd.f_bavail, Matchers.lessThan(stat1st.f_bavail));
    }

    // helper methods
    private void truncateAndWriteNewDataToStream(
            OutputStreamWriter outputStream,
            String data,
            int repeat) throws IOException {

        outputStream.write(data);
        repeat--;
        while (repeat-- > 0) {
            outputStream.append(data);
        }
    }

    private void appendDataToStream(
            OutputStreamWriter outputStream,
            String data,
            int repeat) throws IOException {

        while (repeat-- > 0) {
            outputStream.append(data);
        }
    }

    private OutputStreamWriter openStream(String testfilename) throws FileNotFoundException {
        return new OutputStreamWriter(context.openFileOutput(testfilename, Context.MODE_PRIVATE));
    }
}