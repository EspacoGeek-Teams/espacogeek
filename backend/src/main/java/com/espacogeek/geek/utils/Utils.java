package com.espacogeek.geek.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;

public abstract class Utils {

    public static Integer getUserID(Authentication authentication) {
        return Integer.valueOf(
                authentication.getAuthorities().stream().filter(
                        (authority) -> authority.getAuthority()
                                .startsWith("ID_"))
                        .toList()
                        .getFirst()
                        .getAuthority()
                        .replace("ID_", ""));
    }

    /**
     * Validate the password.
     * @return <code>true</code> if the given password flow all rules and <code>false</code> if password doesn't flow any rule.
     */
    public static boolean isValidPassword(String password) {
        final String REG_EXPN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=])(?=\\S+$).{8,70}$";

        var pattern = Pattern.compile(REG_EXPN_PASSWORD, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private static Boolean updateMediaWhenLastTimeUpdateMoreThanOneDay(MediaModel media) {
        LocalDate mediaUpdateAt = media.getUpdateAt() == null ? null : LocalDate.ofInstant(media.getUpdateAt().toInstant(), ZoneId.systemDefault());

        if (mediaUpdateAt == null || ChronoUnit.DAYS.between(mediaUpdateAt, LocalDate.now()) > 1) {
            return true;
        }

        return false;
    }

    public static List<MediaModel> updateGenericMedia(List<MediaModel> medias, MediaDataController mediaDataController, TypeReferenceModel typeReference, MediaApi mediaApi) {
        List<MediaModel> updatedMedias = new ArrayList<>();

        for (MediaModel media : medias) {
            updatedMedias.add(updateMediaWhenLastTimeUpdateMoreThanOneDay(media)
                    ? mediaDataController.updateAllInformation(media, null, typeReference, mediaApi)
                    : media);
        }

        return medias;
    }

    public static List<MediaModel> updateMedia(List<MediaModel> medias, MediaDataController mediaDataController) {
        List<MediaModel> updatedMedias = new ArrayList<>();

        for (MediaModel media : medias) {
            updatedMedias.add(updateMediaWhenLastTimeUpdateMoreThanOneDay(media)
                    ? mediaDataController.updateAllInformation(media, null)
                    : media);
        }

        return medias;
    }
}
