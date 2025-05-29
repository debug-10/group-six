package top.wxy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.convert.ShareConvert;
import top.wxy.entity.Share;
import top.wxy.framework.common.utils.Result;
import top.wxy.service.ShareService;
import top.wxy.vo.ShareVO;

import java.util.List;

/**
 * @author 笼中雀
 */
@RestController
@RequestMapping("api/content")
@Tag(name = "内容模块")
@AllArgsConstructor
public class ShareController {
    private final ShareService shareService;

    @GetMapping("shares")
    public Result<List<ShareVO>> getShareList() {
        List<Share> shares = shareService.list();
        List<ShareVO> list = ShareConvert.INSTANCE.convertList(shares);
        return Result.ok(list);
    }
}