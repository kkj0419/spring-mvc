package hello.exeption.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ServletExceptionController {

	@GetMapping("/error-ex")
	public void errorEx() {
		throw new RuntimeException("Runtime Exception!!");
	}
}
