package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.model.dto.DictRequestDTO;
import top.wxy.model.entity.Dict;

@Mapper
public interface DictConvert {
    DictConvert INSTANCE = Mappers.getMapper(DictConvert.class);


    Dict convertToDict(DictRequestDTO dto);
}
