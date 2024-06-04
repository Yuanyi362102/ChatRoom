package DataBase;

import java.util.Random;

public class RandomEightDigitNumber {
    public static int RandomGenerate(){
        // 创建Random对象
        Random random = new Random();
        
        // 生成随机的8位数
        int randomNumber = random.nextInt(90000000) + 10000000; // 生成介于10000000到99999999的随机数
        
        return randomNumber;
    }
}