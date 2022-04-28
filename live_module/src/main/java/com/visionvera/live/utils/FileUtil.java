package com.visionvera.live.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @Desc 文件相关工具类
 * @Author yemh
 * @Date 2019/4/12 14:27
 */
public class FileUtil {
    /**
     * 缓存主目录
     */
    public static String FILECACHEPATH = ".VisionDoctor";

    /**
     * 图片缓存目录
     */
    public static final String ImageCachePath = SdCard() + "images/";

    /**
     * 文件缓存目录
     */
    public static final String FileCachePath = SdCard() + "files/";

    /**
     * Log日志缓存目录
     */
    public static final String LogCachePath = SdCard() + "logs/";

    /**
     * 判断SD卡是否存在并返回SD卡路径
     *
     * @return
     */
    public static String SdCard() {
        String cachePath = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cachePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + FILECACHEPATH + File.separator;
        } else {
            cachePath = "/data/data/com.visionvera.psychologist"
                    + File.separator + FILECACHEPATH + File.separator;
        }
        return cachePath;
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除指定目录的文件
     *
     * @param path 文件目录
     */
    public static void delete(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }

        for (File item : files) {
            if (!item.delete()) {
                delete(item.getPath());
            }
        }
        file.delete();

    }

    /**
     * 删除指定文件
     *
     * @param path 文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        } else {
            file.delete();
        }
    }

    /**
     * 获取assets目录下的文件内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        String line = "";
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }
}
