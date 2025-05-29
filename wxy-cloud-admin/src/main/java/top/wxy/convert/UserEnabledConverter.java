package top.wxy.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import top.wxy.enums.AccountStatusEnum;

/**
 * @author mqxu
 * @date 2024/11/18
 * @description UserEnabledConverter
 **/
public class UserEnabledConverter  implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        return new WriteCellData<>(AccountStatusEnum.getNameByValue(context.getValue()));
    }
}