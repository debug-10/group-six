//package top.wxy.mapper;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import org.springframework.stereotype.Repository;
//import top.wxy.model.entity.VersionEntity;
//
///**
// * 版本管理数据访问层
// */
//@Repository
//public interface VersionMapper extends BaseMapper<VersionEntity> {
//
//    /** 查询最新版本（使用Lambda表达式简化查询） */
//    default VersionEntity selectLatestByPlatform(String platform) {
//        return selectOne(Wrappers.<VersionEntity>lambdaQuery()
//                .eq(VersionEntity::getPlatform, platform)
//                .orderByDesc(VersionEntity::getReleaseDate)
//                .last("LIMIT 1")); // 使用last直接拼接SQL片段
//    }
//}