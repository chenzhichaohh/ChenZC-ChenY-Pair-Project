package com.bichoncode;

import com.bichoncode.exception.CommonException;
import com.bichoncode.utils.FileUtils;
import com.bichoncode.utils.AnswerUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 运算数字的范围
        int range = 0;
        // 题目数量
        int number = 0;

        System.out.println("1.生成题目请输入： -n 题目树龄 -r 题目数字的范围。例如：-n 5 -r 5");
        System.out.println("2.对照答案请输入： -e：练习题的绝对路径 -a： 答案的绝对路径。例如: -e F:exercises.txt -a F:answerfile.txt");
        System.out.println("请输入");
        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        args = string.split("\\s+");

        // 判断参数是否正确
        if (args.length < 4) {
            throw new CommonException("请检查参数是否正确");
        }

        // 获取参数
        for (int i = 0; i < args.length; i++) {
            if ("-n".equals(args[i])) {
                number = Integer.parseInt(args[i + 1]);
                i++;
            } else if ("-r".equals(args[i])) {
                range = Integer.parseInt(args[i + 1]);
                i++;
            } else {
                break;
            }
        }
        // 判断是否生成题目,如果不是生成题目，则是对照答案
        // range == 0 && number == 0意味着没有输入这两个参数的值，则是对照答案
        // 生成题目和答案: -n 5 -r 5
        // 对照答案：-e <exercisefile>.txt -a <answerfile>.txt

        if (range == 0 && number == 0) { // 对照答案 -e F:exercises.txt -a F:answerfile.txt
            String answerFileName;
            String execiseFileName;
            execiseFileName = args[1];
            answerFileName = args[3];

            File answerFile = new File(answerFileName);
            File exerciseFile = new File(execiseFileName);
            AnswerUtils.compare(answerFile, exerciseFile);
        } else {
            // -n 5 -r 5
            // 出题和生成答案
            HashMap<String, String> map = AnswerUtils.generateMap(number, range);
            File file = new File("F:exercises.txt");
            //File file = new File(args[1]);
            File answerFile = new File("F:answerfile.txt");
            //File answerFile = new File(args[3]);
            try {
                // 将生成的题目写入文件中
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                FileUtils.writeTitle(printWriter, map);
                printWriter.flush();
                fileWriter.flush();
                printWriter.close();
                fileWriter.close();
                System.out.println("题目已生成，文件路径为F:exercises.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 将答案写入文件中
                FileWriter fileWriter = new FileWriter(answerFile, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                FileUtils.writeAnswer(printWriter, map);
                printWriter.flush();
                fileWriter.flush();
                printWriter.close();
                fileWriter.close();
                System.out.println("答案已生成，文件路径为F:answerfile.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
}
