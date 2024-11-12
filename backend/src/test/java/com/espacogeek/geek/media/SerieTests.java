package com.espacogeek.geek.media;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.TypeReferenceService;
import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("Serie Tests")
public class SerieTests {
    private final static String NAME = "The Boys"; // Utilize a serie that have seasons, alternative titles, artwork, genre and external reference

    @Autowired
    private MediaService mediaService;

    private MediaModel mediaTest;

    @BeforeEach
    void init() {
        Pageable pageable = PageRequest.of(0, 10);
        this.mediaTest = mediaService.findSerieByIdOrName(null, NAME, pageable).getContent().get(0);
    }

    @Nested
    @DisplayName("Methods")
    public class SerieMethodTest {

        @Autowired
        private MediaDataController serieController;

        @Autowired
        private MediaApi tvSeriesApi;

        @Autowired
        private TypeReferenceService typeReferenceService;

        private TypeReferenceModel typeReference;

        @BeforeEach
        void init() {
            typeReference = typeReferenceService.findById(MediaDataController.TMDB_ID).orElseThrow();
        }

        @Test
        void updateSerieWithoutArtwork_shouldReturnMediaWithBannerAndCover() {
            var media = mediaTest;

            media.setBanner(null);
            media.setCover(null);

            media = serieController.updateArtworks(media, null, typeReference, tvSeriesApi);

            assertNotNull(media.getCover());
            assertNotNull(media.getBanner());
        }

        @Test
        void updateAlternativeTitles_shouldReturnMediaWithAlternativeTitles() {
            var media = mediaTest;

            media.setAlternativeTitles(null);

            media.setAlternativeTitles(serieController.updateAlternativeTitles(media, null, typeReference, tvSeriesApi));

            assertNotNull(media.getAlternativeTitles());
        }

        @Test
        @Disabled("Not implemented yet.")
        void updateExternalReferences_shouldReturnMediaWithExternalReference() {}

        @Test
        void updateGenres_shouldReturnMediaWithGenre() {
            var media = mediaTest;

            media.setGenre(null);

            media.setGenre(serieController.updateGenres(media, null, typeReference, tvSeriesApi));

            assertNotNull(media.getGenre());
        }

        @Test
        void updateSeason_shouldReturnMediaWithSeason() {
            var media = mediaTest;

            media.setSeason(null);

            media.setSeason(serieController.updateSeason(media, null, typeReference, tvSeriesApi));

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
            if (mediaTest.getAlternativeTitles().isEmpty()) {
                return;
            }

            var response = tester.documentName("media")
                    .variable("name", mediaTest.getAlternativeTitles().getFirst())
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }

        @Test
        void querySerieBy_whenIdIsNull_shouldReturnEmpty() {
            var response = tester.documentName("media")
                    .variable("id", null)
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeLessThan(1);
        }

        @Test
        void querySerieByName_wjenNameIsnull_shouldReturnEmpty() {
            var response = tester.documentName("media")
                    .variable("name", "")
                    .execute()
                    .path("tvserie")
                    .entityList(MediaModel.class);

            response.hasSizeLessThan(1);
        }
    }
}
