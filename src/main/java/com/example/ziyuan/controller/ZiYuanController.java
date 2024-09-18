package com.example.ziyuan.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.filechooser.FileSystemView;
import java.io.*;

@RestController
@CrossOrigin()
public class ZiYuanController {

    private static final String desktopPath = FileSystemView.getFileSystemView() .getHomeDirectory().getAbsolutePath();

    @PostMapping("/synthesis")
    public String synthesis(String cover,String content) throws IOException {

        String files[] = {cover, content};
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        File skipFile;
        File sizeFile;
        try {
            fileOutputStream = new FileOutputStream(desktopPath + "\\result.jpg");
            byte by[] = new byte[1028 * 8];
            int a = 0;
            for (int i = 0; i < files.length; i++) {
                fileInputStream = new FileInputStream(files[i]);
                while ((a = fileInputStream.read(by)) != -1) {
                    fileOutputStream.write(by, 0, a);
                }
                fileInputStream.close();
            }
            fileOutputStream.close();
            skipFile = new File(cover);
            sizeFile = new File(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            fileInputStream.close();
            fileOutputStream.close();
        }
        return String.valueOf(skipFile.length()) + "," + String.valueOf(sizeFile.length());
    }

    @PostMapping("/decompose")
    public String decompose(String filePath,int skip,int size) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            byte buffSkip[] = new byte[skip];
            fileInputStream.read(buffSkip);
            fileOutputStream = new FileOutputStream(desktopPath + "\\result-1.jpg");
            fileOutputStream.write(buffSkip);
            fileInputStream.close();
            fileOutputStream.close();

            fileInputStream = new FileInputStream(filePath);
            fileInputStream.skip(skip);
            byte buffSize[] = new byte[size];
            fileInputStream.read(buffSize);
            fileOutputStream = new FileOutputStream(desktopPath + "\\result-2.jpg");
            fileOutputStream.write(buffSize);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            fileInputStream.close();
            fileOutputStream.close();
        }
        return "成功";
    }

}
