package hello.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hello.exception.customException.UserException;
import hello.exception.exceptionHandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

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

	@GetMapping("/api2/members/{id}")
	public ApiExceptionController.MemberDTO getMember(@PathVariable("id") String id) {

		if (id.equals("ex")) {
			throw new RuntimeException("잘못된 사용자");
		}

		if (id.equals("bad")) {
			throw new IllegalArgumentException("잘못된 입력 값");
		}

		if (id.equals("user-ex")) {
			throw new UserException("사용자 오류");
		}

		return new ApiExceptionController.MemberDTO(id, "helloooo " + id);
	}

	@Data
	@AllArgsConstructor
	static class MemberDTO {
		private String memberId;
		private String name;
	}
}
