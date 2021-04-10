package com.suyong.myglide.Glide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static MessageDigest digest;
    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encode(String key){
        if(null == key) return String.valueOf(key.hashCode());
        digest.update(key.getBytes());
        return convertToHexString(digest.digest());
    }

    private static String convertToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for(byte b:bytes){
            String hex = Integer.toHexString(0xFF & b );
            if(hex.length() == 1) sb.append(hex);
            sb.append(hex);
        }
        return sb.toString();
    }
}
