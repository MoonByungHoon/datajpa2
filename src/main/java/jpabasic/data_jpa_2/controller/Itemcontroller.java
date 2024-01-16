package jpabasic.data_jpa_2.controller;

import jpabasic.data_jpa_2.domain.Book;
import jpabasic.data_jpa_2.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class Itemcontroller {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("itemForm", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(BookForm form) {
    itemService.saveItem(form.changeToEntity(form));
    return "redirect:/";
  }

  @GetMapping("/items")
  public String list(Model model) {
    model.addAttribute("items", itemService.findItems());
    return "items/itemList";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
    Book item = (Book) itemService.findOne(itemId);

    BookForm form = new BookForm(item.getId(), item.getName(), item.getPrice(),
            item.getStockQuantity(), item.getAuthor(), item.getIsbn());

    model.addAttribute("itemForm", form);
    return "items/updateItemForm";
  }

  @PostMapping("/items/{itemId}/edit")
  public String updateItemForm( @PathVariable Long itemId, @ModelAttribute("itemForm") BookForm form) {

    itemService.updateItem(itemId, form.getBook().getName(), form.getBook().getPrice(), form.getBook().getStockQuantity());
    return "redirect:/items";
  }
}
