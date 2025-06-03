package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.wxy.convert.NoteConvert;
import top.wxy.dao.NoteDao;
import top.wxy.dto.NoteDTO;
import top.wxy.entity.Note;
import top.wxy.framework.common.constant.Constant;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.common.utils.Result;
import top.wxy.framework.mybatis.service.impl.BaseServiceImpl;
import top.wxy.query.NoteQuery;
import top.wxy.service.NoteService;
import top.wxy.vo.NoteVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class NoteServiceImpl extends BaseServiceImpl<NoteDao, Note> implements NoteService {
    @Override
    public PageResult<NoteVO> page(NoteQuery query) {
        // 查询参数
        Map<String, Object> params = getParams(query);
        // 分⻚查询
        IPage<Note> page = getPage(query);
        params.put(Constant.PAGE, page);
        // 数据列表
        List<Note> list = baseMapper.getList(params);
        return new PageResult<>(NoteConvert.INSTANCE.convertList(list), page.getTotal());
    }

    @Override
    public PageResult<NoteVO> getNotesByCategoryId(Long categoryId,NoteQuery query){
        LambdaQueryWrapper<Note> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Note::getCategoryId,categoryId);
        List<Note> list=baseMapper.selectList(queryWrapper);
        Map<String,Object> params=getParams(query);
        IPage<Note> page=getPage(query);
        params.put(Constant.PAGE,page);
        return new PageResult<>(NoteConvert.INSTANCE.convertList(list),page.getTotal());
    }

    @Override
    public boolean publishNote(NoteDTO noteDTO){
        Note note=NoteConvert.INSTANCE.convert(noteDTO);
        return baseMapper.insertOrUpdate(note);
    }



    private Map<String, Object> getParams(NoteQuery query) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", query.getTitle());
        return params;
    }
}