package hello.itemservice.message;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@SpringBootTest
public class MessageSourceTest {

	@Autowired
	MessageSource messageSource;

	@Test
	void initMessage(){

		Locale.setDefault(Locale.KOREA);
		String result = messageSource.getMessage("init", null, null);

		Assertions.assertThat(result).isEqualTo("안녕");
	}

	@Test
	void notFoundMessageCode() {
		assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
			.isInstanceOf(NoSuchMessageException.class);

	}

	@Test
	void argumentInitMessage(){
		String message = messageSource.getMessage("init.name", new Object[] {"Spring"}, null);
		assertThat(message).isEqualTo("안녕 Spring");
	}

	@Test
	void langMessageTest(){
		assertThat(messageSource.getMessage("init", null, Locale.KOREA)).isEqualTo("안녕");
		assertThat(messageSource.getMessage("init", null, Locale.ENGLISH)).isEqualTo("hello팅");

	}
}
