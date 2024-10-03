package online.paescape.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileOperations {

    public static int TotalRead = 0;
    public static int TotalWrite = 0;
    public static int CompleteWrite = 0;

    public FileOperations() {
    }

    public static final byte[] ReadFile(String s) {
        try {
            File file = new File(s);
            int i = (int) file.length();
            byte[] abyte0 = new byte[i];
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
            datainputstream.readFully(abyte0, 0, i);
            datainputstream.close();
            TotalRead++;
            return abyte0;
        } catch (Exception exception) {
        }
        return null;
    }

    public static final void WriteFile(String s, byte[] abyte0) {
        try {
            (new File((new File(s)).getParent())).mkdirs();
            FileOutputStream fileoutputstream = new FileOutputStream(s);
            fileoutputstream.write(abyte0, 0, abyte0.length);
            fileoutputstream.close();
            TotalWrite++;
            CompleteWrite++;
        } catch (Throwable throwable) {
            System.out.println((new StringBuilder()).append("Write Error: ").append(s));
        }
    }

    public static boolean FileExists(String file) {
        File f = new File(file);
        return f.exists();
    }

    public static byte[] readFile(String name) {
        try {
            RandomAccessFile raf = new RandomAccessFile(name, "r");
            ByteBuffer buf = raf.getChannel().map(
                    FileChannel.MapMode.READ_ONLY, 0, raf.length());
            try {
                if (buf.hasArray()) {
                    return buf.array();
                } else {
                    byte[] array = new byte[buf.remaining()];
                    buf.get(array);
                    return array;
                }
            } finally {
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}