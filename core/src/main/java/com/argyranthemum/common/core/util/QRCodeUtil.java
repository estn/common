package com.argyranthemum.common.core.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 二维码生成工具
 * @Author: estn.zuo
 * @CreateTime: 2014-11-13 10:19
 */
public class QRCodeUtil {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;


    private QRCodeUtil() {
    }

    /**
     * 生成二维码图片，同时将该图片保存在系统文件的路径下
     *
     * @param qrCodeContent
     * @return
     */
    public static String encodeQRCode(String qrCodeContent) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            encodeQRCode(qrCodeContent, out);
            return FileUtil.saveFile(out.toByteArray(), "png");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param qrCodeContent 二维码存储内容
     * @param imgPath       图片路径
     */
    public static void encodeQRCode(String qrCodeContent, String imgPath) {
        encodeQRCode(qrCodeContent, imgPath, FileUtil.fetchExtension(imgPath), 7);
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param qrCodeContent 存储内容
     * @param output        输出流
     */
    public static void encodeQRCode(String qrCodeContent, OutputStream output) {
        encodeQRCode(qrCodeContent, output, "png", 7);
    }


    /**
     * 生成二维码(QRCode)图片
     *
     * @param qrCodeContent 存储内容
     * @param output        输出流
     * @param imgType       图片类型
     */
    public static void encodeQRCode(String qrCodeContent, OutputStream output, String imgType) {
        encodeQRCode(qrCodeContent, output, imgType, 7);
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param qrCodeContent 存储内容
     * @param imgPath       图片路径
     * @param imgType       图片类型
     * @param size          二维码尺寸
     */
    public static void encodeQRCode(String qrCodeContent, String imgPath, String imgType, int size) {
        try {
            BufferedImage bufImg = ecodeInternal(qrCodeContent, size);
            File imgFile = new File(imgPath);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        }
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param qrCodeContent 存储内容
     * @param output        输出流
     * @param imgType       图片类型
     * @param size          二维码尺寸
     */
    public static void encodeQRCode(String qrCodeContent, OutputStream output, String imgType, int size) {
        try {
            BufferedImage bufImg = ecodeInternal(qrCodeContent, size);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, output);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        }
    }

    /**
     * 生成二维码(QRCode)图片的公共方法
     *
     * @param qrCodeContent 存储内容
     * @param size          二维码尺寸
     * @return
     */
    private static BufferedImage ecodeInternal(String qrCodeContent, int size) {
        BufferedImage bufImg = null;
        int width = 67 + 16 * (size - 1);
        int height = 67 + 16 * (size - 1);
        // 二维码的图片格式
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            // 生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height, hints);
            bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufImg.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        }
        return bufImg;
    }

}