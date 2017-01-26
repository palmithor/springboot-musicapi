package com.palmithor.musicapi.util;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.service.MessageByLocaleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author palmithor
 * @since 24.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MessageByLocaleServiceTest {

    @Autowired MessageByLocaleService messageByLocaleService;

    @Test
    public void testGetMessageWithDefaultLocale() throws Exception {
        assertThat(messageByLocaleService.getMessage(MessageByLocaleService.Errors.MBID_NOT_FOUND), is("Music Brainz ID not found"));
    }
}