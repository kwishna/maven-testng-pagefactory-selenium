package org.example.utils;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

import static org.example.utils.Constants.*;


public class ZipUtils {
    public static void zip() {
        ZipUtil.pack(new File(EXTENT_REPORT_FOLDER_PATH), new File(ZIPPED_EXTENT_REPORTS_FOLDER_NAME));
    }
}
