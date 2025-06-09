package top.wxy.convert;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.wxy.model.dto.MessageDTO;
import top.wxy.model.dto.MessageListDTO;
import top.wxy.model.entity.Message;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:38:02+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class MessageConvertImpl implements MessageConvert {

    @Override
    public Message toEntity(MessageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Message message = new Message();

        message.setDeviceId( dto.getDeviceId() );
        message.setType( dto.getType() );
        message.setTitle( dto.getTitle() );
        message.setContent( dto.getContent() );
        message.setIsTop( dto.getIsTop() );
        message.setCreateTime( dto.getCreateTime() );
        message.setUpdateTime( dto.getUpdateTime() );

        return message;
    }

    @Override
    public MessageDTO toDTO(Message entity) {
        if ( entity == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setDeviceId( entity.getDeviceId() );
        messageDTO.setType( entity.getType() );
        messageDTO.setTitle( entity.getTitle() );
        messageDTO.setContent( entity.getContent() );
        messageDTO.setIsTop( entity.getIsTop() );
        messageDTO.setCreateTime( entity.getCreateTime() );
        messageDTO.setUpdateTime( entity.getUpdateTime() );

        return messageDTO;
    }

    @Override
    public MessageListDTO toListDTO(Message entity) {
        if ( entity == null ) {
            return null;
        }

        MessageListDTO messageListDTO = new MessageListDTO();

        messageListDTO.setId( entity.getId() );
        messageListDTO.setUserId( entity.getUserId() );
        messageListDTO.setDeviceId( entity.getDeviceId() );
        messageListDTO.setType( entity.getType() );
        messageListDTO.setTitle( entity.getTitle() );
        messageListDTO.setContent( entity.getContent() );
        messageListDTO.setIsTop( entity.getIsTop() );

        messageListDTO.setCreateTime( entity.getCreateTime() != null ? entity.getCreateTime().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null );
        messageListDTO.setUpdateTime( entity.getUpdateTime() != null ? entity.getUpdateTime().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null );

        return messageListDTO;
    }
}
