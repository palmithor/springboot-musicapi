package com.palmithor.musicapi.service.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.palmithor.musicapi.util.Rfc339DateJsonAdapter;
import org.junit.Before;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

/**
 * Base class for Json Conversion tests to extract away boilerplate code
 *
 * @author palmithor
 * @since 24.1.2017.
 */
abstract class JsonConversionTest<T> {

    Gson gson;

    @Before
    public void setup() {
        this.gson = new GsonBuilder().create();
    }


    public T readJsonFromFile(final String filename, final Class<T> clazz) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        final String fullPath = classLoader.getResource(filename).getFile();
        JsonReader reader = new JsonReader(new FileReader(fullPath));
        return gson.fromJson(reader, clazz);
    }
}
