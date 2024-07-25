package com.espacogeek.geek.media;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("Serie Tests")
public class SerieTests {
    private final static String name = "Stranger Things"; // Utilize a serie that have seasons, alternative titles, artwork, genre and external reference

    @Autowired
    private MediaService mediaService;

    private MediaModel mediaTest;

    @BeforeEach
    void init() {
        this.mediaTest = mediaService.findSerieJoinFetchedByIdOrName(null, name).getFirst();
    }

    @Nested
    @DisplayName("Methods")
    public class SerieMethodTest {

        @Autowired
        private MediaDataController serieController;

        @Test
        void updateSerieWithoutArtwork_shouldReturnMediaWithBannerAndCover() {
            var media = mediaTest;

            media.setBanner(null);
            media.setCover(null);

            media = serieController.updateArtworks(media, null);

            assertNotNull(media.getCover());
            assertNotNull(media.getBanner());
        }

        @Test
        void updateAlternativeTitles_shouldReturnMediaWithAlternativeTitles() {
            var media = mediaTest;

            media.setAlternativeTitles(null);

            media.setAlternativeTitles(serieController.updateAlternativeTitles(media, null));

            assertNotNull(media.getAlternativeTitles());
        }

        @Test
        @Disabled("Not implemented yet.")
        void updateExternalReferences_shouldReturnMediaWithExternalReference() {}

        @Test
        void updateGenres_shouldReturnMediaWithGenre() {
            var media = mediaTest;

            media.setGenre(null);

            media.setGenre(serieController.updateGenres(media, null));

            assertNotNull(media.getGenre());
        }

        @Test
        void updateSeason_shouldReturnMediaWithSeason() {
            var media = mediaTest;

            media.setSeason(null);

            media.setSeason(serieController.updateSeason(media, null));

            assertNotNull(media);
        }
    }

    @Nested
    @DisplayName("Requests")
    public class SerieRequestTest extends RequestClient {

        @Test
        void querySerieById_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("id", mediaTest.getId())
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByName_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("name", mediaTest.getName())
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByAlternativeTitle_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("name", mediaTest.getAlternativeTitles().getFirst())
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByIdNull_shouldReturnEmpty() {
            var response = tester.documentName("media")
                    .variable("id", "")
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeLessThan(1);
        }

        @Test
        void querySerieByNameNull_shouldReturnEmpty() {
            var response = tester.documentName("media")
                    .variable("name", "")
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeLessThan(1);
        }
    }
}
