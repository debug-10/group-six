package top.wxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import top.wxy.model.entity.Message;
import top.wxy.model.query.MessageQuery;

public interface MessageService extends IService<Message> {

    // 分页查询消息列表
    IPage<Message> listMessages(int page, int limit, MessageQuery query);

    Message saveAndReturn(Message entity);

}