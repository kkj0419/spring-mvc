package hello.itemservice.web.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

	private final ItemRepository itemRepository;
	private final ItemValidator validator;

	//메서드 호출 시마다 자동으로 validator 적용하여 validate
	@InitBinder
	public void init(WebDataBinder dataBinder) {
		dataBinder.addValidators(validator);
	}

	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "validation/v3/items";
	}

	@GetMapping("/{itemId}")
	public String item(@PathVariable long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v3/item";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "validation/v3/addForm";
	}

	// @PostMapping("/add")
	public String addItemV1(@ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {

		//validate
		if (!StringUtils.hasText(item.getItemName())) {
			result.addError(new FieldError("item", "itemName", "상품 이름을 기입해야 합니다."));

		}
		if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
			result.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용됩니다."));
		}
		if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() <= 0) {
			result.addError(new FieldError("item", "quantity", "수량은 최대 9,999까지 허용됩니다."));
		}

		//복합 rule(global error)
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice < 10000) {
				result.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
			}
		}

		//자동으로 model add
		if (result.hasErrors()) {
			return "validation/v3/addForm";
		}

		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v3/items/{itemId}";
	}

	// @PostMapping("/add")
	public String addItemV2(@ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {

		//validate
		if (!StringUtils.hasText(item.getItemName())) {
			result.addError(
				new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름을 기입해야 합니다."));
		}
		if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
			result.addError(
				new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용됩니다."));
		}
		if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() <= 0) {
			result.addError(
				new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999까지 허용됩니다."));
		}

		//복합 rule(global error)
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice < 10000) {
				result.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
			}
		}

		//자동으로 model add
		if (result.hasErrors()) {
			return "validation/v3/addForm";
		}

		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v3/items/{itemId}";
	}

	// @PostMapping("/add")
	public String addItemV3(@ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {

		//validate
		if (!StringUtils.hasText(item.getItemName())) {
			result.addError(
				new FieldError("item", "itemName", item.getItemName(), false, new String[] {"required.item.itemName"},
					null, "상품 이름을 기입해야 합니다."));
		}
		if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
			result.addError(
				new FieldError("item", "price", item.getPrice(), false, new String[] {"range.item.price"},
					new Object[] {1000, 1000000}, "가격은 1,000 ~ 1,000,000 까지 허용됩니다."));
		}
		if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() <= 0) {
			result.addError(
				new FieldError("item", "quantity", item.getQuantity(), false, new String[] {"max.item.quantity"},
					new Object[] {9999}, "수량은 최대 9,999까지 허용됩니다."));
		}

		//복합 rule(global error)
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice < 10000) {
				result.addError(
					new ObjectError("item", new String[] {"totalPriceMin"}, new Object[] {10000, resultPrice},
						"가격 * 수량의 합은 10,000원 이상이어야 합니다."));
			}
		}

		//자동으로 model add
		if (result.hasErrors()) {
			return "validation/v3/addForm";
		}

		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v3/items/{itemId}";
	}

	// @PostMapping("/add")
	public String addItemV4(@ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {

		//validate
		if (!StringUtils.hasText(item.getItemName())) {
			//MessageCodesResolver 를 이용하여 메시지 코드 간소화
			result.rejectValue("itemName", "required", null, null);
		}
		if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
			result.rejectValue("price", "range", new Object[] {1000, 1000000}, null);
		}
		if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() <= 0) {
			result.rejectValue("quantity", "max", new Object[] {9999}, null);
		}

		//복합 rule(global error)
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice < 10000) {
				result.reject("totalPriceMin", new Object[] {10000, resultPrice}, null);
			}
		}

		//자동으로 model add
		if (result.hasErrors()) {
			return "validation/v3/addForm";
		}

		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v3/items/{itemId}";
	}

	// @PostMapping("/add")
	public String addItemV5(@ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {

		//validate
		validator.validate(item, result);

		//자동으로 model add
		if (result.hasErrors()) {
			return "validation/v3/addForm";
		}

		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v3/items/{itemId}";
	}

	//자동 validation 위한 @Validated 어노테이션
	@PostMapping("/add")
	public String addItemV6(@ModelAttribute @Validated Item item, BindingResult result, RedirectAttributes redirectAttributes) {

		//자동으로 model add
		if (result.hasErrors()) {
			return "validation/v3/addForm";
		}

		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v3/items/{itemId}";
	}


	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v3/editForm";
	}

	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
		itemRepository.update(itemId, item);
		return "redirect:/validation/v3/items/{itemId}";
	}

}

