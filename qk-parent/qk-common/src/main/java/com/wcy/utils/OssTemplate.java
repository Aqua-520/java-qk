package com.wcy.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.wcy.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// 做文件上传的类
@Component
public class OssTemplate {
    // 导入配置类对象
    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    /**
     * 接收文件名称和文件流,返回一个文件的url地址
     * @return 返回url地址
     */
    public String fileUpload(String fileName, InputStream fileInputStream){
        // 创建客户端对象
        OSS ossClient = null;
        try {
            // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET。
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

            // 创建OSSClient实例。
            // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
            ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
            clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
            ossClient = OSSClientBuilder.create()
                    .endpoint(aliyunOssProperties.getEndPoint())
                    .credentialsProvider(credentialsProvider)
                    .clientConfiguration(clientBuilderConfiguration)
                    .region(aliyunOssProperties.getRegion())
                    .build();

            // 调用格式化文件名方法
            String objectName = getObjectName(fileName);

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunOssProperties.getBucketName(), objectName, fileInputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            return getFileNameUrl(objectName);
        } catch (Exception oe) {
            // 打印异常栈调用信息
            oe.printStackTrace();
            throw new BusinessException("阿里云上传文件失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    // 抽取的公共代码
    @NonNull
    private static String getObjectName(String fileName) {
        // 对文件名做改写,并且拼入路径
        // 生成uuid的字符串
        String randomFileName = UUID.randomUUID().toString().replace("-","");
        // 对原始的文件名,将后缀取出来
        String suffix = fileName.substring(fileName.lastIndexOf(".")); // 从后找到.的下标,取到最后
        // 拼接文件路径:阿里云分目录存放
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        // 目录/随机文件名+后缀名为最终的文件名
        return datePath+ "/" + randomFileName + suffix;
    }

    public String getFileNameUrl(String fileName){
        // 字符串切分
        String host = aliyunOssProperties.getEndPoint().replaceFirst("^https?://", "");
        // 拼一个新的地址丢回去
        return String.format("https://%s.%s/%s", aliyunOssProperties.getBucketName(), host, fileName);
    }
}
