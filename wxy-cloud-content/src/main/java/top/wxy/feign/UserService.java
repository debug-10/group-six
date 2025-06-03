package top.wxy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.wxy.framework.common.utils.Result;
import top.wxy.vo.UserVO;

/**
 * @author 笼中雀
 */
@FeignClient(name="wxy-cloud-user")
public interface UserService {
    @GetMapping(value = "api/user/getUserById")
    Result<UserVO> getUserById(@RequestParam("id") Long id);
}
