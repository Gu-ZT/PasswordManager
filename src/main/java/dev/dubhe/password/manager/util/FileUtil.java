package dev.dubhe.password.manager.util;

import dev.dubhe.password.manager.exception.CustomException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileUtil {
    public static @Nullable String readFile(@Nonnull File file) {
        if (!file.isFile() || !file.canRead()) return null;
        List<Character> buf = new ArrayList<>();
        char[] buffer = new char[1024];
        try (FileReader reader = new FileReader(file)) {
            int length;
            while ((length = reader.read(buffer)) > 0) {
                for (int i = 0; i < length; i++) {
                    buf.add(buffer[i]);
                }
            }
            byte[] result = new byte[buf.size()];
            for (int i = 0; i < buf.size(); i++) {
                result[i] = (byte) buf.get(i).charValue();
            }
            return new String(result, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static void writeFile(@Nonnull File file, @Nonnull String string) {
        try {
            if (!file.isFile() && !file.createNewFile()) throw CustomException.error();
            if (!file.canWrite()) throw CustomException.error();
            try (FileWriter writer = new FileWriter(file)) {
                byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
                char[] chars = new char[bytes.length];
                for (int i = 0; i < bytes.length; i++) {
                    chars[i] = (char) bytes[i];
                }
                writer.write(chars);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw CustomException.error(e);
        }
    }
}
