package com.vee.shang.util;


import com.vee.shang.vo.RespFollowVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shiyz
 * @creation date 2012-10-25 上午13:01:21
 *
 */
public class Sort {
    public static void main(String[] args) {
        Sort obj1 = new Sort();
        System.out.println("======================");
        ArrayList list=new ArrayList();
//        list.add("adisen");
//        list.add("bulsi");
//        list.add("?Kobe");
//        list.add("布丁");
//        list.add("杜甫");
//        list.add("鳄鱼");
//        Map map=obj1.sort(list);
//        System.out.println("-------分组后的输出-----------");
//        System.out.println(map.get("A"));
//        System.out.println(map.get("B"));
//        System.out.println(map.get("C"));
//        System.out.println(map.get("D"));
//        System.out.println(map.get("Y"));

        String s = filter("鳄鱼");
        String b = String2AlphaFirst(s, "b");

        System.out.println(s+"----"+b);
    }
    public Sort() {

    }
    //字母Z使用了两个标签，这里有２７个值
    //i, u, v都不做声母, 跟随前面的字母
    private static char[] chartable =
            {
                    '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
                    '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
                    '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'
            };
    private static char[] alphatableb =
            {
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            };
    private static char[] alphatables =
            {
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
            };
    private static int[] table = new int[27];  //初始化
    {
        for (int i = 0; i < 27; ++i) {
            table[i] = gbValue(chartable[i]);
        }
    }
    //主函数,输入字符,得到他的声母,
    //英文字母返回对应的大小写字母
    //其他非简体汉字返回 '0'  按参数
    public static char Char2Alpha(char ch,String type) {
        if (ch >= 'a' && ch <= 'z')
            return (char) (ch - 'a' + 'A');//为了按字母排序先返回大写字母
        // return ch;
        if (ch >= 'A' && ch <= 'Z')
            return ch;

        int gb = gbValue(ch);
        if (gb < table[0])
            return '0';

        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb))
                break;
        }

        if (i >= 26){
            return '0';}
        else{
            if("b".equals(type)){//大写
                return alphatableb[i];
            }else{//小写
                return alphatables[i];
            }
        }
    }
    //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public static String String2Alpha(String SourceStr,String type) {
        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += Char2Alpha(SourceStr.charAt(i),type);
            }
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }
    //根据一个包含汉字的字符串返回第一个汉字拼音首字母的字符串
    public static String String2AlphaFirst(String SourceStr,String type) {
        if(SourceStr.startsWith("鳄")){
            SourceStr="e";
        }
        String Result = "";
        try {
            Result += Char2Alpha(SourceStr.charAt(0),type);
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }
    private static boolean match(int i, int gb) {
        if (gb < table[i])
            return false;
        int j = i + 1;

        //字母Z使用了两个标签
        while (j < 26 && (table[j] == table[i]))
            ++j;
        if (j == 26)
            return gb <= table[j];
        else
            return gb < table[j];
    }

    //取出汉字的编码
    private static int gbValue(char ch) {
        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GBK");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xff00) + (bytes[1] &
                    0xff);
        } catch (Exception e) {
            return 0;
        }
    }
    public Map sort(List<RespFollowVo> list){
        Map map=new HashMap();
        ArrayList arraylist=new ArrayList();
        String[] alphatableb =
                {
                        "A", "B", "C", "D", "E", "F", "G", "H", "I",
                        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
                };
        for(String a:alphatableb){
            for(int i=0;i<list.size();i++){//为了排序都返回大写字母

                if(a.equals(String2AlphaFirst(filter(list.get(i).getNickname().toString()), "b"))){
                    arraylist.add(list.get(i));
                }
            }
            if(arraylist.size()>0){
                map.put(a,arraylist);
            }
            arraylist=new ArrayList();
        }
        return map;
    }

    public static String filter(String str) {
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


}

 