package hello.itemservice.web.validation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveValidation;
import hello.itemservice.domain.item.UpdateValidation;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

	private final ItemRepository itemRepository;

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

	/**
	 * @Validated(@Valid) 어노테이션을 통하여 LocalValidatorFactoryBean을 validator로 등록
	 * <- 개발자가 따로 등록한 validator가 존재하지 않을 때
	 */
	// @PostMapping("/add")
	public String addItem(@ModelAttribute @Validated Item item, BindingResult result,
		RedirectAttributes redirectAttributes) {

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

	@PostMapping("/add")
	public String addItemV2(@ModelAttribute @Validated(SaveValidation.class) Item item, BindingResult result,
		RedirectAttributes redirectAttributes) {

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

	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v3/editForm";
	}

	// @PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @Valid @ModelAttribute Item item, BindingResult result) {

		//복합 rule(global error)
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice < 10000) {
				result.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
			}
		}

		if (result.hasErrors()) {
			return "validation/v3/editForm";
		}

		itemRepository.update(itemId, item);
		return "redirect:/validation/v3/items/{itemId}";
	}

	@PostMapping("/{itemId}/edit")
	public String editV2(@PathVariable Long itemId, @Validated(UpdateValidation.class) @ModelAttribute Item item,
		BindingResult result) {

		//복합 rule(global error)
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice < 10000) {
				result.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
			}
		}

		if (result.hasErrors()) {
			return "validation/v3/editForm";
		}

		itemRepository.update(itemId, item);
		return "redirect:/validation/v3/items/{itemId}";
	}

}

