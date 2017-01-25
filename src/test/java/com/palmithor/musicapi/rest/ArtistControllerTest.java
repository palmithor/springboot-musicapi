package com.palmithor.musicapi.rest;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.rest.response.ErrorResponse;
import com.palmithor.musicapi.rest.response.ObjectResponse;
import com.palmithor.musicapi.service.ArtistService;
import com.palmithor.musicapi.service.ServiceError;
import com.palmithor.musicapi.service.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rx.Observable;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

/**
 * @author palmithor
 * @since 25.1.2017.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
public class ArtistControllerTest {

    @Autowired private WebApplicationContext wac;
    @MockBean private ArtistService artistService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void testGetArtistSuccess() throws Exception {
        ArtistDto artistDto = ArtistDto.createBuilder()
                .withMBId(UUID.randomUUID().toString())
                .withDescription("description")
                .build();
        given(artistService.findByMusicBrainzId(artistDto.getMbid()))
                .willReturn(Observable.just(artistDto));

        final MvcResult mvcResult = this.mockMvc.perform(get("/artists/" + artistDto.getMbid())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        ResponseEntity<ObjectResponse<ArtistDto>> responseEntity = (ResponseEntity<ObjectResponse<ArtistDto>>) mvcResult.getAsyncResult();
        assertThat(responseEntity.getStatusCodeValue(), is(HttpStatus.OK.value()));
        ObjectResponse<ArtistDto> responseBody = responseEntity.getBody();
        assertThat(responseBody.getMeta().getCode(), is(20000));
        assertThat(responseBody.getMeta().getMessage(), is("Successful - OK"));
        assertThat(responseBody.getData().getMbid(), is(artistDto.getMbid()));
        assertThat(responseBody.getData().getDescription(), is(artistDto.getDescription()));
    }

    @Test
    public void testGetArtistError() throws Exception {
        ArtistDto artistDto = ArtistDto.createBuilder()
                .withMBId(UUID.randomUUID().toString())
                .withDescription("description")
                .build();
        given(artistService.findByMusicBrainzId(artistDto.getMbid()))
                .willReturn(Observable.error(new ServiceException(ServiceError.MUSIC_BRAIN_ID_NOT_FOUND)));

        final MvcResult mvcResult = this.mockMvc.perform(get("/artists/" + artistDto.getMbid())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        ResponseEntity<ErrorResponse> responseEntity = (ResponseEntity<ErrorResponse>) mvcResult.getAsyncResult();
        assertThat(responseEntity.getStatusCodeValue(), is(HttpStatus.NOT_FOUND.value()));
        ErrorResponse responseBody = responseEntity.getBody();
        assertThat(responseBody.getMeta().getCode(), is(40401));
        assertThat(responseBody.getMeta().getMessage(), is("Music Brainz ID not found"));
    }
}