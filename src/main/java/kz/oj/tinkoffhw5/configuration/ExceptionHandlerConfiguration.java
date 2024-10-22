package kz.oj.tinkoffhw5.configuration;

import kz.oj.tinkoffhw5.exception.EntityNotFoundException;
import kz.oj.tinkoffhw5.exception.RelatedEntityNotFoundException;
import kz.oj.tinkoffhw5.web.rest.v1.dto.ExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException e) {

        ExceptionDto body = new ExceptionDto("ENTITY_NOT_FOUND", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(RelatedEntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleRelatedEntityNotFoundException(RelatedEntityNotFoundException e) {

        ExceptionDto body = new ExceptionDto("RELATED_ENTITY_NOT_FOUND", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }


}
