package top.wxy.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wxy.common.model.BaseServiceImpl;
import top.wxy.convert.UserConvert;
import top.wxy.enums.AccountStatusEnum;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.mapper.UserMapper;
import top.wxy.model.dto.UserEditDTO;
import top.wxy.model.entity.User;
import top.wxy.model.query.UserQuery;
import top.wxy.model.vo.UserInfoVO;
import top.wxy.service.UserService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author mqxu
 * @date 2024/11/18
 * @description UserServiceImpl
 **/
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult<UserInfoVO> page(UserQuery query) {
        Page<UserInfoVO> page = new Page<>(query.getPage(), query.getLimit());
        List<UserInfoVO> list = baseMapper.getUserPage(page, query);
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public void update(UserEditDTO dto) {
        User user = UserConvert.INSTANCE.convert(dto);
        User byPhone = baseMapper.getByPhone(user.getPhone());
        if (byPhone != null && !byPhone.getPkId().equals(user.getPkId())) {
            throw new ServerException("手机号已存在");
        }
        baseMapper.updateById(user);
    }

    @Override
    public void export(UserQuery query, HttpServletResponse response) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getNickname()), User::getNickname, "%" + query.getNickname() + "%")
                .eq(StringUtils.isNotBlank(query.getPhone()), User::getPhone, query.getPhone())
                .eq(query.getGender() != null, User::getGender, query.getGender());
        List<User> list = baseMapper.selectList(wrapper);
        List<UserInfoVO> excelData = UserConvert.INSTANCE.convert(list);
        try {
            String fileName = URLEncoder
                    .encode("用户信息" + System.currentTimeMillis() + ".xls", StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            EasyExcelFactory.write(response.getOutputStream(), UserInfoVO.class)
                    .charset(StandardCharsets.UTF_8)
                    .excelType(ExcelTypeEnum.XLS)
                    .sheet()
                    .doWrite(excelData);
        } catch (Exception e) {
            log.error("导出用户信息异常", e);
            throw new ServerException("导出用户信息异常");
        }
    }

    @Override
    public void enabled(Integer userId) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new ServerException("用户不存在");
        }
        user.setEnabled(user.getEnabled() == AccountStatusEnum.ENABLED.getValue() ? 0 : 1);
        baseMapper.updateById(user);
    }
}