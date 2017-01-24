package com.palmithor.musicapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * Utilities used in tests when working with JSON
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public final class JsonTestUtils {

    private JsonTestUtils() {

    }

    private static Gson gson = new GsonBuilder().create();


    /**
     * Read json from file and return a object using GSON conversion
     *
     * @param filename Relative path of the file inside resources folder - external/CoverArtArchiveResponse.json etc.
     * @param clazz    The type of the returning object
     * @return An object of type <T> from the json file provided
     * @throws FileNotFoundException
     */
    public static <T> T readJsonFromFile(final String filename, final Class<T> clazz) throws FileNotFoundException {
        ClassLoader classLoader = clazz.getClassLoader();
        URL resource = classLoader.getResource(filename);
        if (resource != null) {
            JsonReader reader = new JsonReader(new FileReader(resource.getFile()));
            return gson.fromJson(reader, clazz);
        } else {
            throw new FileNotFoundException("File being referenced was not found");
        }
    }
}
