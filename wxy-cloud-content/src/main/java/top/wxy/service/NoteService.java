package top.wxy.service;

import top.wxy.dto.NoteDTO;
import top.wxy.entity.Note;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.mybatis.service.BaseService;
import top.wxy.query.NoteQuery;
import top.wxy.vo.NoteVO;

/**
 * @author 笼中雀
 */
public interface NoteService extends BaseService<Note> {
    PageResult<NoteVO> page(NoteQuery query);

    PageResult<NoteVO> getNotesByCategoryId(Long categoryId,NoteQuery query);

    boolean publishNote(NoteDTO noteDTO);
}
