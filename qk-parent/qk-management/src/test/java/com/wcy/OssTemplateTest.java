package com.wcy;

import com.wcy.utils.AliyunOssProperties;
import com.wcy.utils.OssTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
public class OssTemplateTest {

    @Autowired
    private OssTemplate ossTemplate;

    // 测试
    @Test
    void test() throws FileNotFoundException {
        // 声明文件的stream流
        InputStream inputStream = new FileInputStream("C:\\图吧工具箱\\美图\\135926457_p0.png");
        String url = ossTemplate.fileUpload("小鸟游星野中秋.jpg", inputStream);
        System.out.println(url);

    }
}
