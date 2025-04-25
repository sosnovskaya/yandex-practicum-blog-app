package edu.misosnovskaya.utils;

import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.exceptions.UploadingFileException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PostProcessUtils {
    public Set<TagEntity> extractHashtags(String input) {
        Set<TagEntity> hashtags = new LinkedHashSet<>();

        Pattern pattern = Pattern.compile("#([a-zA-Z]+)\\s");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            hashtags.add(new TagEntity(matcher.group(1)));
        }

        return hashtags;
    }

    public String storeFileToPath(MultipartFile file, String uploadDir) {
        Path filePath;
        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UploadingFileException(String.format("Could not upload file, [file = %s]", file.toString()), e);
        }

        return filePath.toString();
    }
}
