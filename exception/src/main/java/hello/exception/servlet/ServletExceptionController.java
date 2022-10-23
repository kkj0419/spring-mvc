package hello.exception.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ServletExceptionController {

	//WAS까지 RuntimeException 전달 -> WebServerCustomizer에서 catch
	@GetMapping("/error-ex")
	public void errorException() {
		throw new RuntimeException("Runtime Error!!");
	}

	@GetMapping("/error-404")
	public void error404(HttpServletResponse response) throws IOException {
		response.sendError(404, "404 오류!");
	}

	@GetMapping("/error-500")
	public void error500(HttpServletResponse response) throws IOException {
		response.sendError(500);
	}
}

