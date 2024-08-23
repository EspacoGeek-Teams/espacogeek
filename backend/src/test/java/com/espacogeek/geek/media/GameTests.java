package com.espacogeek.geek.media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.espacogeek.geek.data.api.MediaApi;

@Nested
@SpringBootTest
@DisplayName("Game Tests")
public class GameTests {
    private final static String NAME = "Red Dead Redemption 2";

    @Autowired
    private MediaApi gamesAndVNsAPI;

    @Nested
    @DisplayName("Game Api")
    public class GameMethodTest {

        @Test
        void searchGame_shouldReturnMediaModelWithGameData() {
            var game = gamesAndVNsAPI.doSearch(NAME);
            System.out.println(game.get(0).getName());
        }
    }

}
