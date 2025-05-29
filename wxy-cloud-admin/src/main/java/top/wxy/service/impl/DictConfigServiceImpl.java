package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wxy.convert.DictConfigConvert;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.mapper.DictConfigMapper;
import top.wxy.model.dto.DictConfigRequestDTO;
import top.wxy.model.entity.DictConfig;
import top.wxy.model.query.DictConfigQuery;
import top.wxy.service.DictConfigService;
import top.wxy.service.DictService;


import java.util.List;

@Service
@AllArgsConstructor
public class DictConfigServiceImpl extends ServiceImpl<DictConfigMapper, DictConfig> implements DictConfigService {

    private final DictService dictService;

    @Override
    public List<DictConfig> list(DictConfigQuery query) {
        LambdaQueryWrapper<DictConfig> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getTitle())) {
            wrapper.like(DictConfig::getTitle, query.getTitle());
        }
        wrapper.eq(DictConfig::getDictNumber, query.getNumber());
        wrapper.orderByDesc(DictConfig::getCreateTime);
        List<DictConfig> result = baseMapper.selectList(wrapper);
        return result;
    }

    @Override
    public void add(DictConfigRequestDTO dto) {
        DictConfig dictConfig = DictConfigConvert.INSTANCE.convertToConfig(dto);
        LambdaQueryWrapper<DictConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictConfig::getDictNumber, dictConfig.getDictNumber());
        wrapper.eq(DictConfig::getTitle, dictConfig.getTitle());
        List<DictConfig> dictConfigs = baseMapper.selectList(wrapper);
        if (dictConfigs.size() > 0) {
            throw new ServerException("该字典标题已存在");
        }
        wrapper.clear();
        dictConfigs.clear();
        wrapper.eq(DictConfig::getDictNumber, dictConfig.getDictNumber());
        wrapper.eq(DictConfig::getValue, dictConfig.getValue());
        dictConfigs = baseMapper.selectList(wrapper);
        if (dictConfigs.size() > 0) {
            throw new ServerException("该字典值已存在");
        }
        baseMapper.insert(dictConfig);
        dictService.refreshTransCache();
    }


    @Override
    public void edit(DictConfigRequestDTO dto) {
        DictConfig dictConfig = DictConfigConvert.INSTANCE.convertToConfig(dto);
        LambdaQueryWrapper<DictConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictConfig::getDictNumber, dictConfig.getDictNumber());
        wrapper.eq(DictConfig::getTitle, dictConfig.getTitle());
        List<DictConfig> dictConfigs = baseMapper.selectList(wrapper);
        if (dictConfigs.size() > 0) {
            throw new ServerException("该字典标题已存在");
        }
        wrapper.clear();
        dictConfigs.clear();
        wrapper.eq(DictConfig::getDictNumber, dictConfig.getDictNumber());
        wrapper.eq(DictConfig::getValue, dictConfig.getValue());
        dictConfigs = baseMapper.selectList(wrapper);
        if (dictConfigs.size() > 0) {
            throw new ServerException("该字典值已存在");
        }
        updateById(dictConfig);
        dictService.refreshTransCache();
    }

    @Override
    public void remove(List<Integer> ids) {
        removeBatchByIds(ids);
    }
}
