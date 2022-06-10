package com.sages.project2.commons;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {

    public static byte[] readfileAsBytes(File file) throws IOException {
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
            byte[] bytes = new byte[(int) accessFile.length()];
            accessFile.readFully(bytes);
            return bytes;
        }
    }

    public static void writeFile(byte[] bytes, String fileName) throws IOException {
        File file = new File(fileName);
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw")) {
            accessFile.write(bytes);
        }
    }
}
