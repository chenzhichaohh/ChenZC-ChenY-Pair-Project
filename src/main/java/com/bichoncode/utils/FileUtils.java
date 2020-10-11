package com.bichoncode.utils;


import java.io.*;
import java.util.*;

/**
 * @author ChenYan
 * @create 2020/10/10
 */
public class FileUtils {

    public  static void writeTitle(PrintWriter printWriter, Map<String,String> map){
        Set<String> titles=map.keySet();
        int i=1;
        for(String title:titles){
           printWriter.println(i+":"+title);
           i++;
        }

    }


    // 将答案写入文件中
    public static void  writeAnswer(PrintWriter printWriter,Map<String,String> map){
        Set<String> answer=map.keySet();
        int i=1;
        for (String key :answer){
            String value=map.get(key);
            printWriter.println(i+":"+value);
            i++;
        }
    }




}
