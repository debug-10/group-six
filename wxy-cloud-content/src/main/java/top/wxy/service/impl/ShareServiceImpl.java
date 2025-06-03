package top.wxy.service.impl;

import org.springframework.stereotype.Service;
import top.wxy.dao.ShareDao;
import top.wxy.entity.Share;
import top.wxy.framework.mybatis.service.impl.BaseServiceImpl;
import top.wxy.service.ShareService;

/**
 * @author 笼中雀
 */
@Service
public class ShareServiceImpl extends BaseServiceImpl<ShareDao, Share> implements ShareService {

}