package com.study.springboot.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    // @Value("121.89.200.109")
    // private String ip;

    private static final String ip = "localhost";

    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        // 文件夹的绝对路径
        // 定义文件的唯一标识(前缀)
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag + "_" + originalFilename;
        // 把文件写入到上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.success("http://" + ip + ":" + port + "/files/" + flag);
    }

    /**
     * 富文本上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        // 文件夹的绝对路径
        // 定义文件的唯一标识(前缀)
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag + "_" + originalFilename;
        // 把文件写入到上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        String url = ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        return json;
    }



    /**
     * 文件下载接口
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFile(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        // 定义文件上传的根路径
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";
        // 获取所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(basePath);
        // 找到跟参数一致的文件
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if(StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                // 通过文件的路径 读取文件的字节流
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                // 通过输出流 返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}
