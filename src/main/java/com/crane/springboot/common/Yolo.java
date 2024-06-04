package com.crane.springboot.common;

import org.python.core.io.BufferedWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Yolo {

    public static ArrayList<String> useYolo(String imageURL) {
        ArrayList<String> list = new ArrayList<>();
//        python detect.py --source 'E:\\1.jpg' --img 640 --device 0 --weights 'weights/best3.pt' --name exp
        // 设置Python解释器和Python脚本路径
        String pythonInterpreter = "D:\\Anaconda3\\envs\\yolov9\\python"; // 根据实际情况修改解释器路径
        String pythonScript = "D:\\gitCodePython\\yolov9\\detect.py"; // 根据实际情况修改脚本路径
        String arg1 = "--source";
//        String arg2 = "E:\\1.jpg";
        String arg3 = "--img";
        String arg4 = "640";
        String arg5 = "--device";
        String arg6 = "0";
        String arg7 = "--weights";
        String arg8 = "D:\\gitCodePython\\yolov9\\weights\\best3.pt";
        String arg9 = "--name";
        String arg10 = "exp";
        String[] arg = new String[] { pythonInterpreter, pythonScript, arg1, imageURL, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10};
        try {
            Process proc = Runtime.getRuntime().exec(arg);  //执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
//                System.out.println(line);
                list.add(line);
            }
            in.close();
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();


        }
        return list;
    }
}


