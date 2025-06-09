package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.wxy.model.dto.MessageDTO;
import top.wxy.model.dto.MessageListDTO;
import top.wxy.model.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageConvert {
    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);
    Message toEntity(MessageDTO dto);
    MessageDTO toDTO(Message entity);

    @Mapping(target = "createTime", expression = "java(entity.getCreateTime() != null ? entity.getCreateTime().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)")
    @Mapping(target = "updateTime", expression = "java(entity.getUpdateTime() != null ? entity.getUpdateTime().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)")
    MessageListDTO toListDTO(Message entity);
}