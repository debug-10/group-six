package top.wxy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("")
    public String index() {
        return "您好，项目已启动，祝您使用愉快！";
    }
}
