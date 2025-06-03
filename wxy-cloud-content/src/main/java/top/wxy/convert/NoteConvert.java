package top.wxy.convert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.dto.NoteDTO;
import top.wxy.entity.Note;
import top.wxy.vo.NoteVO;
import java.util.List;


/**
 * @author 笼中雀
 */
@Mapper
public interface NoteConvert {
    NoteConvert INSTANCE = Mappers.getMapper(NoteConvert.class);
    NoteVO convert(Note entity);
    List<NoteVO> convertList(List<Note> list);
    Note convert(NoteDTO noteDTO);
}
