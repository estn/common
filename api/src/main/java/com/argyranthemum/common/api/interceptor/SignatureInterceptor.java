package com.argyranthemum.common.api.interceptor;

import com.argyranthemum.common.core.exception.BaseException;
import com.argyranthemum.common.core.exception.DefaultError;
import com.argyranthemum.common.core.support.SignSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 签名验证
 * <p>
 * 1.验证APPID
 * <p>
 * 2.验证时间戳是否正确
 * <p>
 * 3.验证邮戳是否正确
 * <p>
 * 4.验证签名是否正确
 */
public class SignatureInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SignatureInterceptor.class);

    private List<String> appIds;

    private SignatureService signatureService;

    public SignatureInterceptor(List<String> appIds, SignatureService signatureService) {
        this.appIds = appIds;
        this.signatureService = signatureService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, String[]> parameter = request.getParameterMap();

        Map<String, Object> param = SignSupport.convert(parameter);

        if (this.verifySecret(param)) {
            return true;
        }

        this.verifyAppId(param);

        this.verifyTimestamp(param);

        this.verifyStamp(param);

        this.verifySign(param);

        return true;
    }

    private boolean verifySecret(Map<String, Object> param) {
        Object secretObj = param.get("secret");
        if (secretObj == null || StringUtils.isBlank(secretObj.toString())) {
            return false;
        }

        String _secret = signatureService.getSecret();
        if (StringUtils.isBlank(_secret)) {
            return false;
        }

        return secretObj.toString().equals(_secret);
    }

    private void verifyAppId(Map<String, Object> param) {
        String appId = this.getAppId(param);
        if (!this.appIds.contains(appId)) {
            throw new BaseException(DefaultError.APP_ID_ERROR, appId);
        }
    }


    private void verifyStamp(Map<String, Object> param) {
        Object nonceObj = param.get("nonce");
        if (nonceObj == null || StringUtils.isBlank(nonceObj.toString()) || nonceObj.toString().length() > 40) {
            throw new BaseException(DefaultError.DUPLICATE_NONCE);
        }

        String nonce = nonceObj.toString();
        boolean result = signatureService.verifyNonce(nonce);
        if (!result) {
            throw new BaseException(DefaultError.DUPLICATE_NONCE, nonce);
        }
    }

    private void verifyTimestamp(Map<String, Object> param) {
        Object timestampObj = param.get("timestamp");
        if (timestampObj == null || StringUtils.isBlank(timestampObj.toString())) {
            throw new BaseException(DefaultError.TIMESTAMP_ERROR);
        }

        long timestamp = Long.parseLong(timestampObj.toString());
        boolean result = signatureService.verifyTimestamp(timestamp);
        if (!result) {
            throw new BaseException(DefaultError.TIMESTAMP_ERROR, timestamp, System.currentTimeMillis());
        }
    }

    private void verifySign(Map<String, Object> param) {
        Object signObj = param.get("sign");
        if (signObj == null || StringUtils.isBlank(signObj.toString())) {
            logger.error("sign is null");
            throw new BaseException(DefaultError.SIGN_ERROR);
        }
        String sign = signObj.toString();

        String appId = getAppId(param);
        String version = param.get("version").toString();
        String platform = param.get("platform").toString();

        String key = signatureService.getKey(appId, platform, version);
        String newSign = SignSupport.sign(param, key);
        if (!sign.equals(newSign)) {
            throw new BaseException(DefaultError.SIGN_ERROR, sign, newSign);
        }
    }


    private String getAppId(Map<String, Object> param) {
        Object appIdObj = param.get("appId");

        if (appIdObj == null || StringUtils.isBlank(appIdObj.toString())) {
            throw new BaseException(DefaultError.APP_ID_ERROR);
        }
        return appIdObj.toString();
    }
}
