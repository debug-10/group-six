//package top.wxy.service;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import top.wxy.model.entity.VersionEntity;
//
///**
// * 版本管理服务接口
// */
//public interface VersionService {
//
//    /** 创建新版本 */
//    VersionEntity createVersion(VersionEntity entity);
//
//    /** 更新版本信息 */
//    void updateVersion(Long id, VersionEntity entity);
//
//    /** 删除版本（物理删除） */
//    void deleteVersion(Long id);
//
//    /** 查询最新版本（按平台） */
//    VersionEntity getLatestVersion(String platform);
//
//    /** 分页查询版本列表 */
//    Page<VersionEntity> getVersionPage(int pageNum, int pageSize, String platform, String version);
//}