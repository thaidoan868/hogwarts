package vn.conguyetduong.hogwarts.business.util;

import java.security.SecureRandom;

public class Generator {
    public static String code(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // 0-9
        }
        return sb.toString();
    }
}
