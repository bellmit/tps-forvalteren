package no.nav.tps.vedlikehold.provider.rs.api.v1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

/**
 * @author Øyvind Grimnes, Visma Consulting AS
 */

@ControllerAdvice
public class HttpExceptionController {

    @ExceptionHandler(HttpUnauthorisedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    ExceptionInformation unauthorisedAccess(HttpException exception) {
        return informationForException(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpInternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionInformation internalServerError(HttpException exception) {
        return informationForException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpBadRequestException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ExceptionInformation badRequest(HttpException exception) {
        return informationForException(exception, HttpStatus.BAD_REQUEST);
    }

    private ExceptionInformation informationForException(HttpException exception, HttpStatus status) {
        return ExceptionInformation.create()
                .setError( status.getReasonPhrase() )
                .setStatus( status.value() )
                .setMessage( exception.getMessage() )
                .setPath( exception.getPath() )
                .setTimestamp( new Date().getTime() );
    }
}
