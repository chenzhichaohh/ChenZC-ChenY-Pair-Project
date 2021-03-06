package com.bichoncode.utils;

import com.bichoncode.bean.BiTreeNode;
import com.bichoncode.exception.CommonException;
import com.bichoncode.bean.Expression;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/11
 */
public class AnswerUtils {



    // 生成题目和答案的映射关系
    public static HashMap<String, String> generateMap(int exam_number, int answer_range) {
        if (exam_number < 1) {
            throw new CommonException("生成题目的个数必须大于0");
        }
        if (answer_range < 1) {
            throw new CommonException("运算数字的范围必须大于等于1");
        }
        // 存储去重之后的题目
        HashMap<String, String> hashMap = new HashMap<>();

       // 存储刚生成的题目（此时未去重）
        HashMap<Expression, String> hashMap2 = new HashMap<>();

        while (hashMap.size() < exam_number) {
            for (int i = 1; hashMap2.size() < exam_number + 2000; ) {
                // 因为在运算的过程中会出现n/0的情况，这时候就会抛异常
                Expression expression = new Expression(3, answer_range);
                if ((hashMap.get(expression.toString()) != null || !"".equals(expression.toString()))
                        &&
                        !expression.divisorIsZero()) {
                    hashMap2.put(expression, expression.getRoot().result.toString());
                    i++;
                }
            }
            // 去重
            HashMap<Expression, String> distincMap = distinc(hashMap2);
            hashMap2.clear();
            hashMap2 = distincMap;


            for (Expression expression : distincMap.keySet()) {
                hashMap.put(expression.toString(), expression.getRoot().result.toString());
                if (hashMap.size() == exam_number) {
                    break;
                }
            }

        }

        return hashMap;
    }

    public static void compare(File answerFile, File exerciseFile) throws IOException {
        if (!exerciseFile.exists()) {
            throw new CommonException("练习答案文件不存在");
        }
        if (!answerFile.exists()) {
            throw new CommonException("答案文件不存在");
        }
        // key是题号，value是答案
        Map<Integer, String> exerciseMap = new HashMap<>();
        Map<Integer, String> answerMap = new HashMap<>();
        // 对比的结果
        List<Integer> rightRsult=new LinkedList<>();
        List<Integer>  errorRsult=new LinkedList<>();
        InputStreamReader exerciseIn = new InputStreamReader(new FileInputStream(exerciseFile.getAbsolutePath()), StandardCharsets.UTF_8);
        InputStreamReader answerIn = new InputStreamReader(new FileInputStream(answerFile.getAbsolutePath()), StandardCharsets.UTF_8);
        BufferedReader exerciseReader = new BufferedReader(exerciseIn);
        BufferedReader answerReader = new BufferedReader(answerIn);
        String string = null;
        // 存储的练习答案
        while ((string = exerciseReader.readLine()) != null) {
            string = string.replaceAll(" +", "");
            string = string.replaceAll("\uFEFF", "");
            String TEXT=string.split("[:]")[0];
            exerciseMap.put(Integer.valueOf(string.split("[:]")[0]), string.split(":")[1].split("=")[1]);
        }
        while ((string = answerReader.readLine()) != null) {
            string = string.replaceAll(" +", "");
            string = string.replaceAll("\uFEFF", "");
            answerMap.put(Integer.valueOf(string.split("[:]")[0]), string.split(":")[1]);
        }
        exerciseReader.close();
        answerReader.close();

        // 比较答案
        for (int i = 1; i <= answerMap.size(); i++){
            if(exerciseMap.containsKey(i)){
                if(exerciseMap.get(i).equals(answerMap.get(i))){
                    rightRsult.add(i);
                }else {
                    errorRsult.add(i);
                }
            }
        }
        // 将比较结果存储到文件中
        File file=new File("Grade.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(" ");
        printWriter.print("Correct:正确题数："+rightRsult.size()+"(");
        System.out.print("Correct:正确题数："+rightRsult.size()+"(");
        for (int str: rightRsult) {
            printWriter.print(str+",");
            System.out.print(str+",");
        }
        printWriter.println(")");
        System.out.println(")");
        printWriter.print("Wrong:错误题数："+errorRsult.size()+"(");
        System.out.print("Wrong:错误题数："+errorRsult.size()+"(");
        for (int str: errorRsult) {
            printWriter.print(str+",");
            System.out.print(str+",");
        }
        printWriter.print(")");
        System.out.print(")");
        printWriter.flush();
        fileWriter.flush();
        printWriter.close();
        fileWriter.close();
        System.out.println("比较完成,");
    }

    /**
     * 去除重复的题目
     * @param map
     * @return
     */
    public static HashMap<Expression, String> distinc(HashMap<Expression, String> map){
        HashMap<Expression, String> distincMap = new HashMap<>();
        List<Expression> repeatList = new ArrayList<>();
        for (String key : map.values()) {
            List<Expression> keyList = getRepratKeys(map, key);
            if (keyList.size() > 1) {
                // 获取两个表达式的根节点
                BiTreeNode root1 = keyList.get(0).getRoot();
                for (int i = 1; i < keyList.size(); i++){
                    BiTreeNode root2 = keyList.get(i).getRoot();
                    boolean isomorphism = DistincOperatorUtils.isomorphism(root1, root2);
                    if (isomorphism) {
                        // 如果题目重复，则移除第二道
                        //map.remove(keyList.get(i));
                        repeatList.add(keyList.get(i));
                    }
                }

            }
        }
        for (Expression expression : map.keySet()) {
            if (!repeatList.contains(expression)) {
                distincMap.put(expression, expression.getRoot().result.toString());
            }
        }
        return distincMap;
    }


    public static List<Expression> getRepratKeys(Map<Expression, String> map, String value){
        Set set = map.entrySet(); //通过entrySet()方法把map中的每个键值对变成对应成Set集合中的一个对象
        Iterator<Map.Entry<Expression, String>> iterator = set.iterator();
        ArrayList<Expression> arrayList = new ArrayList();
        while(iterator.hasNext()){
            //Map.Entry是一种类型，指向map中的一个键值对组成的对象
            Map.Entry<Expression, String> entry = iterator.next();
            if(entry.getValue().equals(value)){
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }

}
