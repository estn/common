package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.exception.BaseException;
import com.google.common.base.Preconditions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description: 条形码生成工具
 * @Author: estn.zuo
 * @CreateTime: 2014-11-13 10:19
 */
public class BarCodeUtil {

    private static Logger logger = LoggerFactory.getLogger(BarCodeUtil.class);

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private static final int DEFAULT_HEIGHT = 50;

    private static final String DEFAULT_IMAGE_FORMAT = "png";

    private static final int DEFAULT_WIDTH = 95;

    private static final int FACTOR = 14;

    private BarCodeUtil() {
    }

    /**
     * 将编码内容输出到文件中，该文件保存在系统的文件夹下，
     * <p/>
     * 如/resource/201507/01/UUID.png
     *
     * @param content 编码内容
     * @return
     */
    public static String encode(String content) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            encode(content, out);
            return FileUtil.saveFile(out.toByteArray(), DEFAULT_IMAGE_FORMAT);
        } catch (Exception e) {
            throw new BaseException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.toString(), e);
                }
            }
        }
    }

    /**
     * 将编码内容生成条形码后返回输出流,用户可以根据输出流自定义需要的格式
     *
     * @param content 编码内容
     * @param out     输出流
     */
    public static void encode(String content, OutputStream out) {
        try {
            BufferedImage image = encodeInternal(content);
            ImageIO.write(image, DEFAULT_IMAGE_FORMAT, out);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 对编码内容进行条形码编码
     *
     * @param content 编码内容
     * @return
     */
    private static BufferedImage encodeInternal(String content) {
        Preconditions.checkNotNull(content);

        int width = Math.max(DEFAULT_WIDTH, content.length() * FACTOR);
        BufferedImage bufferedImage = new BufferedImage(width, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            BitMatrix bitMatrix = new Code128Writer().encode(content,
                    BarcodeFormat.CODE_128, content.length() * FACTOR, DEFAULT_HEIGHT, null);
            for (int x = 0; x < content.length() * FACTOR; x++) {
                for (int y = 0; y < DEFAULT_HEIGHT; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return bufferedImage;
    }
}