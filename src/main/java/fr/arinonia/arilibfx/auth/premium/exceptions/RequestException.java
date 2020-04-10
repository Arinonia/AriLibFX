package fr.arinonia.arilibfx.auth.premium.exceptions;

import fr.arinonia.arilibfx.auth.premium.responses.ErrorResponse;

/**
 * Created by Arinonia on 10/04/2020 inside the package - fr.arinonia.arilibfx.auth.premium.responses
 */
public class RequestException extends Exception{

    private ErrorResponse error;
    public RequestException(ErrorResponse error) {
        this.error = error;
    }

    public ErrorResponse getError() {
        return error;
    }
    public String getErrorMessage(){
        return this.error.getErrorMessage();
    }
    public String getErrorCause(){
        return this.error.getCause();
    }
}
