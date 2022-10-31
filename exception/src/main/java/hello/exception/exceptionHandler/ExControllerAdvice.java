package hello.exception.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hello.exception.api.ApiExceptionV2Controller;
import hello.exception.customException.UserException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(assignableTypes = ApiExceptionV2Controller.class)
public class ExControllerAdvice {

	//@ResponseStatus 이용하여 상태 코드 설정
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResult illegalExHandle(IllegalArgumentException e) {
		log.error("[exceptionHandle] EX ", e);
		return new ErrorResult("BAD REQUEST", e.getMessage());
	}

	//@ExceptionHandler의 예외 생략 가능
	@ExceptionHandler
	public ResponseEntity<ErrorResult> userExHandle(UserException e) {
		log.error("[exceptionHandle] EX ", e);
		ErrorResult errorResult = new ErrorResult("USER - EX ", e.getMessage());
		//ResponseEntity 이용한 상태 코드 설정
		return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	//IllegalArgument, UserException 제외한 exception 처리
	public ErrorResult exHandle(Exception e) {
		log.error("[exceptionHandle] EX ", e);
		return new ErrorResult("EX", "내부 오류");
	}
}
