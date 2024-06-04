package com.crane.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootTest
public class TestYolo {

    @Test
    void test() {
        //        python detect.py --source 'E:\\1.jpg' --img 640 --device 0 --weights 'weights/best3.pt' --name exp
        // 设置Python解释器和Python脚本路径
        String pythonInterpreter = "D:\\Anaconda3\\envs\\yolov9\\python"; // 根据实际情况修改解释器路径
        String pythonScript = "D:\\gitCodePython\\yolov9\\detect.py"; // 根据实际情况修改脚本路径
        String arg1 = "--source";
        String arg2 = "E:\\yoloTest";
        String arg3 = "--img";
        String arg4 = "640";
        String arg5 = "--device";
        String arg6 = "0";
        String arg7 = "--weights";
        String arg8 = "D:\\gitCodePython\\yolov9\\weights\\best3.pt";
        String arg9 = "--name";
        String arg10 = "exp";
        String[] arg = new String[] { pythonInterpreter, pythonScript, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10};
        try {
            Process proc = Runtime.getRuntime().exec(arg);  //执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();


        }

    }

}
