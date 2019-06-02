package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.constant.ConfigurationConst;
import com.argyranthemum.common.core.constant.SystemConst;
import com.argyranthemum.common.core.support.Base64FileSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;


/**
 * Description: FileUtil
 * Author: Estn
 * CreateTime: 2014-07-23 22:13
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
    }

    /**
     * 获取扩展名
     *
     * @param filename 文件名
     * @return
     */
    public static String fetchExtension(String filename) {

        if (StringUtils.isBlank(filename)) {
            return null;
        }

        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 获取扩展名
     *
     * @param contentType 文件格式
     * @return
     */
    public static String fetchExtensionFromContentType(String contentType) {

        if (StringUtils.isBlank(contentType)) {
            return null;
        }

        return contentType.substring(contentType.indexOf("/") + 1);
    }

    /**
     * 保存文件
     *
     * @param url       文件URL地址
     * @param extension 扩展名(不带"."的)
     * @return
     */
    public static String saveFile(String url, String extension) {
        try {
            InputStream is = new URL(url).openStream();
            return FileUtil.saveFile(is, extension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存文件
     *
     * @param inputStream 文件数据
     * @param extension   扩展名(不带"."的)
     * @return
     */
    public static String saveFile(InputStream inputStream, String extension) {
        try {
            return saveFile(IOUtils.toByteArray(inputStream), extension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 保存文件
     *
     * @param base64 文件base64格式字符串
     * @return
     */
    public static String saveFile(String base64) {
        String filename = UUIDUtil.random();
        String path = doFetchPath(filename);
        String absolutelyPath = getAbsolutelyPath(path);
        base64 = Base64FileSupport.replaceBase64Prefix(base64);
        String extension = Base64FileSupport.getBase64StringExtension(base64);
        Base64FileSupport.decoderBase64File(base64, absolutelyPath + filename + "." + extension);
        return "/" + path + filename + "." + extension;
    }

    /**
     * 保存文件
     *
     * @param bytes     文件数据
     * @param extension 扩展名(不带"."的)
     * @return
     */
    public static String saveFile(byte[] bytes, String extension) {
        String filename = UUIDUtil.random();
        String path = doFetchPath(filename);
        String absolutelyPath = getAbsolutelyPath(path);

        File mediaFile = new File(absolutelyPath + filename + "." + extension);

        try {
            FileUtils.writeByteArrayToFile(mediaFile, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/" + path + mediaFile.getName();
    }

    /**
     * 保存文件
     *
     * @param file      文件流
     * @param extension 扩展名(不带"."的)
     * @return
     * @throws Exception
     */
    public static String saveFile(File file, String extension) {

        try {
            if (file == null || file.length() == 0) {
                return null;
            }
            String filename = UUIDUtil.random();
            String path = doFetchPath(filename);
            String absolutelyPath = getAbsolutelyPath(path);

            File mediaFile = new File(absolutelyPath + filename + "." + extension);
            FileUtils.copyFile(file, mediaFile);

            return "/" + path + mediaFile.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建文件保存路径 </br>
     *
     * @param filename 文件名
     * @return RESOURCE_DIR/201704/23/ab/
     */
    private static String doFetchPath(String filename) {
        return SystemConst.RESOURCE_DIR + "/" + getYearAndMonth() + "/" + getDay() + "/" + filename.substring(0, 2) + "/";
    }

    private static String getAbsolutelyPath(String path) {
        String absolutelyPath = ConfigurationConst.RESOURCE_ROOT_PATH + "/" + path;
        File absolutelyPathFile = new File(absolutelyPath);
        if (!absolutelyPathFile.exists()) {
            absolutelyPathFile.mkdirs();
        }
        return absolutelyPath;
    }

    private static String getYearAndMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthString = month + "";
        if (month < 10) {
            monthString = "0" + month;
        }
        return calendar.get(Calendar.YEAR) + monthString;
    }

    private static String getDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return day + "";
    }


}
