package com.example.uploadfile.controller;

import com.example.uploadfile.service.TestService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created: by 2022-09-09 12:06
 * Author: 张松
 * Description:
 */
@RestController
public class TestController {

    /**
     * Servlet请求域对象
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 注入数据层添加URL业务接口
     */
    @Autowired
    private TestService testService;

    /**
     * 保存物理真实目录路径
     */
    @Value("${uploadPath.realFolder}")
    private String logoRealFolderPath;

    /**
     * 反射虚拟路径
     */
    @Value("${uploadPath.reflexFolder}")
    private String uploadReflexPath;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "imgFile") MultipartFile imgFile) throws IOException {
        if (imgFile != null && !imgFile.isEmpty()) {
            //获取文件名
            String filename = imgFile.getOriginalFilename(); //图片名
            String[] split = new String[0];
            if (filename != null) {
                split = filename.split("\\.");
            }
            //只接受jpg、png、jpeg格式图片文件，其它格式的文件可按需添加判断，主要是为了防止上传恶意文件，加强安全性
            if ("jpg".equalsIgnoreCase(split[1]) || "png".equalsIgnoreCase(split[1]) || "jpeg".equalsIgnoreCase(split[1])) {
                //图片重命名加后缀
                String photoName = UUID.randomUUID().toString().replace("-", "") + "." + split[1];
                File destFile = new File(logoRealFolderPath + File.separator + photoName);
                //判断是否存在, 不存在就创建
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                //压缩图片保存
                Thumbnails.of(imgFile.getInputStream()).scale(0.8).toFile(destFile);
                //获取协议、服务器IP、端口号、工程路径
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                String httpUrl = basePath + uploadReflexPath + photoName;
                System.out.println("完成URL地址 = " + httpUrl);
                //获取到URL后，可以将URL保存到数据库中，以便后续使用，这里就不做演示了，使用Mybatis即可
                if (testService.addUrl(httpUrl) == 0) {
                    return "保存URL到数据库失败";
                } else {
                    return "保存URL到数据库成功，文件地址为：" + httpUrl;
                }
            }
        }
        return "请上传文件后重试";
    }
}
