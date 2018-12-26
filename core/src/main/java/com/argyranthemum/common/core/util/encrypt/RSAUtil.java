/**
 * Copyright  2015  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

/**
 * @Description: RSA算法 <strong>私钥(秘钥格式为PKCS#8)<strong/>
 * @Author: estn.zuo
 * @CreateTime: 2015-12-30 16:27
 */
public class RSAUtil {

    private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 默认秘钥长度
     */
    private static final int DEFAULT_KEYSIZE = 2048;

    /**
     * 默认签名算法
     */
    private static final String DEFAULT_SIGN_ALGORITHM = "SHA256WithRSA";

    /**
     * 统一字符编码集
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 秘钥算法
     */
    private static final String KEY_ALG = "RSA";

    private RSAUtil() {
    }

    /**
     * RSA算法 生成秘钥对，秘钥格式为PKCS#8
     * 默认秘钥长度为:1024
     *
     * @param publicKeyFileName  公钥文件路径，包含文件名
     * @param privateKeyFileName 私钥文件路径，包含文件名
     */
    public static void generateKey(String publicKeyFileName, String privateKeyFileName) {
        generateKey(DEFAULT_KEYSIZE, publicKeyFileName, privateKeyFileName);
    }

    /**
     * RSA算法 生成秘钥对，秘钥格式为PKCS#8
     *
     * @param keysize            秘钥长度
     * @param publicKeyFileName  公钥文件路径，包含文件名
     * @param privateKeyFileName 私钥文件路径，包含文件名
     */
    public static void generateKey(int keysize, String publicKeyFileName, String privateKeyFileName) {
        FileOutputStream fos = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALG);
            SecureRandom secureRandom = new SecureRandom(UUID.randomUUID().toString().getBytes());
            keyPairGenerator.initialize(keysize, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            //保存公钥
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            String s = Base64.encodeBase64String(publicKeyBytes);
            fos = new FileOutputStream(publicKeyFileName);
            fos.write(s.getBytes());
            fos.flush();

            //保存私钥
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            String s1 = Base64.encodeBase64String(privateKeyBytes);
            fos = new FileOutputStream(privateKeyFileName);
            fos.write(s1.getBytes());
            fos.flush();

        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                logger.error(e.toString(), e);
            }
        }

    }

    //========================================公钥加密私钥解密======================START====================

    /**
     * 加密 (公钥加密私钥解密)
     *
     * @param plainText 明文
     * @param publicKey 公钥
     * @return 加密后数据
     */
    private static byte[] encryptByPublicKey(byte[] plainText, byte[] publicKey) {
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALG);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
            PublicKey pubKey = kf.generatePublic(spec);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            cipherText = cipher.doFinal(plainText);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return cipherText;
    }

    /**
     * 加密 (公钥加密私钥解密)
     *
     * @param plainText 明文
     * @param publicKey 公钥,Base64后的公钥
     * @return 加密后进行Base64的数据
     */
    public static String encryptByPublicKey(String plainText, String publicKey) {
        String cipherText = null;
        try {
            byte[] cipherTextByte = encryptByPublicKey(plainText.getBytes(CHARSET_NAME), Base64.decodeBase64(publicKey));
            cipherText = Base64.encodeBase64String(cipherTextByte);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return cipherText;
    }

    /**
     * 解密 (公钥加密私钥解密)
     *
     * @param cipherText 密文
     * @param privateKey 私钥(秘钥格式为PKCS#8)
     * @return
     */
    private static byte[] decryptByPrivateKey(byte[] cipherText, byte[] privateKey) {
        byte[] plainText = null;
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALG);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
            PrivateKey privKey = kf.generatePrivate(spec);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            plainText = cipher.doFinal(cipherText);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return plainText;
    }

    /**
     * 解密 (公钥加密私钥解密)
     *
     * @param cipherText 密文，Base64后的密文
     * @param privateKey 私钥(秘钥格式为PKCS#8)
     * @return 原文
     */
    public static String decryptByPrivateKey(String cipherText, String privateKey) {
        String plainText = null;
        try {
            byte[] plainBytes = decryptByPrivateKey(Base64.decodeBase64(cipherText), Base64.decodeBase64(privateKey));
            plainText = new String(plainBytes, Charset.forName(CHARSET_NAME));
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return plainText;
    }

    /**
     * 签名 (私钥签名公钥验签)
     *
     * @param input         原文
     * @param privateKey    私钥
     * @param signAlgorithm 签名算法
     * @return
     */
    private static byte[] sign(byte[] input, byte[] privateKey, String signAlgorithm) {
        byte[] signatureBytes = null;
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
            PrivateKey privKey = kf.generatePrivate(spec);

            Signature sig = Signature.getInstance(signAlgorithm);
            sig.initSign(privKey);
            sig.update(input);
            signatureBytes = sig.sign();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return signatureBytes;
    }

    //========================================公钥加密私钥解密======================END====================


    //========================================私钥加密公钥解密======================START====================

    /**
     * 加密 (私钥加密公钥解密)
     *
     * @param plainText  明文
     * @param privateKey 私钥
     * @return 加密后数据
     */
    private static byte[] encryptByPrivateKey(byte[] plainText, byte[] privateKey) {
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALG);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
            RSAPrivateKey priKey = (RSAPrivateKey) kf.generatePrivate(spec);
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            cipherText = cipher.doFinal(plainText);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return cipherText;
    }

    /**
     * 加密 (私钥加密公钥解密)
     *
     * @param plainText  明文
     * @param privateKey 私钥(秘钥格式为PKCS#8)
     * @return 加密后进行Base64的数据
     */
    public static String encryptByPrivateKey(String plainText, String privateKey) {
        String cipherText = null;
        try {
            byte[] cipherTextByte = encryptByPrivateKey(plainText.getBytes(CHARSET_NAME), Base64.decodeBase64(privateKey));
            cipherText = Base64.encodeBase64String(cipherTextByte);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return cipherText;
    }

    /**
     * 解密 (私钥加密公钥解密)
     *
     * @param cipherText 密文
     * @param publicKey  公钥
     * @return
     */
    private static byte[] decryptByPublicKey(byte[] cipherText, byte[] publicKey) {
        byte[] plainText = null;
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALG);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
            RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(spec);
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            plainText = cipher.doFinal(cipherText);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return plainText;
    }

    /**
     * 解密 (私钥加密公钥解密)
     *
     * @param cipherText 密文，Base64后的密文
     * @param publicKey  公钥，Base64后的公钥
     * @return 原文
     */
    public static String decryptByPublicKey(String cipherText, String publicKey) {
        String plainText = null;
        try {
            byte[] plainBytes = decryptByPublicKey(Base64.decodeBase64(cipherText), Base64.decodeBase64(publicKey));
            plainText = new String(plainBytes, Charset.forName(CHARSET_NAME));
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return plainText;
    }


    //========================================私钥加密公钥解密======================END====================


    /**
     * 签名 (私钥签名公钥验签)
     * 默认签名算法为：SHA1WithRSA
     *
     * @param input      原文
     * @param privateKey 秘钥
     * @return
     */
    public static String sign(String input, String privateKey) {
        String signString = null;
        try {
            byte[] signatureBytes = sign(input.getBytes(CHARSET_NAME), Base64.decodeBase64(privateKey), DEFAULT_SIGN_ALGORITHM);
            signString = Base64.encodeBase64String(signatureBytes);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return signString;
    }


    /**
     * 验签 (私钥签名公钥验签)
     *
     * @param input         原文
     * @param sign          签名后的字符串
     * @param publicKey     公钥
     * @param signAlgorithm 签名算法
     * @return
     */
    private static boolean verify(byte[] input, byte[] sign, byte[] publicKey, String signAlgorithm) {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALG);
            PublicKey pubKey = kf.generatePublic(spec);

            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(pubKey);
            signature.update(input);
            return signature.verify(sign);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return false;
    }

    /**
     * 验签 (私钥签名公钥验签)
     * 所有参数都是字符串形式的
     *
     * @param input         原文
     * @param sign          签名后的字符串(被Base64编码后的字符串)
     * @param publicKey     公钥 (Base64编码后字符串)
     * @param signAlgorithm 签名算法
     * @return
     */
    public static boolean verify(String input, String sign, String publicKey, String signAlgorithm) {
        try {
            return verify(input.getBytes(CHARSET_NAME), Base64.decodeBase64(sign), Base64.decodeBase64(publicKey), signAlgorithm);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return false;
    }

    /**
     * 验签 (私钥签名公钥验签)
     * 默认签名算法为：SHA1WithRSA
     *
     * @param input     原文
     * @param sign      签名后的字符串(被Base64编码后的字符串)
     * @param publicKey 公钥 (Base64编码后字符串)
     * @return
     */
    public static boolean verify(String input, String sign, String publicKey) {
        try {
            return verify(input.getBytes(CHARSET_NAME), Base64.decodeBase64(sign), Base64.decodeBase64(publicKey), DEFAULT_SIGN_ALGORITHM);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return false;
    }

    public static String publickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApvNfVsT6QeGb9rAL8La4VYSl4bibDIDZ9Ru5So/W8PnYLUS0n4gFCFyoM13/+adLzmGVLHY4QStJWRxXWWj33EbVfT8lcO8sWp8hyww0+CvrF+TZm5fV+Ufs7hux/etEy3xiZuHyT+1CzaOIT0YapBEp7It528hEAl7s2YH7fRxzkuxiLtMZCBGNF8QKXKxNR4QT0GENLOdrCVTcNrJMLyC+falWBEBIQxeNW8NtzycEqrJMW9Wy4H2J07jJ7nUKZfYodXWlRtdpbSSDcmv1GNIebYYYGVLVu7Zs7XC6Qm5bZI/fdFR7JcAlFvdAf/aBOKv/sMJuo2C+CBjGz6WfzQIDAQAB";
    public static String privatekey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCm819WxPpB4Zv2sAvwtrhVhKXhuJsMgNn1G7lKj9bw+dgtRLSfiAUIXKgzXf/5p0vOYZUsdjhBK0lZHFdZaPfcRtV9PyVw7yxanyHLDDT4K+sX5Nmbl9X5R+zuG7H960TLfGJm4fJP7ULNo4hPRhqkESnsi3nbyEQCXuzZgft9HHOS7GIu0xkIEY0XxApcrE1HhBPQYQ0s52sJVNw2skwvIL59qVYEQEhDF41bw23PJwSqskxb1bLgfYnTuMnudQpl9ih1daVG12ltJINya/UY0h5thhgZUtW7tmztcLpCbltkj990VHslwCUW90B/9oE4q/+wwm6jYL4IGMbPpZ/NAgMBAAECggEBAI0tqSW7FNW5byZA4RgBrAfFUHF8psEABVBczWfcEytcXA6L1yJA5+CWWjZ1+wynCFfR9U/P+OpDLJeKe+ND/LXtwINVbtf9qUhI4U7PV8u1d+yQvePxliRjSlxgVzgeR+K7oIrX7wzf2moDLDW9VrH/QbCikb3lsH1AwJF+dajplHUfiaLZ3HZo5tWIB5HneBhqICDmkRAP7Ooa4t28SMZbcPDtnKJc/aEC/MVqCIXeKCkj5/bYIyJaj1BVl7hjGL69nYWsbD8Ks4jdqmbwJlRKLAsEzfAjkBijshceTCNSbC/IeJKokFZNmj3p1yYowDbuKsuCy31Y/V/uqwsDYJECgYEA1ylFssdhxBu54B5g49NL1UMe6SOxcKcvcL2SDTupeKQhz0zQsa6nTRO/jZkpnw/DTNcPGTUXFkgxXfXWH4P7+vFGlMyFjhmPDxsGiaW92APM3fiETZrieTc8fVav75oUiBwJ5itih7DeSvAGM2LENVtxpb2tkdixN7cdWtzOouMCgYEAxqOKMwFb8D2GROZl6sEbsBh87Pvr94v6Uwzr3DBYRCYJOrR3GBykyb/eX+m9pOp781z1eSE86XK1E5fMMoJLmf0N5q5EzJGi89lO8YTdzA7dhkbF/qZ9hBgu3o748mmu6iEXF1Ur9IiR4X1ghMDPv0FJ8CRaP6AAWW5lvlk/QY8CgYBgXFyZt8/iS2SQqipMfmodBeWHFnQ2drm5bT1G7ex1WlTGCaoBma3VyD1mxiyszxtxg3OAn5VKZmStpxcRoEOu3HX22C4MOQgoBLuF1BPU9ca/trhSJUa+cO2HWNaMoV4Go0APtGh5Ss4Tk7HDO72SA/U+/FcK1Zhw0nK0KeXlbwKBgCPLoMHkQs8eJy4PXtpZBkZLg2epVjJ71RZWQ8KgK6fMOumLT9LWhpOcsdQS6Qvo/YEzhUDYzoM7Gop3VmLbvAf55CFn0hoTPIw44tL/IID/RMhHxLJJCmFdgkS0+wOkK9MhN2oK/SrJ2ZAK6fPCUy2h9FMEGgdRK/7xt///UTjdAoGAEIJHl4hqSSj+EX4XN30PKu0ZVhBRNyTOsSc62JLNwCFLBf0kds+aQ09B3XmxaSNuz2CsEsAFZde7d9cRAwd/aIcNnYLyWtCXDENrBk1e2Wn8V1mwYeg2i+e8oXDm8ekxPFlrhHmhthqGHK6WDV6xEDQFEB4UmlFtkt6wTx2flw8=";

    public static void main(String[] args) throws Exception {
        String input = "123456";
        String s1 = encryptByPublicKey(input, publickey);
        System.out.println(1);
        System.out.println("解密字符串：" + decryptByPrivateKey(s1, privatekey));

        String s2 = sign(input, privatekey);
        System.out.println("验签结果：" + verify(input, s2, publickey));


        input = "123456";
        s1 = encryptByPrivateKey(input, privatekey);
        System.out.println(s1);
        System.out.println("解密字符串：" + decryptByPublicKey(s1, publickey));


    }


}
