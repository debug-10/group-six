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

    // DTO 转实体
    Message toEntity(MessageDTO dto);

    // 实体 转 DTO
    MessageDTO toDTO(Message entity);

    // 实体 转 列表DTO（显式指定日期字段的映射）
//    @Mapping(target = "createTime", expression = "java(entity.getCreateTime() != null ? entity.getCreateTime().toString() : null)")
//    @Mapping(target = "updateTime", expression = "java(entity.getUpdateTime() != null ? entity.getUpdateTime().toString() : null)")
//    MessageListDTO toListDTO(Message entity);

    @Mapping(target = "createTime", expression = "java(entity.getCreateTime() != null ? entity.getCreateTime().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)")
    @Mapping(target = "updateTime", expression = "java(entity.getUpdateTime() != null ? entity.getUpdateTime().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)")
    MessageListDTO toListDTO(Message entity);
}