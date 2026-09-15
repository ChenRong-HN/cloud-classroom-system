package com.yanque.tool;

import java.util.Random;

/**
 * 验证码生成工具类
 *
 * @author cr
 */
public class ValidateCodeGenerateTool {
    // 数字验证码数组
    public static final String[] numberValidateCodeArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final Random R = new Random();

    /**
     * 生成指定位数的数字验证码数据
     *
     * @param length 验证码长度
     * @return 验证码字符串数据
     */
    public static String generateValidateCode(int length) {
        StringBuilder validateCode = new StringBuilder();
        int bound = numberValidateCodeArray.length;
        for (int i = 0; i < length; i++) {
            validateCode.append(numberValidateCodeArray[R.nextInt(bound)]);
        }
        return validateCode.toString();
    }
}
