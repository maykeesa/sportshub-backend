package br.com.sporthub.config.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val mapDto = HashMap<String, String?>()
        val fieldErrors = ex.bindingResult.fieldErrors

        for (fieldError in fieldErrors) {
            mapDto[fieldError.field] = fieldError.defaultMessage
        }

        return ResponseEntity.badRequest().body(mapDto)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgument(ex: IllegalArgumentException): ResponseEntity<Any> {
        val mapDto = HashMap<String, String>()
        mapDto["error"] = "O id passado não é um UUID."

        return ResponseEntity.badRequest().body(mapDto)
    }
}