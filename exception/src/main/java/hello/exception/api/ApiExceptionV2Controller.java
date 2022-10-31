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
