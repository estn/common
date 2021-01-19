/**
 * Copyright  2018  estn.zuo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.support;

import com.argyranthemum.common.core.exception.BaseException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: Base64FileSupport
 * @Author: estn.zuo
 * @CreateTime: 2018-07-16 12:38
 */
public class Base64FileSupport {

    private static final Logger logger = LoggerFactory.getLogger(Base64FileSupport.class);

    /**
     * 去掉base64图片前缀
     *
     * @param base64String base64字符串
     * @return
     */
    public static String replaceBase64Prefix(String base64String) {
        String imgType = "jpg,png,jpeg";
        if (!StringUtils.isEmpty(imgType)) {
            String[] imgTypes = imgType.split(",");
            Pattern pattern;
            Matcher matcher;
            String regex;
            for (String v : imgTypes) {
                regex = MessageFormat.format("data:image/{0};base64,", v);
                pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(base64String);
                if (matcher.lookingAt()) {
                    return matcher.replaceFirst("");
                }
            }
        }
        return base64String;
    }


    /**
     * 获取base64文件的后缀
     *
     * @param base64String
     * @return
     */
    public static String getBase64StringExtension(String base64String) {
        ImageInputStream imageInputstream = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64String);
            imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(bytes));
            ImageIO.setUseCache(false);
            Iterator<ImageReader> it = ImageIO.getImageReaders(imageInputstream);
            if (it.hasNext()) {
                ImageReader imageReader = it.next();
                imageReader.setInput(imageInputstream, true, true);
                return imageReader.getFormatName().trim().toLowerCase();
            }
        } catch (IOException e) {
            logger.error("parse suffix error.", e);
            throw new BaseException("parse suffix error.");
        } finally {
            if (imageInputstream != null) {
                try {
                    imageInputstream.close();
                } catch (IOException e) {
                    logger.error("e", e);
                }
            }
        }
        throw new BaseException("parse suffix error.");
    }

    /**
     * base64字符串转换成文件
     *
     * @param base64Code
     * @param targetPath
     */
    public static void decoderBase64File(String base64Code, String targetPath) {
        FileOutputStream out = null;
        try {
            byte[] buffer = Base64.decodeBase64(base64Code);
            out = new FileOutputStream(targetPath);
            out.write(buffer);
        } catch (Exception e) {
            throw new BaseException("save base64 file error. e:{}", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("close FileOutputStream error", e);
            }
        }

    }

    /**
     * 文件转换成Base64
     *
     * @param path 文件路径
     * @return
     */
    public static String encodeBase64File(String path) {
        FileInputStream inputFile = null;
        try {
            File file = new File(path);
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeBase64String(buffer);
        } catch (Exception e) {
            throw new BaseException("encodeBase64File error. e:{}", e);
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    logger.error(" inputFile close error.", e);
                }
            }
        }
    }

}
