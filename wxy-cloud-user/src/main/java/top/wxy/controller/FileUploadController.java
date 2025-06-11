package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.wxy.dto.UserDTO;
import top.wxy.framework.common.utils.Result;
import top.wxy.service.UserService; // 添加这个导入
import top.wxy.storage.service.AliyunStorageService;

import java.io.IOException;

/**
 * @author 笼中雀
 */
@RestController
@RequestMapping("api/file")
@Tag(name = "⽂件上传")
@AllArgsConstructor
public class FileUploadController {
    private final AliyunStorageService storageService;
    private final UserService sysUserService; // 添加这个字段
    @PostMapping("upload")
    @Operation(summary = "上传")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的⽂件");
        }
        // 上传路径
        String path = storageService.getPath(file.getOriginalFilename());
        // 上传⽂件
        String url = storageService.upload(file.getBytes(), path);
        return Result.ok(url);
    }

    @PostMapping("upload/avatar")
    @Operation(summary = "上传头像")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的头像");
        }
        // 上传路径
        String path = storageService.getPath(file.getOriginalFilename());
        // 上传文件
        String url = storageService.upload(file.getBytes(), path);
        
        // 自动更新用户头像
        UserDTO userDTO = new UserDTO();
        userDTO.setAvatarUrl(url);
        sysUserService.update(userDTO);
        
        return Result.ok(url);
    }
}