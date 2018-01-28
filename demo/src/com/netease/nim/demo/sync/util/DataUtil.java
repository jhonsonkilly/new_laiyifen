package com.netease.nim.demo.sync.util;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;


public class DataUtil {

    private static Gson gson = new Gson();

    public static String signRequest(String appid, Object src, String appkey)
            throws Exception {

        String srcText = gson.toJson(src);


        String str = appid + srcText;

        String base64Text = new String(Base64.encodeBase64((appid + srcText)
                .getBytes("utf-8"), false));
        // MD5加密
        String destText = MD5Util.md5Digest(base64Text);
        AlgorithmData data = new AlgorithmData();
        data.setDataMing(destText);
        data.setKey(appkey);
        Algorithm3DES.encryptMode(data);
        return data.getDataMi();
    }

    public static String getHeadPhoneName(String employeCode) {
        String[] lettersa = {"b", "3", "i", "j", "m", "q", "8", "u", "x", "2"};
        String[] lettersb = {"9", "d", "g", "k", "o", "0", "s", "v", "4", "y"};
        String[] lettersc = {"a", "e", "h", "l", "7", "p", "t", "6", "w", "z"};
        String str = getSwapStr(employeCode, lettersa) + getSwapStr(employeCode, lettersb) + getSwapStr(employeCode, lettersc);
        return "co" + str.substring(1, str.length() - 2);
    }

    private static String getSwapStr(String employeCode, String[] letters) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < employeCode.length(); i++) {
            sb.append(letters[employeCode.charAt(i) - '0']);
        }
        return sb.toString();
    }

    //	public static void main(String[] args) throws Exception {
    //		String userid = getHeadPhoneName("00041568");
    //		System.out.println(userid+"$$$"+userid.length());
    ////		String imgFile = "d:\\b.jpg";
    ////		String imgStr = GetImageStr(imgFile);
    ////		String url = upload(userid, imgStr);
    ////		System.out.println(url);
    //	}
}