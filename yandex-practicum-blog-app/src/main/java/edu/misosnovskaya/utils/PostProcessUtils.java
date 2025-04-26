package edu.misosnovskaya.utils;

import edu.misosnovskaya.configuration.AppValues;
import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.exceptions.UploadingFileException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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
@AllArgsConstructor
public class PostProcessUtils {

    private final AppValues appValues;
    public Set<TagEntity> extractHashtags(String input) {
        Set<TagEntity> hashtags = new LinkedHashSet<>();

        Pattern pattern = Pattern.compile("#([a-zA-Z]+)\\s");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            hashtags.add(new TagEntity(matcher.group(1)));
        }

        return hashtags;
    }

    public String storeFileToPath(MultipartFile file) {
        Path filePath;
        try {
            Path uploadPath = Paths.get(appValues.getUploadDir());

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

    public ByteArrayResource getFileFromPath(String filePath) {
        try {
            Path uploadPath = Paths.get(filePath);
            byte[] fileContent = Files.readAllBytes(uploadPath);
            return new ByteArrayResource(fileContent);
        } catch (IOException e) {
            throw new UploadingFileException(String.format("Could not load file from path, [filePath = %s]", filePath), e);
        }
    }

    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new UploadingFileException("Failed to delete file: " + filePath, e);
        }
    }
}
