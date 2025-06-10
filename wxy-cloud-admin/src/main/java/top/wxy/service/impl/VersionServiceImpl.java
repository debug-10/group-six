//package top.wxy.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//import top.wxy.model.entity.VersionEntity;
//import top.wxy.mapper.VersionMapper;
//import top.wxy.service.VersionService;
//
//import java.util.Date;
//
///**
// * 版本管理服务实现类
// */
//@Service
//public class VersionServiceImpl extends ServiceImpl<VersionMapper, VersionEntity> implements VersionService {
//
//    /** 创建新版本（自动填充发布时间） */
//    @Override
//    public VersionEntity createVersion(VersionEntity entity) {
//        entity.setReleaseDate(new Date()); // 自动设置发布时间
//        save(entity); // 调用MyBatis-Plus的save方法
//        return entity;
//    }
//
//    /** 更新版本信息（更新发布时间） */
//    @Override
//    public void updateVersion(Long id, VersionEntity updateEntity) {
//        VersionEntity oldEntity = getById(id);
//        if (oldEntity == null) {
//            throw new RuntimeException("版本不存在");
//        }
//        updateEntity.setId(id);
//        updateEntity.setReleaseDate(new Date()); // 强制更新发布时间为当前时间
//        updateById(updateEntity); // 调用MyBatis-Plus的updateById方法
//    }
//
//    /** 物理删除版本 */
//    @Override
//    public void deleteVersion(Long id) {
//        removeById(id); // 调用MyBatis-Plus的物理删除方法
//    }
//
//    /** 查询最新版本（按平台） */
//    @Override
//    public VersionEntity getLatestVersion(String platform) {
//        return baseMapper.selectLatestByPlatform(platform);
//    }
//
//    /** 分页查询版本列表（支持平台和版本号过滤） */
//    @Override
//    public Page<VersionEntity> getVersionPage(int pageNum, int pageSize, String platform, String version) {
//        LambdaQueryWrapper<VersionEntity> queryWrapper = new LambdaQueryWrapper<>()
//                .eq(platform != null, VersionEntity::platform, platform) // 直接引用字段名
//                .like(version != null, VersionEntity::version, version)
//                .orderByDesc(VersionEntity::releaseDate);
//
//        return page(new Page<>(pageNum, pageSize), queryWrapper);
//    }
//    }
//}