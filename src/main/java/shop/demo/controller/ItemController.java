package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.demo.domain.Item;
import shop.demo.domain.Category;
import shop.demo.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    //상품 등록
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        itemService.saveItem(item);
        return ResponseEntity.ok("상품이 추가되었습니다.");
    }

    //상품 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> itemList = itemService.itemList();
        return ResponseEntity.ok(itemList);
    }

    //특정 상품 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable Long itemId) {
        Item item = itemService.findOne(itemId);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build(); //상품이 존재하지 않는 경우 404 상태 코드를 반환
        }
    }

    //카테고리별 상품 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable Category category) {
        List<Item> items = itemService.getItemsByCategory(category);
        return ResponseEntity.ok(items);
    }

    //상품 업데이트
    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable Long itemId, @RequestBody Item newItem) {
        itemService.updateItem(itemId, newItem);
        return ResponseEntity.ok("상품이 업데이트되었습니다.");
    }

    //상품 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        if (itemService.deleteItem(itemId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); //상품이 존재하지 않는 경우 404 상태 코드를 반환
        }
    }

}