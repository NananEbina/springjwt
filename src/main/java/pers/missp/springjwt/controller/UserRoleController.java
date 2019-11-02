package pers.missp.springjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRoleController {

    @GetMapping("/api1")
    public ResponseEntity<Object> userapi(){
        // 获得通过验证的用户名，进行敏感操作
        // 例如：修改密码时需要验证输入的用户名是否为当前通过验证的用户名
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok("userapi1");
    }
}
