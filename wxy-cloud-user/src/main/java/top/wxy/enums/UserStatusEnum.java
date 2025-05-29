package top.wxy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author 笼中雀
 */

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    /*
    停用
    */
    DISABLE(0,"停用"),
    ENABLED(1,"正常");

    private final int Value;
    private final String name;

    public static String getNameByValues(int value){
        for(UserStatusEnum s:UserStatusEnum.values()){
            if(s.getValue()==value){
                return s.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for(UserStatusEnum s:UserStatusEnum.values()){
            if(Objects.equals(s.getName(),name)){
                return s.getValue();
            }
        }
        return null;
    }
}
