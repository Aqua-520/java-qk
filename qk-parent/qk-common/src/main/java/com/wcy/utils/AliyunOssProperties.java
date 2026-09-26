package com.wcy.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
// 此注解会自动将配置文件内容写入属性中
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {
    // 设置为北京
    private String endPoint;
    // 填写Bucket名称，桶的名称。
    private String bucketName;
    // 使用哪一个云
    private String region;

}
