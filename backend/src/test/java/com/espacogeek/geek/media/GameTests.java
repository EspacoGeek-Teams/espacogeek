package com.espacogeek.geek.media;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("Game Tests")
public class GameTests {
    private final static String NAME = "Red Dead Redemption 2";

    @Autowired
    private MediaApi gamesAndVNsAPI;

    @Nested
    @DisplayName("Game Api")
    public class GameMethodApiTest {

        @Test
        void searchGame_shouldReturnMediaModelWithGameData() {
            var game = gamesAndVNsAPI.doSearch(NAME);
            assertTrue(() -> game.size() > 0);
        }
    }

    @Nested
    @DisplayName("Game Request")
    public class GameRequest extends RequestClient {

        @Test
        void findGame_whenNameIsValid_shouldReturnOneOrSeveralMedias() {
            var response = tester.documentName("findGame")
                .variable("name", NAME)
                .execute()
                .path("game")
                .entityList(MediaModel.class);

            response.hasSizeGreaterThan(0);
        }
    }

}
