package hello.itemservice.domain.item;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
//메시지 코드 : ScriptAssert.item 로 메시지를 표현할 수도 있음.
// @ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "총 합이 10000 이상이어야 합니다.")
public class Item {

	@NotNull(groups = UpdateValidation.class)
	private Long id;

	@NotBlank(groups = {SaveValidation.class, UpdateValidation.class})
	private String itemName;

	@NotNull(groups = {SaveValidation.class, UpdateValidation.class})
	@Range(min = 1000, max = 1000000, groups = {SaveValidation.class, UpdateValidation.class})
	private Integer price;

	@NotNull(groups = {SaveValidation.class, UpdateValidation.class})
	@Max(value = 9999, groups = SaveValidation.class)
	private Integer quantity;

	public Item() {
	}

	public Item(String itemName, Integer price, Integer quantity) {
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
