/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.uitl;

import java.util.Random;

public class UserKey {
    private final int PRE_LEN = 16;
    private final int SUF_LEN = 16;
    private String prefixKey;
    private String suffixKey;
    char[] dic = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public UserKey() {
        generateKey();
    }

    public String getPrefixKey() {
        return prefixKey;
    }

    public String getSuffixKey() {
        return suffixKey;
    }

    private void generateKey() {
        StringBuffer pre = new StringBuffer();
        Random random = new Random();
        final int len = dic.length;
        for (int i = 1; i <= PRE_LEN; i++) {
            pre.append(dic[random.nextInt(len)]);
        }
        this.prefixKey = pre.toString();
        StringBuffer suf = new StringBuffer();
        for (int i = 1; i <= SUF_LEN; i++) {
            suf.append(dic[random.nextInt(len)]);
        }
        this.suffixKey = suf.toString();
    }

    /**
     *
     * @param cipher: 密文
     * @return 解析后的密码
     */
    public String trimPreAndSuf(String cipher) {
        String substring = cipher.substring(PRE_LEN);
        int index = substring.indexOf(this.suffixKey);
        return substring.substring(0, index);
    }

//    public static void main(String[] args) {
//        UserKey userKey = new UserKey();
//        System.out.println("pre:");
//        System.out.println(userKey.getPrefixKey());
//        System.out.println("suf:");
//        System.out.println(userKey.getSuffixKey());
//        String pas = "huang123";
//        System.out.println(userKey.trimPreAndSuf(userKey.getPrefixKey() + pas + userKey.getSuffixKey()));
//    }
}
