package hello.exception.resolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.exception.customException.UserException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserExceptionResolver implements HandlerExceptionResolver {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) {

		try {
			if (ex instanceof UserException) {
				log.info("UserException resolver TOOOOOO 400");
				String acceptHeader = request.getHeader("accept");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				// Accept : application/json 일 때
				if ("application/json".equals(acceptHeader)) {
					Map<String, Object> errorResult = new HashMap<>();
					errorResult.put("ex", ex.getClass());
					errorResult.put("message", ex.getMessage());
					String result =
						objectMapper.writeValueAsString(errorResult);

					//응답 value 설정
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(result);
					return new ModelAndView();
				} else {
					return new ModelAndView("error/500");
				}
			}
		} catch (IOException e) {
			log.error("resolver EX", e);
		}
		return null;
	}
}
