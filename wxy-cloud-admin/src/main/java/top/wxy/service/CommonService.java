package top.wxy.service;


import org.springframework.web.multipart.MultipartFile;
import top.wxy.model.vo.FileUrlVO;

public interface CommonService {

    FileUrlVO upload(MultipartFile uploadFile);
}
