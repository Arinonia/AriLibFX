package fr.arinonia.arilibfx.auth.premium.responses;

import fr.arinonia.arilibfx.auth.premium.Profile;

/**
 * Created by Arinonia on 10/04/2020 inside the package - fr.arinonia.arilibfx.auth.premium.responses
 */
public class LoginResponse {

    private String accessToken;
    private String clientToke;
    private Profile selectedProfile;

    public LoginResponse(String accessToken, String clientToke, Profile selectedProfile) {
        this.accessToken = accessToken;
        this.clientToke = clientToke;
        this.selectedProfile = selectedProfile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientToke() {
        return clientToke;
    }

    public Profile getSelectedProfile() {
        return selectedProfile;
    }
}
