package com.noobzy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * https://blog.csdn.net/x18707731829/article/details/109261718
 */
public class FileTypeUtil {

    // 默认判断文件头前三个字节内容
    public static int default_check_length = 3;
    final static Map<String, String> fileTypeMap = new HashMap<>();

    final static HashSet<String> pictureSet = new HashSet();
    final static HashSet<String> videoSet = new HashSet();
    final static HashSet<String> soundSet = new HashSet();
    final static HashSet<String> compressedSet = new HashSet();
    final static HashSet<String> documentSet = new HashSet();

    final static private String FLIECLASS_PICTURE = "picture";
    final static private String FLIECLASS_SOUND = "sound";
    final static private String FLIECLASS_DOCUMENT = "document";
    final static private String FLIECLASS_ELSE = "else";
    final static private String FLIECLASS_COMPRESSED = "compressed";



    // 初始化文件头类型，不够的自行补充
    static {
        pictureSet.add("jpg");
        pictureSet.add("png");
        pictureSet.add("gif");
        pictureSet.add("tif");
        pictureSet.add("bmp");

        videoSet.add("mp4");
        videoSet.add("rmvb");
        videoSet.add("flv");
        videoSet.add("avi");
        videoSet.add("mpg");
        videoSet.add("mov");
        videoSet.add("swf");
        videoSet.add("mkv");

        soundSet.add("wav");
        soundSet.add("aif");
        soundSet.add("au");
        soundSet.add("mp3");
        soundSet.add("ram");
        soundSet.add("wma");
        soundSet.add("mmf");
        soundSet.add("amr");
        soundSet.add("aac");
        soundSet.add("flac");

        compressedSet.add("rar");
        compressedSet.add("zip");
        compressedSet.add("arj");
        compressedSet.add("gz");
        compressedSet.add("z");
        compressedSet.add("7z");

        documentSet.add("txt");
        documentSet.add("doc");
        documentSet.add("docx");
        documentSet.add("hlp");
        documentSet.add("wps");
        documentSet.add("rtf");
        documentSet.add("html");
        documentSet.add("pdf");
        documentSet.add("xls");
        documentSet.add("xlsx");
        documentSet.add("ppt");
        documentSet.add("pptx");

        fileTypeMap.put("ffd8ffe000104a464946", "jpg");
        fileTypeMap.put("89504e470d0a1a0a0000", "png");
        fileTypeMap.put("47494638396126026f01", "gif");
        fileTypeMap.put("49492a00227105008037", "tif");
        fileTypeMap.put("424d228c010000000000", "bmp");
        fileTypeMap.put("424d8240090000000000", "bmp");
        fileTypeMap.put("424d8e1b030000000000", "bmp");
        fileTypeMap.put("41433130313500000000", "dwg");
        fileTypeMap.put("3c21444f435459504520", "html");
        fileTypeMap.put("3c21646f637479706520", "htm");
        fileTypeMap.put("48544d4c207b0d0a0942", "css");
        fileTypeMap.put("696b2e71623d696b2e71", "js");
        fileTypeMap.put("7b5c727466315c616e73", "rtf");
        fileTypeMap.put("38425053000100000000", "psd");
        fileTypeMap.put("46726f6d3a203d3f6762", "eml");
        fileTypeMap.put("d0cf11e0a1b11ae10000", "doc");
        fileTypeMap.put("5374616E64617264204A", "mdb");
        fileTypeMap.put("252150532D41646F6265", "ps");
        fileTypeMap.put("255044462d312e350d0a", "pdf");
        fileTypeMap.put("2e524d46000000120001", "rmvb");
        fileTypeMap.put("464c5601050000000900", "flv");
        fileTypeMap.put("00000020667479706d70", "mp4");
        fileTypeMap.put("49443303000000002176", "mp3");
        fileTypeMap.put("000001ba210001000180", "mpg");
        fileTypeMap.put("3026b2758e66cf11a6d9", "wmv");
        fileTypeMap.put("52494646e27807005741", "wav");
        fileTypeMap.put("52494646d07d60074156", "avi");
        fileTypeMap.put("4d546864000000060001", "mid");
        fileTypeMap.put("504b0304140000000800", "zip");
        fileTypeMap.put("526172211a0700cf9073", "rar");
        fileTypeMap.put("235468697320636f6e66", "ini");
        fileTypeMap.put("504b03040a0000000000", "jar");
        fileTypeMap.put("4d5a9000030000000400", "exe");
        fileTypeMap.put("3c25402070616765206c", "jsp");
        fileTypeMap.put("4d616e69666573742d56", "mf");
        fileTypeMap.put("3c3f786d6c2076657273", "xml");
        fileTypeMap.put("494e5345525420494e54", "sql");
        fileTypeMap.put("7061636b616765207765", "java");
        fileTypeMap.put("406563686f206f66660d", "bat");
        fileTypeMap.put("1f8b0800000000000000", "gz");
        fileTypeMap.put("6c6f67346a2e726f6f74", "properties");
        fileTypeMap.put("cafebabe0000002e0041", "class");
        fileTypeMap.put("49545346030000006000", "chm");
        fileTypeMap.put("04000000010000001300", "mxp");
        fileTypeMap.put("504b0304140006000800", "docx");
        fileTypeMap.put("6431303a637265617465", "torrent");
        fileTypeMap.put("6D6F6F76", "mov");
        fileTypeMap.put("FF575043", "wpd");
        fileTypeMap.put("CFAD12FEC5FD746F", "dbx");
        fileTypeMap.put("2142444E", "pst");
        fileTypeMap.put("AC9EBD8F", "qdf");
        fileTypeMap.put("E3828596", "pwl");
        fileTypeMap.put("2E7261FD", "ram");
        fileTypeMap.put("664c61", "flac");

    }

    /**
     * @param fileName
     * @return String
     * @description 通过文件后缀名获取文件类型
     * @author xyz
     */
    public static String getFileTypeBySuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * @param inputStream
     * @return String
     * @description 通过文件头魔数获取文件类型
     * @author xyz
     */
    public static String getFileTypeByMagicNumber(InputStream inputStream, String fileName) {
        byte[] bytes = new byte[default_check_length];
        try {
            // 获取文件头前三位魔数的二进制
            inputStream.read(bytes, 0, bytes.length);
            // 文件头前三位魔数二进制转为16进制
            String code = bytesToHexString(bytes);
            String suffix = null;
            for (Map.Entry<String, String> item : fileTypeMap.entrySet()) {
                if (item.getKey().startsWith(code)) {
                    suffix = item.getValue();
                    break;
                }
            }

            if (suffix == null || suffix.isEmpty() || suffix.isBlank()) {
                suffix = getFileTypeBySuffix(fileName);
            }

            if (suffix == null || suffix.isEmpty() || suffix.isBlank()) {
                return FLIECLASS_ELSE;
            }

            if (pictureSet.contains(suffix)) {
                return FLIECLASS_PICTURE;
            } else if (soundSet.contains(suffix)) {
                return FLIECLASS_SOUND;
            } else if (compressedSet.contains(suffix)) {
                return FLIECLASS_COMPRESSED;
            } else if (documentSet.contains(suffix)) {
                return FLIECLASS_DOCUMENT;
            } else {
                return FLIECLASS_ELSE;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param bytes
     * @return String
     * @description 字节数组转为16进制
     * @author xyz
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
