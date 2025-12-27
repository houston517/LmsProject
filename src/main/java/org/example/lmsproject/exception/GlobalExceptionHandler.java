package org.example.lmsproject.exception;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler({CourseNotFoundException.class, GroupNotFoundException.class,
            ScheduleNotFoundException.class,StudentNotFountException.class,TeacherNotFoundException.class})
    public ResponseEntity handleException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }
}
