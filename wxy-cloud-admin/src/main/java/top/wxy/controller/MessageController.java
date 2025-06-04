package top.wxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.wxy.convert.MessageConvert;
import top.wxy.model.dto.MessageDTO;
import top.wxy.model.dto.MessageListDTO;
import top.wxy.model.entity.Message;
import top.wxy.model.query.MessageQuery;
import top.wxy.service.MessageService;

@RestController
@Tag(name = "消息管理", description = "消息管理")
@RequestMapping("/api/message")

public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 创建消息
     * @param dto 请求DTO
     * @return 包含完整信息的列表DTO
     */
    @PostMapping
    @Operation(summary = "创建消息")
    public MessageListDTO createMessage(@RequestBody MessageDTO dto) {
        Message message = MessageConvert.INSTANCE.toEntity(dto);

        // 使用自定义方法，返回保存后的实体
        Message savedMessage = messageService.saveAndReturn(message);

        return MessageConvert.INSTANCE.toListDTO(savedMessage);
    }
    /**
     * 分页查询消息列表
     */
    @GetMapping
    @Operation(summary = "消息列表")
    public IPage<MessageListDTO> listMessages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            MessageQuery query
    ) {
        // 执行分页查询并转换为DTO列表
        IPage<Message> messagePage = messageService.listMessages(page, limit, query);
        return messagePage.convert(MessageConvert.INSTANCE::toListDTO);
    }

    /**
     * 根据ID查询消息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询消息")
    public MessageListDTO getMessageById(@PathVariable Long id) {
        Message message = messageService.getById(id);
        return MessageConvert.INSTANCE.toListDTO(message);
    }

    /**
     * 更新消息
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新消息")
    public void updateMessage(
            @PathVariable Long id,
            @RequestBody MessageDTO dto
    ) {
        // 1. DTO转换为实体
        Message message = MessageConvert.INSTANCE.toEntity(dto);

        // 2. 设置ID（从路径变量获取，确保安全）
        message.setId(id);

        // 3. 更新消息（自动更新updateTime）
        messageService.updateById(message);
    }

    /**
     * 删除消息
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除消息")
    public void deleteMessage(@PathVariable Long id) {
        messageService.removeById(id);
    }
}