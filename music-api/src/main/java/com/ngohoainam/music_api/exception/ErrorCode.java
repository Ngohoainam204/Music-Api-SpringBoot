package com.ngohoainam.music_api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    //Success
    SUCCESS("MSC-0000","Success",HttpStatus.OK),
    CREATED("MSC-0001","Created successfully",HttpStatus.CREATED),
    DELETE_SUCCESS("MSC-0002","Delete successfully",HttpStatus.OK),
    //Authentication
    UNAUTHORIZED("MSC-1000","Unauthorized",HttpStatus.UNAUTHORIZED),
    USER_NOT_EXIST("MSC-1001","User not exist",HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("MSC-1002","User not found",HttpStatus.UNAUTHORIZED),
    USER_EXISTED("MSC-1003","User already exist",HttpStatus.CONFLICT),
    INVALID_PASSWORD("MSC-1004","Invalid password",HttpStatus.UNAUTHORIZED),
    FORBIDDEN("MSC-1005","Forbidden",HttpStatus.FORBIDDEN),
    //
    SONG_NOT_FOUND("MSC-2000","Song not found",HttpStatus.NOT_FOUND),
    ALBUM_NOT_FOUND("MSC-2001","Album not found",HttpStatus.NOT_FOUND),
    PLAYLIST_NOT_FOUND("MSC-2002","Playlist not found",HttpStatus.NOT_FOUND),
    ARTIST_NOT_FOUND("MSC-2003","Artist not found",HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND("MSC-2004","File not found",HttpStatus.NOT_FOUND),

    ;
    ErrorCode(String code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }


    private String code;
    private  String message;
    private  HttpStatusCode statusCode;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
