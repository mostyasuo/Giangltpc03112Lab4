package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CookieService;
import com.example.demo.service.ParamService;
import com.example.demo.service.SessionService;

@Controller
public class AccountController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/account/login")
    public String login1() {
        return "/account/login";
    }

    @PostMapping("/account/login")
    public String login2() {
        String un = paramService.getString("username", "");
        String pw = paramService.getString("password", "");
        boolean rm = paramService.getBoolean("remember", false);

        if (un.equals("poly") && pw.equals("123")) {
            // Lưu username vào session
            sessionService.set("username", un);

            if (rm) {
                // Ghi nhớ tài khoản trong 10 ngày
                cookieService.add("user", un, 10);
            } else {
                // Xóa cookie tài khoản đã ghi nhớ trước đó
                cookieService.remove("user");
            }

            // Chuyển hướng đến trang thành công
            return "redirect:/account/success";
        }

        // Chuyển hướng đến trang đăng nhập với thông báo lỗi
        return "redirect:/account/login?error=true";
    }

    @GetMapping("/account/register")
    public String register1() {
        return "/account/register";
    }

    @PostMapping("/account/register")
    public String register2(@RequestParam("file") MultipartFile file) {
        // Lưu hình vào thư mục
        String imagePath = "/path/to/image/folder";
        File savedImage = paramService.save(file, imagePath);
        // Xử lý đăng ký và lưu thông tin vào database
        System.out.println(savedImage);
        // Chuyển hướng đến trang thành công
        return "redirect:/account/success";
    }

    
}
