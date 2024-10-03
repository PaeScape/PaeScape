package com.arlania.util.everythingrs.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private final List<String> fileList = new ArrayList<>();

    public ZipUtils() {
    }

    public static void init(String fileSource, String fileOutput, String zipName) {
        ZipUtils appZip = new ZipUtils();
        boolean sourceExists = (new File(fileSource)).exists();
        boolean outputExists = (new File(fileOutput)).exists();
        if (!fileOutput.equalsIgnoreCase("") && !fileSource.equalsIgnoreCase("")) {
            if (!sourceExists) {
                System.out.println("[EverythingRS] The folder you are trying to backup does not exist");
            } else {
                if (!outputExists) {
                    (new File(fileOutput)).mkdir();
                }

                appZip.generateFileList(new File(fileSource), fileSource);
                appZip.zipIt(fileOutput + File.separator + zipName, fileSource);
            }
        } else {
            System.out.println("[EverythingRS] Please make sure to enter the backup folder name and the output directory");
        }
    }

    public void zipIt(String zipFile, String fileSource) {
        byte[] buffer = new byte[1024];
        String source = (new File(fileSource)).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
            System.out.println("[EverythingRS] Creating local backup : " + zipFile);
            FileInputStream in = null;
            Iterator var9 = this.fileList.iterator();

            while (true) {
                String file;
                do {
                    if (!var9.hasNext()) {
                        zos.closeEntry();
                        System.out.println("[EverythingRS] Backup successfully created");
                        this.fileList.clear();
                        return;
                    }

                    file = (String) var9.next();
                } while (file.endsWith("[everythingrs].zip"));

                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);

                try {
                    in = new FileInputStream(fileSource + File.separator + file);

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
        } catch (IOException var27) {
            var27.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException var25) {
                var25.printStackTrace();
            }

        }

    }

    public void generateFileList(File node, String fileSource) {
        if (node.isFile()) {
            this.fileList.add(this.generateZipEntry(node.toString(), fileSource));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();

            for (int i = 0; i < subNote.length; ++i) {
                String filename = subNote[i];
                this.generateFileList(new File(node, filename), fileSource);
            }
        }

    }

    private String generateZipEntry(String file, String fileSource) {
        return file.substring(fileSource.length() + 1);
    }
}
