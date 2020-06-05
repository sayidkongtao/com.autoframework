package autoframework.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    private static Logger logger = LogManager.getLogger(Utils.class);

    public static void sleepByMillisecond(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sleepBySecond(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAbsolutePath(String relativePath) {
        String path = Utils.class.getClassLoader().getResource(relativePath).getPath();
        File file = new File(path);
        return file.getAbsolutePath();
    }

    public static String readFile(String relativePath) {
        StringBuilder out = new StringBuilder();
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        String encoding = "Utf-8";
        String filePath = getAbsolutePath(relativePath);
        File file = new File(filePath);
        try {

            if (file.isFile() && file.exists()) {
                InputStreamReader in = null;

                in = new InputStreamReader(new FileInputStream(file), encoding);

                for (; ; ) {
                    int count = 0;
                    count = in.read(buffer, 0, buffer.length);
                    if (count < 0)
                        break;
                    out.append(buffer, 0, count);
                }
                in.close();
            } else {
                logger.info("Can not find the file");
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

        return out.toString();
    }

    public static <T> T deserialize(String sourceFileWithRelativePath, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(readFile(sourceFileWithRelativePath), clazz);
    }

    public static <T> List<T> deserialize(String jsonList) {
        Gson gson = new Gson();
        return gson.fromJson(jsonList, new TypeToken<List<T>>() {
        }.getType());
    }

    public static String toJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static void write(String content, String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(content);
        }
    }

    public static void addAttachment(String instruction, String pathToAttachmentContnet) {
        Path content = Paths.get(pathToAttachmentContnet);
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(instruction, is);
        } catch (Exception ignore) {
            logger.error("Failed to add attachment since " + ignore);
            Allure.addAttachment("Failed to add attachment", "Failed to add attachment since" + ignore);
        }
    }
}
