package entity.web;

public class HttpResponseObject {

    int responseCode;
    String message;

    public HttpResponseObject( int responseCode, String message ) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setResponseCode( int responseCode ) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "HttpResponseObject{" + "responseCode=" + responseCode + ", message="
                + message + '}';
    }

}
