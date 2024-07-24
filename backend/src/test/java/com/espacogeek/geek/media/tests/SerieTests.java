package com.espacogeek.geek.media.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.media.MediaTest;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;

@Nested
@DisplayName("Serie Tests")
public class SerieTests extends MediaTest {
    // Utilize a serie that have seasons, alternative titles, artwork, genre and external reference
    private final static Integer ID = 59191;
    private final static String NAME = "Stranger Things";
    private final static String ALTERNATIVE_TITLES = "Coisas Estranhas";

    @Nested
    @DisplayName("Methods")
    public class SerieMethodTest {
        @Autowired
        private MediaService mediaService;

        @Autowired
        private MediaDataController serieController;

        private MediaModel media;

        @BeforeEach
        void init(){
            this.media = mediaService.findSerieByIdOrName(ID, NAME).getFirst();
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
        void updateExternalReferences_shouldReturnMediaWithExternalReference() {
            throw new UnsupportedOperationException("Test for updateExternalReferences not implemented");
        }

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
    public class SerieRequestTest {

        @Test
        void querySerieById_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("id", ID)
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByName_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("name", NAME)
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieByAlternativeTitle_shouldReturnSomething() {
            var response = tester.documentName("media")
                    .variable("name", ALTERNATIVE_TITLES)
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
