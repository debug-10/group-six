package top.wxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.wxy.model.entity.Package;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PackageMapper extends BaseMapper<Package> {
}
