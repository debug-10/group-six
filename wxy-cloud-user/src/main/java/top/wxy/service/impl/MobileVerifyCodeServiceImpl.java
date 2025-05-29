package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.wxy.framework.security.mobile.MobileVerifyCodeService;
import top.wxy.sms.service.AliyunSmsService;

/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
    private final AliyunSmsService smsService;
    @Override
    public boolean verifyCode(String mobile, String code) {
        return smsService.verifyCode(mobile, code);
    }
}

