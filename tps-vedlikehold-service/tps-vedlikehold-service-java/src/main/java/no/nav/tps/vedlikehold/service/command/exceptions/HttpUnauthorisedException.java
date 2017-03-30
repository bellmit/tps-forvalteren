package no.nav.tps.vedlikehold.service.command.exceptions;


public class HttpUnauthorisedException extends HttpException {

    public static final String messageKey = "rest.service.request.exception.Unauthorized";

    public HttpUnauthorisedException(String message, String path) {
        super(message, path);
    }

    public HttpUnauthorisedException(Exception exception, String path) {
        super(exception, path);
    }
}
