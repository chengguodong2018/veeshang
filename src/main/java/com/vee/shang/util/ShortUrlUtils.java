package com.vee.shang.util;

import org.springframework.util.StringUtils;

/**
 * @author admin
 * @date 2017/5/12.
 */
public class ShortUrlUtils {

    /**
     * 短链接生成
     * @param url
     * @return
     */
    public static String shortUrl(String url,String channel) {

        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "kaishu"; //+ System.currentTimeMillis();
        if(!StringUtils.isEmpty(channel)){
            key += channel;
        }

        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"
        };

        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = (MD5Util.getMD5String(key + url));
        String hex = sMD5EncryptResult;

        String resUrl ="";
        for ( int i = 0; i < 1; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            String outChars = "" ;
            for ( int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[( int ) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl = outChars;
        }
        return resUrl;
    }

    public static void main(String[] args) {
        String resUrl = shortUrl("https://weixin.kaishustory.com/mp/oauth/?appid=wx846bfb98437c580b&redirect_uri=https://weixin" +
                ".kaishustory" +
                ".com/ksweb/page/custom.html&scope=snsapi_userinfo&custom=%7B%0A%20%20%22type%22%20:%20%22treat%22," +
                "%0A%20%20%22treatId%22%20:%20%221443325%22,%0A%20%20%22accountType%22%20:%20%221%22%0A%7D&referid=291","noraml");
        System.out.println(resUrl);

        String resUrl1 = shortUrl("https://weixin.kaishustory.com/ksweb/page/custom.html?type=album&id=179&albumName=%E9%A9%AC%E7%91%9E%C2%B7%E6%90%9E%E5%AE%9A%E5%AD%A9%E5%AD%90%E5%8F%91%E8%84%BE%E6%B0%9436%E8%AE%A1&referid=1418097","noraml");
        System.out.println(resUrl1);
    }
}
