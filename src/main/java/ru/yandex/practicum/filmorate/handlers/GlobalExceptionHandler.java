package ru.yandex.practicum.filmorate.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserNotFound(UserNotFoundException exception) {
        return ApiError.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description(exception.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleFilmNotFound(FilmNotFoundException exception) {
        return ApiError.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description(exception.getMessage())
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleReleaseData(ReleaseDataException exception) {
        return ApiError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .description(exception.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUserAddFriends(UserAddFriendsException exception) {
        return ApiError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .description(exception.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException exception) {
        return ApiError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .description(exception.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUncaught(Exception exception) {
        return ApiError.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description(exception.getMessage())
                .build();
    }
}
