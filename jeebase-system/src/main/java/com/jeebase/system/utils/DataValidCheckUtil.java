package com.dayan.business.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DataValidCheckUtil {

	/** 手机号码校验规则 */
	private static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
	
	/** 电话号码校验规则 */
	private static final String REGEX_TEL = "^[0][1-9]{2,3}-[0-9]{5,10}$";
	
	/** IP地址校验规则 */
	private static final String REGEX_IP = "(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";
	
	/** 登录名校验规则,账号为3-20位字母或数字开头，允许字母数字下划线 */
	private static final String REGEX_ACCOUNT= "^[a-zA-Z0-9][a-zA-Z0-9_]{2,19}$";
	
	/** 登录密码校验规则,6-20位字母或数字开头 */
	private static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
	
	/** 用户名校验规则,用户名只能包括中文字、英文字母、数字和下划线*/
	private static final String REGEX_USERNAME = "^[\\u0391-\\uFFE5\\w]+$";
	
	/** 邮政编码校验规则,纯数字的6位编码 */
	private static final String REGEX_POSTAL = "^[1-9]\\d{5}(?!\\d) $";
	
	/** QQ号码校验规则,QQ号码为至少5位,且签名不能为0 */
	private static final String REGEX_QQ = "^[1-9][0-9]{4,}$";

	/** 邮箱号码校验规则 */
	private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	
	/** 汉字校验规则 */
	private static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]";
	
	/** 港澳通信证校验规则 */
	private static final String REGEX_HK_AND_XG = "^[HMhm]{1}([0-9]{10}|[0-9]{8})$";
	
	/** 台湾通信证校验规则 */
	private static final String REGEX_TW = "";
	
	/** 护照校验规则 */
	private static final String REGEX_HZ = "^[a-zA-Z0-9]{3,21}$";
	
	/** 真实姓名校验规则,2-30个汉字 */
	private static final String REGEX_REALNAME = "^[\\u4e00-\\u9fa5]{2,30}$";
	
	/** 军官证校验规则 */
	private static final String REGEX_JG = "^[a-zA-Z0-9]{7,21}$";
	
	/** 外国人居留许可证 */
	private static final String REGEX_Jl = "^[a-zA-Z]{3}\\d{12}$";
	
    private static Map<String,Integer> datas = null;
    private static char[] pre17s;
    static int[] power = {1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28};
	// 社会统一信用代码不含（I、O、S、V、Z） 等字母
    static char[] code = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','T','U','W','X','Y'};
	
    
    
    /**
     * 判断是否是一个有效的社会信用代码
     * @param creditCode
     * @return
     */
    static boolean checkIsCreditCode(String creditCode){
        if("".equals(creditCode)||" ".equals(creditCode)){
            return false;
        }else if(creditCode.length()<18){
            return false;
        }else if(creditCode.length()>18){
            return false;
        }else{
            int sum =  sum(pre17s);
            int temp = sum%31;
            temp = temp==0?31:temp;
            return creditCode.substring(17,18).equals(code[31-temp]+"")?true:false;
        }
    }
 
    /**
     * @param chars
     * @return
     */
    private static int sum(char[] chars){
        int sum = 0;
        for(int i=0;i<chars.length;i++){
            int code = datas.get(chars[i]+"");
            sum+=power[i]*code;
        }
        return sum;
 
    }
 
    /**
     * 获取前17位字符
     * @param creditCode
     */
    static  void  pre17(String creditCode){
        String pre17 = creditCode.substring(0,17);
        pre17s = pre17.toCharArray();
    }
 
    /**
     * 初始化数据
     * @param count
     */
    static void  initDatas(int count){
        datas = new HashMap<>();
        for(int i=0;i<code.length;i++){
            datas.put(code[i]+"",i);
        }
        System.out.println();
    }
    
    
	/**
	 * 按照已定义规则校验护照号码
	 * @param number
	 * @return
	 */
	public static boolean checkHz(String number) {
		return Pattern.matches(REGEX_HZ,number);
	}
	
	/**
	 * 按照已定义规则校验外国人居留许可证
	 * @param number
	 * @return
	 */
	public static boolean checkJl(String number) {
		return Pattern.matches(REGEX_Jl, number);
	}
	
	/**
	 * 按照已定义规则校验登录密码
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}
	
	/**
	 * 按照自定义规则校验登录密码
	 * @param regex
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String regex,String password) {
		return Pattern.matches(regex, password);
	}
	
	/**
	 * 按照已定义规则校验军官证号码
	 * @param number
	 * @return
	 */
	public static boolean checkJg(String number) {
		return Pattern.matches(REGEX_JG, number);
	}
	/**
	 * 按照已定义格式校验真实姓名
	 * @param name
	 * @return
	 */
	public static boolean checkRealName(String name) {
		return Pattern.matches(REGEX_REALNAME, name);
	}

	
	/**
	 * 按照已定义格式校验台湾通信证
	 * @param number
	 * @return
	 */
	public static boolean checkTw(String number) {
		return Pattern.matches(REGEX_TW, number);
	}
	
	/**
	 * 按照已定义的格式校验港澳通信证
	 * @param number
	 * @return
	 */
	public static boolean checkHkAndXg(String number) {
		return Pattern.matches(REGEX_HK_AND_XG, number);
	}
	
	/**
	 * 按照自定义的格式校验港澳通信证
	 * @param regex
	 * @param number
	 * @return
	 */
	public static boolean checkHkAndXg(String regex,String number) {
		return Pattern.matches(REGEX_HK_AND_XG, number);
	}
	
	/**
	 * 按照已定义规则校验电话号码
	 * @param tel
	 * @return
	 */
	public static boolean checkTel(String tel) {
		return Pattern.matches(REGEX_TEL, tel);
	}
	
	/**
	 * 按照自定义规则校验电话号码
	 * @param regex
	 * @param tel
	 * @return
	 */
	public static boolean checkTel(String regex,String tel) {
		return Pattern.matches(regex, tel);
	}
	
	/**
	 * 按照已定义规则校验IP地址
	 * @param ip
	 * @return
	 */
	public static boolean checkIp(String ip) {
		return Pattern.matches(REGEX_IP, ip);
	}
	
	/**
	 * 按照自定义规则校验IP地址
	 * @param regex
	 * @param ip
	 * @return
	 */
	public static boolean checkIp(String regex,String ip) {
		return Pattern.matches(regex, ip);
	}
	
	/**
	 * 按照已定义规则校验手机号
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}
	
	/**
	 * 按照自定义规则校验手机号
	 * @param regex
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String regex,String mobile) {
		return Pattern.matches(regex, mobile);
	}
	
	/**
	 * 按照已定义规则校验是否为汉字(单个)
	 * @param chinese
	 * @return
	 */
	public static boolean checkOneChinese(String chinese) {
		return Pattern.matches(REGEX_CHINESE, chinese);
	}
	
	/**
	 * 按照已定义的规则校验是否为汉字(多个)
	 * @param chinese
	 * @return
	 */
	public static boolean checkChinese(String chinese) {
		char[] array = chinese.toCharArray();
		for (char c : array) {
			if (!Pattern.matches(REGEX_CHINESE, c+"")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 	按照已定义规则校验登录名
	 * @param username
	 * @return
	 */
	public static boolean checkUserName(String username) {
		return Pattern.matches(REGEX_USERNAME, username);
	}
	
	/**
	 * 按照自定义规则校验登录名
	 * @param regex
	 * @param username
	 * @return
	 */
	public static boolean checkUserName(String regex,String username) {
		return Pattern.matches(regex, username);
	}
	
	/**
	 *	按照已定义规则校验账号
	 * @param account
	 * @return
	 */
	public static boolean checkAccount(String account) {
		return Pattern.matches(REGEX_ACCOUNT, account);
	}
	
	/**
	 * 	按照自定义规则校验账号
	 * @param regex
	 * @param account
	 * @return
	 */
	public static boolean checkAccount(String regex,String account) {
		return Pattern.matches(regex, account);
	}
	
	
	/**
	 * 	按照已定义规则校验邮箱是否符合规范
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}
	
	/**
	 * 	按照自定义规则校验邮箱是否符合规范
	 * @param regex
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String regex,String email) {
		return Pattern.matches(regex, email);
	}
	
	/**
	 * 	按照已定义规则校验QQ号码
	 * @param qq
	 * @return
	 */
	public static boolean checkQq(String qq) {
		return Pattern.matches(REGEX_QQ, qq);
	}
	
	/**
	 * 	按照自定义规范校验QQ号码
	 * @param regex
	 * @param qq
	 * @return
	 */
	public static boolean checkQq(String regex,String qq) {
		return Pattern.matches(regex, qq);
	}
	
	/**
	 * 按照已定义规则校验邮政编码
	 * @param zipCode
	 * @return
	 */
	public static boolean checkPostal(String zipCode) {
		return Pattern.matches(REGEX_POSTAL, zipCode);
	}
	
	/**
	 * 按照规定规则校验邮政编码
	 * @param regex
	 * @param zipCode
	 * @return
	 */
	public static boolean checkPostal(String regex,String zipCode) {
		return Pattern.matches(regex, zipCode);
	}
	
	/**
	 *  按照regex,校验input
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean check(String regex,String input) {
		return Pattern.matches(regex, input);
	}
	
}
