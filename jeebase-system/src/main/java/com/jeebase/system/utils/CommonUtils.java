package com.jeebase.system.utils;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class CommonUtils {
    
    /**
     * 生成指定长度的随机字符串(仅包含字母和数字)
     * @param len  指定字符串的长度
     * @return
     */
    public static String RandomStr(int len) {
        String strChar = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUWXYZ";
        
        if (len <= 0) {
            return "";
        }
        StringBuilder strRandom = new StringBuilder();
        Random tmp = new Random();
        for (int i = 0; i < len; i++) {
            int j = tmp.nextInt(strChar.length());
            strRandom.append(strChar.charAt(j));
        }
        return strRandom.toString();
    }
    
    /**
     * 获取类路径
     * @return
     */
    public static String getClassesPath(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        return path.replaceFirst("file:/", "");
    }
    
    /**
     * MD5算法
     * 用处：当用户登录的时候，系统把用户输入的密码进行MD5 Hash运算，然后再去和保存在文件系统中的MD5值进行比较，进而确定输入的密码是否正确。通过这样的步骤，
     * 系统在并不知道用户密码的明码的情况下就可以确定用户登录系统的合法性。这可以避免用户的密码被具有系统管理员权限的用户知道
     * @param pwd
     * @return
     */
    public final static String getMD5String(String pwd) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] strTemp = pwd.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将大整数从16进制转为10进制
     * @param bigInt_16     16进制的大整数
     * @return
     */
    public static String bigInt16To10(String bigInt_16) {
        BigInteger bigInt = new BigInteger(bigInt_16, 16);
        return bigInt.toString();
    }
    
    /**
     * 将大整数从10进制转为16进制
     * @param bigInt_10     10进制的大整数
     * @return
     */
    public static String bigInt10To16(String bigInt_10) {
        BigInteger bigInt = new BigInteger(bigInt_10);
        return bigInt.toString(16);
    }
    
    /**
     * 根据信息类型从证书DN中获取单个信息
     * @param certInfo      证书的DN信息
     * @param InfoType      信息类型
     * @return
     */
    public static String getSignCertInfoByType(String certInfo, String InfoType) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] sub = certInfo.split(",");
        for (int i = 0, len = sub.length; i < len; i++) {
            String[] temp = sub[i].split("=");
            if(temp!=null && temp.length>1){
                map.put(temp[0].trim(), temp[1]);
            }
        }
        return (String) map.get(InfoType);
    }
    
    
    /**
     * 计算两个时间相隔天数，多出来的秒数向上多取一天
     * @return  与当前时间的相隔天数
     * @throws InvalidParameterException 
     */
    public static int TimeBetweenDay(Date beforeTime, Date afterTime) throws Exception{
        //时间转换为时间戳比较大小
        long beforeTimeLong = beforeTime.getTime();
        long afterTimeLong = afterTime.getTime();
        if(afterTimeLong < beforeTimeLong){
            throw new Exception("参数错误，第一个时间参数应该比第二个时间参数小");
        }
        long diff = afterTimeLong - beforeTimeLong;
        int days = (int)(diff / (1000 * 60 * 60 * 24));
        if(diff % (1000 * 60 * 60 * 24) > 0){
            days+=1;
        }
        
        return days;
    }
    
    /**
     * 校验身份证号输入是否正确
     * @param idnum
     * @return
     */
    public static String checkIdNumber(String idnum){
        //加权因子
        int[] b = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        
        int sum = 0;
        
        //计算前17身份证号的各位数字与对应的加权因子的乘积的和
        for(int i = 0; i < b.length; i++){
            int x = strToInt(idnum.substring(i, i+1));
            int y = b[i];
//            System.out.println("第"+i+"位，x："+x+",y:"+y);
            sum = sum + (x * y);
        }
        
        //计算出的身份证最后一位的值
        int d = (12 - (sum % 11)) % 11;
        System.out.println("最后一位应为:"+d);
        
        //取出身份证的最后一位，若为“x”则转为10
        int endNum;
        if("x".equalsIgnoreCase(idnum.substring(17))){
            endNum = 10;
        }else {
            endNum = strToInt(idnum.substring(17));
        }
        
        return d==endNum?"匹配成功":"不匹配";
    }
    
    /**
     * 字符串转换为int类型
     * @param str
     * @return
     */
    public static int strToInt(String str){
        return Integer.valueOf(str); 
    }
    
    
    /**
     * 将原数组按照指定的长度分割为若干个子数组
     * @param ary       需要分割的数组
     * @param subSize   分割的子数组大小
     * @return          若干个子数组的集合，Object[ Object1[], Object2[], Object3[]...]
     */
    public static Object[] newSplitArray(Object[] ary, int subSize){
        //根据原数组和分割大小得出子数组的个数
        int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;

        //新的子数组集合
        Object[] rootArray = new Object[count];

        //为每一个子数组赋值
        for(int i = 0; i < count; i++) {
            
            //每一个子数组从原数组中取值的开始下标
            int index = i * subSize;
            
            //最后一个数组的大小根据能否整除来决定
            Object[] subArray;
            if(i < count-1 || ary.length % subSize == 0){
                subArray = new Object[subSize];
            } else {
                subArray = new Object[ary.length % subSize];
            }
            
            int j = 0;
            while (j < subSize && index < ary.length) {
                subArray[j] = ary[index++];
                j++;
            }
            
            //将子数组添加到集合中
            rootArray[i] = subArray;
        }
        return rootArray;
    }

    //获取页数
    public static long getPages(long total, long pageSize) {
        if (pageSize == 0L) {
            return 0L;
        } else {
            long pages = total / pageSize;
            if (total % pageSize != 0L) {
                ++pages;
            }
            return pages;
        }
    }

}
