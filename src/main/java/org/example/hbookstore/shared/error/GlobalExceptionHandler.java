package org.example.hbookstore.shared.error;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage() != null ? exception.getMessage() : "Entity not found",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(
            InvalidRequestException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage() != null ? exception.getMessage() : "Invalid request",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage() != null ? exception.getMessage() : "Unauthorized",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage() != null ? exception.getMessage() : "Authentication failed",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.FORBIDDEN,
                exception.getMessage() != null ? exception.getMessage() : "Access denied",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
            UsernameNotFoundException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage() != null ? exception.getMessage() : "User not found",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(
            JWTVerificationException exception,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage() != null ? exception.getMessage() : "JWT verification failed",
                request.getRequestURI()
        );
    }

    ResponseEntity<ErrorResponse> createErrorResponse(
            HttpStatus status,
            String message,
            String path
    ) {
        ErrorResponse response = new ErrorResponse(
                status,
                message,
                path
        );
        return ResponseEntity.status(status).body(response);
    }
}
