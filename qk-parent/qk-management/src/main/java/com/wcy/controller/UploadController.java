package com.wcy.controller;

import com.wcy.common.Response;
import com.wcy.utils.OssTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UploadController {
    // 注入上传文件的对象
    private final OssTemplate ossTemplate;

    // 接口
    @PostMapping("/upload")
    public Response avatarUpLoad(@RequestParam("image") MultipartFile file) throws IOException {
        // 通过file参数接收文件对象,image是请求体参数
        // 调用上传方法
        String uploadUrl = ossTemplate.fileUpload(file.getOriginalFilename(), file.getInputStream());

        // 返回给前端
        return Response.success(uploadUrl);
    }
}
