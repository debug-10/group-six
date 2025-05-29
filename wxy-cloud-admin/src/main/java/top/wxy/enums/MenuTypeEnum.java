package top.wxy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    /**
     * 菜单目录
     */
    MENU_DIR,
    /**
     * 菜单项
     */
    MENU,
    /**
     * 按钮
     */
    BUTTON;

}
