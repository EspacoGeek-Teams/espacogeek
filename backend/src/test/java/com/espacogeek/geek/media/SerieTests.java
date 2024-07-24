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

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("Serie Tests")
public class SerieTests {
    private final static String name = "Stranger Things"; // Utilize a serie that have seasons, alternative titles, artwork, genre and external reference
    private Integer id;
    private String alternativeTitle;

    @Autowired
    private MediaService mediaService;

    @BeforeEach
    void init() {
        var media = mediaService.findSerieByIdOrName(null, name).getFirst();

        this.id = media.getId();
        this.alternativeTitle = media.getAlternativeTitles().getFirst().getName();
    }

    @Nested
    @DisplayName("Methods")
    public class SerieMethodTest {

        @Autowired
        private MediaDataController serieController;

        private MediaModel media;

        @BeforeEach
        void init(){
            this.media = mediaService.findSerieByIdOrName(id, name).getFirst();
        }

        @Test
        void updateSerieWithoutArtwork_shouldReturnMediaWithBannerAndCover() {
            var media = this.media;

            media.setBanner(null);
            media.setCover(null);

            media = serieController.updateArtworks(media, null);

            assertNotNull(media.getCover());
            assertNotNull(media.getBanner());
        }

        @Test
        void updateAlternativeTitles_shouldReturnMediaWithAlternativeTitles() {
            var media = this.media;

            media.setAlternativeTitles(null);

            media.setAlternativeTitles(serieController.updateAlternativeTitles(media, null));

            assertNotNull(media.getAlternativeTitles());
        }

        @Test
        @Disabled("Not implemented yet.")
        void updateExternalReferences_shouldReturnMediaWithExternalReference() {}

        @Test
        void updateGenres_shouldReturnMediaWithGenre() {
            var media = this.media;

            media.setGenre(null);

            media.setGenre(serieController.updateGenres(media, null));

            assertNotNull(media.getGenre());
        }

        @Test
        void updateSeason_shouldReturnMediaWithSeason() {
            var media = this.media;

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
                    .variable("id", id)
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByName_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("name", name)
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByAlternativeTitle_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("name", alternativeTitle)
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
