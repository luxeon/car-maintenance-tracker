package fyi.dslab.car.maintenance.tracker.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

/**
 * Utility matcher that stores the actual response into {@code src/test/resources} on the first run
 * and uses JsonUnit assertions afterwards. This makes it easy to bootstrap new fixtures without
 * manually copying payloads.
 */
public final class JsonFixtureMatcher {

    private static final Path TEST_RESOURCES_ROOT = Path.of("src", "test", "resources");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonFixtureMatcher() {
    }

    public static JsonFixtureMatcher json() {
        return new JsonFixtureMatcher();
    }

    private static void writeFixture(Path fixturePath, String content) {
        try {
            Files.writeString(fixturePath, prettyPrintOrOriginal(content), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write fixture to %s".formatted(fixturePath),
                    e);
        }
    }

    private static String prettyPrintOrOriginal(String json) {
        try {
            Object parsed = OBJECT_MAPPER.readValue(json, Object.class);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(parsed);
        } catch (JsonProcessingException e) {
            return json;
        }
    }

    public ResultMatcher isEqualToFile(String relativePath) {
        return result -> {
            Path fixturePath = TEST_RESOURCES_ROOT.resolve(relativePath);
            String actualResponse = result.getResponse().getContentAsString();

            if (Files.notExists(fixturePath)) {
                Files.createDirectories(fixturePath.getParent());
                writeFixture(fixturePath, actualResponse);
            } else {
                String expectedResponse = Files.readString(fixturePath, StandardCharsets.UTF_8);
                assertThatJson(actualResponse).isEqualTo(expectedResponse);
            }
        };
    }
}
