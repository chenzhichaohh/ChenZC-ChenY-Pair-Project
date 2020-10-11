package com.bichoncode;

import com.bichoncode.utils.AnswerUtils;
import com.bichoncode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

class ChenzcCyPairProjectApplicationTests {

    /**
     * 测试生成题目
     */
    @Test
    void testProductExercise() {
        // 运算数字的范围
        int range = 5;
        // 题目数量
        int number = 5;

        // 出题和生成答案
        HashMap<String, String> map = AnswerUtils.generateMap(number, range);
        File file = new File("F:exercises.txt");
        File answerFile = new File("F:answerfile.txt");
        try {// 写入题目
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            FileUtils.writeTitle(printWriter, map);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {// 写入答案
            FileWriter fileWriter = new FileWriter(answerFile, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            FileUtils.writeAnswer(printWriter, map);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试练习题和答案的比较
     */
    @Test
    void testCompare() throws IOException {
        File execiseFile = new File("F:exercises.txt");
        File answerFile = new File("F:answerfile.txt");
        AnswerUtils.compare(answerFile, execiseFile);
    }



}
