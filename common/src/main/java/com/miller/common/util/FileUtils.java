package com.miller.common.util;


import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Calendar;

public class FileUtils {
    /**
     * 读取一个文件，返回文件中的字符串
     *
     * @param fileCompletePath 文件全路径
     * @return 返回文件中的字符串
     */
    public static String readTextFormFile(String fileCompletePath) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileCompletePath)));
            String str;
            while (null != (str = bufferedReader.readLine())) {
                result.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 根据文件名称返回 resource 目录下的文件名对应的路径
     *
     * @param filePath resource 目录下的文件名
     * @return
     */
    public static String getFilePathFormResource(String filePath) {
        try {
            String result = FileUtils.class.getClassLoader().getResource(filePath).getPath();
            return result;

        } catch (NullPointerException npe) {
            return null;
        }
    }

    /**
     * 写入字符到文件，支持中文
     *
     * @param text
     * @param filePath
     */
    public static void writeTextToFile(String text, String filePath) {
        File sourceFile = new File(filePath);
        // 修改写入文件支持中文
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            writer.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝文件(删除源文件)
     * 功能：如果源文件和目标文件文件名相同，刚无需COPY，其它情况拷贝文件,且拷贝完后删除源文件。
     *
     * @param sourceFilePath 源文件文件名
     * @param targetFilePath 目标文件文件名
     */
    public static String doCopyAndDelSrc(String sourceFilePath, String targetFilePath) throws IOException {
        targetFilePath = doCopy(sourceFilePath, targetFilePath);
        File temp = new File(sourceFilePath);
        if(!temp.delete()) throw new RuntimeException("删除文件失败");
        return targetFilePath;
    }

    /**
     * 拷贝文件
     * 功能：如果源文件和目标文件文件名相同，刚无需COPY，其它情况拷贝文件。
     *
     * @param sourceFilePath 源文件文件名
     * @param targetFilePath 目标文件文件名
     * @throws IOException
     */
    public static String doCopy(String sourceFilePath, String targetFilePath) throws IOException {
        if (!sourceFilePath.equals(targetFilePath)) {
            File inFile = new File(sourceFilePath);
            File outFile = new File(targetFilePath);
            //判断父路径是否存在，不存在创建一个
            if (!outFile.getParentFile().exists()) {
                if(!outFile.getParentFile().mkdirs()){
                    throw new IOException("文件Copy出错！错误信息：创建目标文件路径失败");
                }
            }
            try {
                FileCopyUtils.copy(inFile, outFile);
            } catch (IOException e) {
                targetFilePath = "";
                throw new IOException("文件Copy出错！错误信息：" + e.getMessage(), e);
            }
        }
        return targetFilePath;
    }

    /**
     * 拷贝整个文件夹
     * @param 	source_dir	原始文件（目录）
     * @param 	target_dir	目标文件（目录）
     * @return	复制成功返回true，失败返回false
     * @throws IOException
     */
    public static boolean doCopyDir(String source_dir, String target_dir) throws IOException {
        //顶层目录
        File source_file = new File(source_dir);
        File target_file = new File(target_dir);
        //复制目录
        if (source_file.isDirectory()) {
            //创建顶层目录
            if (!target_file.isDirectory()){
                if(!target_file.mkdirs()){
                    throw new IOException("文件Copy出错！错误信息：创建目标文件路径失败");
                }
            }
            //获取当前目录下的所有文件或目录
            File[] sourceDir = source_file.listFiles();
            //逐个拷贝到顶层目录下
            if(sourceDir != null){
                for (int i = 0; i < sourceDir.length; i++) {
                    if (null != sourceDir[i] && sourceDir[i].isFile()) {
                        //拷贝文件
                        File targetFile = new File(target_dir + File.separator + sourceDir[i].getName());
                        try {
                            FileCopyUtils.copy(sourceDir[i], targetFile);
                        } catch (IOException e) {
                            throw new IOException("文件Copy出错！错误信息：" + e.getMessage(), e);
                        }
                    }

                }
            }
        }
        //复制成功
        return true;
    }

    public static String copyFile(String tempPath, String dir, String uploadDir) throws Exception
    {
        String fileName = tempPath.substring(tempPath.lastIndexOf("/") + 1);
        if (tempPath.contains("/temp/")){
            String tempFile = uploadDir + "//temp//" + fileName;
            String targetFile = uploadDir + "//" + dir + "//" + fileName;
            doCopyAndDelSrc(tempFile, targetFile);
        }
        return dir + "/" + fileName;
    }

    private static String PATH_SEPARATE = "/";
    /**
     * 创建目录
     *
     * @param dirPath
     */
    public static void mkdirs(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                throw new RuntimeException("创建目录失败");
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }

    public static boolean deleteDirectory(String dirPath) {
        return deleteDirectory(new File(dirPath));
    }

    /**
     * 删除文件和目录
     *
     * @param file
     * @return
     */
    public static boolean deleteDirectory(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File childrenFile : listFiles) {
                    FileUtils.deleteDirectory(childrenFile);
                }
            }
        }
        return file.delete();
    }

    /**
     * 获取一个随机的文件名
     *
     * @param realFilename
     * @return
     */
    public static String getRandomFileName(String realFilename) {
        String newFileName = Calendar.getInstance().getTimeInMillis() + RandomUtil.getNumberCode(4);
        int index = realFilename.lastIndexOf(".");
        if (index >= 0) {
            newFileName += realFilename.substring(index);
        }
        return newFileName;
    }

    /**
     * 获取一个新文件对象，存在先删除
     *
     * @param filePath
     * @return
     */
    public static File getNewFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("删除已存在文件失败");
            }
        }
        return file;
    }

    /**
     * 创建一个文件，如果文件存在就先删除再创建一个新的
     *
     * @param filePath 文件完整路径
     * @return 返回新创建的文件
     */
    public static File createNewFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("删除已存在文件失败");
            }
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成带版本号的文件名
     *
     * @param fileName
     * @param
     * @return
     */
    public static String getFileVersionName(String fileName, int version) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(0, index) + version + fileName.substring(index);
    }

    /**
     * 检查文件路径是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean checkFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 返回文件的扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        return getExtension(fileName, "");
    }

    public static String getExtension(String fileName, String ext) {
        if (fileName != null && fileName.length() > 0) {
            int i = fileName.lastIndexOf(".");
            if (i > -1 && i < (fileName.length() - 1)) {
                return fileName.substring(i + 1);
            }
        }
        return ext;
    }

}
