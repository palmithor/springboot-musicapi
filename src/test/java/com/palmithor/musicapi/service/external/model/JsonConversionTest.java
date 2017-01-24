package com.palmithor.musicapi.service.external.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.junit.Before;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import static org.junit.Assert.fail;

/**
 * Base class for Json Conversion tests to extract away boilerplate code
 *
 * @author palmithor
 * @since 24.1.2017.
 */
abstract class JsonConversionTest<T> {

    private Gson gson;

    @Before
    public void setup() {
        this.gson = new GsonBuilder().create();
    }


    T readJsonFromFile(final String filename, final Class<T> clazz) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL urlResource = classLoader.getResource(filename);
        if (urlResource != null) {
            final String fullPath = urlResource.getFile();
            JsonReader reader = new JsonReader(new FileReader(fullPath));
            return gson.fromJson(reader, clazz);
        } else {
            fail();
            return null;
        }
    }
}
