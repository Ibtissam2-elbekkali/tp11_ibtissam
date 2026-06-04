package com.example.lab11.utils;

import android.content.Context;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class CacheStore_ibtissam {
    private CacheStore_ibtissam() {}

    public static void writeCache_ibtissam(Context context_ibtissam, String fileName_ibtissam, String content_ibtissam) throws Exception {
        File file_ibtissam = new File(context_ibtissam.getCacheDir(), fileName_ibtissam);
        Files.write(file_ibtissam.toPath(), content_ibtissam.getBytes(StandardCharsets.UTF_8));
    }

    public static int purgeCache_ibtissam(Context context_ibtissam) {
        File[] files_ibtissam = context_ibtissam.getCacheDir().listFiles();
        if (files_ibtissam == null) return 0;
        int deleted_ibtissam = 0;
        for (File f_ibtissam : files_ibtissam) {
            if (f_ibtissam.delete()) deleted_ibtissam++;
        }
        return deleted_ibtissam;
    }
}
