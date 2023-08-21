package com.example.nokbackend.presentation.api

import com.example.nokbackend.exception.LoginFailedException
import com.example.nokbackend.exception.UnidentifiedUserException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        httpHeaders: HttpHeaders,
        httpStatus: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message", ex)
        val messages = when (val exception = ex.cause) {
            is MissingKotlinParameterException -> "${exception.parameter.name.orEmpty()}: 널이어서는 안됩니다"
            is InvalidFormatException -> "${exception.path.last().fieldName.orEmpty()}: 올바른 형식이어야 합니다"
            else -> exception?.message.orEmpty()
        }
        return responseEntity {
            status = HttpStatus.OK
            body = apiResponse<EmptyBody> {
                status = HttpStatus.BAD_REQUEST.value()
                message = messages
                data = EmptyBody
            }
        }
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        httpStatus: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message", ex)
        return responseEntity {
            status = HttpStatus.OK
            body = apiResponse<EmptyBody> {
                status = HttpStatus.BAD_REQUEST.value()
                message = ex.messages()
                data = EmptyBody
            }
        }
    }

    private fun MethodArgumentNotValidException.messages(): String {
        return bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage.orEmpty()}" }
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleBadRequestException(exception: RuntimeException): ResponseEntity<ApiResponse<EmptyBody>> {
        logger.error("message", exception)
        return responseEntity {
            status = HttpStatus.OK
            body = apiResponse {
                status = HttpStatus.BAD_REQUEST.value()
                message = exception.message
                data = EmptyBody
            }
        }
    }

    @ExceptionHandler(LoginFailedException::class)
    fun handleUnauthorizedException(exception: LoginFailedException): ResponseEntity<ApiResponse<EmptyBody>> {
        logger.error("message", exception)
        return responseEntity {
            status = HttpStatus.OK
            body = apiResponse {
                status = exception.status.value()
                message = exception.message
                data = EmptyBody
            }
        }
    }

    @ExceptionHandler(UnidentifiedUserException::class)
    fun handleForbiddenException(exception: UnidentifiedUserException): ResponseEntity<ApiResponse<EmptyBody>> {
        logger.error("message", exception)
        return responseEntity {
            status = HttpStatus.OK
            body = apiResponse {
                status = exception.status.value()
                message = exception.message
                data = EmptyBody
            }
        }
    }

    @ExceptionHandler(RuntimeException::class, Exception::class)
    fun handleInternalServerErrorException(exception: RuntimeException): ResponseEntity<ApiResponse<EmptyBody>> {
        logger.error("message", exception)
        return responseEntity {
            status = HttpStatus.OK
            body = apiResponse {
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
                message = exception.message
                data = EmptyBody
            }
        }
    }
}