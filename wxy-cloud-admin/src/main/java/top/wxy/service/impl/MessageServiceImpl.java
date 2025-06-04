package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.wxy.mapper.MessageMapper;
import top.wxy.model.entity.Message;
import top.wxy.model.query.MessageQuery;
import top.wxy.service.MessageService;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public IPage<Message> listMessages(int page, int limit, MessageQuery query) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();

        // 根据查询条件动态构建SQL
        if (query.getUserId() != null) {
            wrapper.eq("user_id", query.getUserId());
        }
        if (query.getDeviceId() != null) {
            wrapper.eq("device_id", query.getDeviceId());
        }
        if (query.getType() != null) {
            wrapper.eq("type", query.getType());
        }
        if (query.getIsTop() != null) {
            wrapper.eq("is_top", query.getIsTop());
        }

        // 排序（默认按创建时间降序）
        wrapper.orderByDesc("create_time");

        // 执行分页查询
        return this.page(new Page<>(page, limit), wrapper);
    }

    @Override
    public Message saveAndReturn(Message entity) {
        super.save(entity);
        return entity; // 返回保存后的实体（ID已填充）
    }
}