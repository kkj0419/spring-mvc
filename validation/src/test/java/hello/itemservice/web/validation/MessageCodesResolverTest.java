package hello.itemservice.web.validation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

class MessageCodesResolverTest {

	//input(errorcode) -> message code list
	MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

	@Test
	@DisplayName("message resolver test(with Object)")
	void messageResolverWithObjectName() {

		String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
		assertThat(messageCodes).containsExactly("required.item", "required");
	}

	@Test
	@DisplayName("message resolver test(with fieldName)")
	void messageCodesResolverWithField() {

		String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
		for (String messageCode : messageCodes) {
			System.out.println("messageCode = " + messageCode);
		}
		assertThat(messageCodes).containsExactly(
			"required.item.itemName",
			"required.itemName",
			"required.java.lang.String",
			"required"
		);
	}

}