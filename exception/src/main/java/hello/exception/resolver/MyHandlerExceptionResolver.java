package hello.exception.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) {
		try{
			if (ex instanceof IllegalArgumentException) {
				log.info("IllegalArgumentException resolver TOOOO 400");
				//상태 코드 400으로 지정
				response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				//실행 흐름이 WAS까지 정상적으로 동작하도록 함 -> WAS에서는 전달받은 상태코드 400을 통하여 /error 호출
				return new ModelAndView();
			}
		} catch (IOException e) {
			log.error("resolver EX ", e);
		}
		//처리할 수 없는 예외, 발생한 Exception WAS까지 던짐 -> 500 internal error 응답
		return null;
	}
}
