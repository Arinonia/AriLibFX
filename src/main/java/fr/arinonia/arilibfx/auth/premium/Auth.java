package fr.arinonia.arilibfx.auth.premium;

import fr.arinonia.arilibfx.auth.premium.exceptions.AuthenticationUnavailableException;
import fr.arinonia.arilibfx.auth.premium.exceptions.InvalidCredentialsException;
import fr.arinonia.arilibfx.auth.premium.exceptions.RequestException;
import fr.arinonia.arilibfx.auth.premium.exceptions.UserMigratedException;
import fr.arinonia.arilibfx.auth.premium.responses.AuthenticationResponse;
import fr.arinonia.arilibfx.auth.premium.responses.ErrorResponse;
import fr.arinonia.arilibfx.auth.premium.responses.RequestResponse;
import fr.arinonia.arilibfx.utils.JsonUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by Arinonia on 10/04/2020 inside the package - fr.arinonia.arilibfx.auth.premium
 */
public class Auth {
    private static Profile profile;
    private static String tokenAccess;

    public static AuthenticationResponse authenticate(String username, String password, String clientToken, Proxy proxy)throws RequestException, AuthenticationUnavailableException {
        RequestResponse result = sendJsonPostRequest(getRequestUrl("authenticate"), JsonUtils.credentialsToJson(username,password,clientToken), proxy);
        if(result.isSuccessful()){
            String accessToken = (String) result.getData().get("accessToken");
            String rClientToken = (String) result.getData().get("clientToken");
            Profile selectedProfile = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("selectedProfile")), Profile.class);
            Profile[] availableProfile = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("availableProfiles")), Profile[].class);
            profile = selectedProfile;
            tokenAccess = accessToken;
            return new AuthenticationResponse(accessToken, rClientToken, selectedProfile, availableProfile);
        }else{
            profile = null;
            tokenAccess = "";
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            if(result.getData().get("cause") != null && ((String) (result.getData().get("cause"))).equalsIgnoreCase("UserMigratedException")){
                throw new UserMigratedException(errorResponse);
            }else{
                throw new InvalidCredentialsException(errorResponse);
            }
        }
    }

    public static AuthenticationResponse authenticate(String username, String password)throws RequestException, AuthenticationUnavailableException{
        return authenticate(username,password,null,null);
    }
    private static URL getRequestUrl(String request){
        try{
            return new URL("https://authserver.mojang.com/" + request);
        }catch (Exception e){
            e.printStackTrace();
            //TODO LOGGER
            return null;
        }
    }
    
    public static LoginResponse refresh(String accessToken, String clientToken) throws AuthenticationUnavailableException, UserMigratedException, InvalidCredentialsException
    {
        return refresh(accessToken, clientToken, null);
    }

    public static LoginResponse refresh(String accessToken, String clientToken, Proxy proxy) throws AuthenticationUnavailableException, UserMigratedException, InvalidCredentialsException
    {
        final RequestResponse result = sendJsonPostRequest(getRequestUrl("refresh"), JsonUtils.tokenToJson(accessToken, clientToken), proxy);
        if (result.isSuccessful())
        {
            final String rAccessToken = (String) result.getData().get("accessToken");
            final String rClientToken = (String) result.getData().get("clientToken");
            final Profile selectedProfile = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("selectedProfile")), Profile.class);

            profile     = selectedProfile;
            tokenAccess = rAccessToken;

            return new LoginResponse(rAccessToken, rClientToken, selectedProfile);
        }
        profile     = null;
        tokenAccess = "";
        final ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
        if (result.getData().get("cause") != null && ((String) (result.getData().get("cause"))).equalsIgnoreCase("UserMigratedException"))
            throw new UserMigratedException(errorResponse);
        else
            throw new InvalidCredentialsException(errorResponse);
    }
    
    public static void invalidate(String accessToken, String clientToken) throws AuthenticationUnavailableException
    {
        invalidate(accessToken, clientToken, null);
    }

    public static void invalidate(String accessToken, String clientToken, Proxy proxy) throws AuthenticationUnavailableException
    {
        sendJsonPostRequest(getRequestUrl("invalidate"), JsonUtils.tokenToJson(accessToken, clientToken), proxy);
    }

    private static RequestResponse sendJsonPostRequest(URL requestUrl, String payload, Proxy proxy)throws AuthenticationUnavailableException{
        HttpsURLConnection connection = null;
        try {
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
            connection = (HttpsURLConnection) (proxy != null ? requestUrl.openConnection(proxy) : requestUrl.openConnection());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Content-Length",String.valueOf(payloadBytes.length));
            connection.setUseCaches(false);
            OutputStream out = connection.getOutputStream();
            out.write(payloadBytes, 0, payloadBytes.length);
            out.close();
            int responseCode = connection.getResponseCode();
            BufferedReader reader = null;
            String response;
            switch (responseCode){
                case 200:
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    response = reader.readLine();
                    break;
                case 204:
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
                    response = reader.readLine();
                    break;
                default:
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
                    response = reader.readLine();
                    break;
            }
            if(reader != null){
                reader.close();
            }
            Map<String, Object> map = JsonUtils.gson.fromJson(response, JsonUtils.stringObjectMap);
            return new RequestResponse(responseCode,map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationUnavailableException(null);
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    public static Profile getProfile() {return profile;}
    public static String getTokenAccess() {return tokenAccess;}
}
