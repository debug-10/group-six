package top.wxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.wxy.model.entity.Package;
import top.wxy.mapper.PackageMapper;
import top.wxy.service.PackageService;

@Service
public class PackageServiceImpl extends ServiceImpl<PackageMapper, Package> implements PackageService {
}
