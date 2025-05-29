package top.wxy.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.wxy.convert.GenderConverter;
import top.wxy.convert.UserEnabledConverter;

import java.time.LocalDateTime;


/**
 * @author 笼中雀
 */
@Data
@Schema(name = "UserInfoVO", description = "用户信息返回vo")
public class UserInfoVO {
    @Schema(name = "pk_id", description = "用户id")
    @ExcelIgnore
    private Integer pkId;

    @Schema(name = "nickname", description = "昵称")
    @ExcelProperty("昵称")
    private String nickname;

    @Schema(name = "phone", description = "手机号")
    @ExcelProperty("手机号")
    private String phone;

    @Schema(name = "wxOpenId", description = "微信openid")
    @ExcelProperty("微信openid")
    private String wxOpenId;

    @Schema(name = "avatar", description = "头像")
    @ExcelProperty("头像")
    private String avatar;

    @Schema(name = "gender", description = "性别")
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    private Integer gender;

    @Schema(name = "birthday", description = "生日")
    @ExcelProperty("生日")
    private String birthday;

    @Schema(name = "bonus", description = "积分")
    @ExcelProperty("积分")
    private Integer bonus;

    @Schema(name = "remark", description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "账户状态")
    @ExcelProperty(value = "账户状态", converter = UserEnabledConverter.class)
    private Integer enabled;

    @Schema(name = "createTime", description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}