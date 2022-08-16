package priv.jesse.mall.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;

public class FileUtil {

    /**
     * 保存上传的文件
     *
     * @param file
     * @return 文件下载的url
     * @throws Exception
     */
    public static String saveFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return "";
        }
        // 创建file 流
        File target = new File("file");
        // 测试此抽象路径名表示的文件是否为目录。
        if (!target.isDirectory()) {
            // 创建目录
            target.mkdirs();
        }
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 通过MD5算法格式化
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 使用指定的字节数组更新摘要。
        md.update(file.getBytes());
        // 通过字符串拼接获取文件名
        String fileName = (Helper.bytesToHex(md.digest(),0,md.digest().length-1)) + "." + getPostfix(originalFilename);
        // 创建file 流
        File file1 = new File(target.getPath() + "/" + fileName);
        // file 流写入
        Files.write(Paths.get(file1.toURI()), file.getBytes(), StandardOpenOption.CREATE_NEW);
        return "/mall/admin/product/img/" + fileName;
    }

    /**
     * 获得文件的后缀名
     *
     * @param fileName
     * @return
     */
    public static String getPostfix(String fileName) {
        if (fileName == null || "".equals(fileName.trim())) {
            return "";
        }
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        }
        return "";
    }

}
