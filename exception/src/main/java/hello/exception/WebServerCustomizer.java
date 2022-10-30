package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// @Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	//Servlet Error Page 등록
	//지정된 path로 이동 -> controller에서 이를 잡아 처리
	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
		ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

		ErrorPage errorPageRunEx = new ErrorPage(RuntimeException.class, "/error-page/500");
		factory.addErrorPages(errorPage404, errorPage500, errorPageRunEx);
	}
}
