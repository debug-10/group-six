package top.wxy.sms.service;

/**
 * @author 笼中雀
 */
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import top.wxy.framework.common.cache.RedisCache;
import top.wxy.framework.common.cache.RedisKeys;
import top.wxy.sms.config.AliyunSmsConfig;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.Client;

@Slf4j
@Service
@AllArgsConstructor
public class AliyunSmsService extends SmsService {
    private final AliyunSmsConfig aliyunSmsConfig;
    private final RedisCache redisCache;

    @Override
    public boolean sendSms(String mobile) {
        try {
            Config config = new Config()
                    .setAccessKeyId(aliyunSmsConfig.getAccessKey())
                    .setAccessKeySecret(aliyunSmsConfig.getAccessKeySecret());
            Client client = new Client(config);
            String code = RandomStringUtils.randomNumeric(4);
            SendSmsRequest request = new SendSmsRequest()
                    .setSignName(aliyunSmsConfig.getSignName())
                    .setTemplateCode(aliyunSmsConfig.getTemplateCode())
                    .setPhoneNumbers(mobile)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(request, new RuntimeOptions());
            SendSmsResponseBody body = sendSmsResponse.getBody();
            if ("OK".equals(body.getCode())) {
                log.info(" ============ 短信发送成功 ============");
                redisCache.set(RedisKeys.getCaptchaKey(mobile),code,60);
                return true;
            }else {
                log.error("短信发送失败，错误码: {},错误信息: {}", body.getCode(),body.getMessage());
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}