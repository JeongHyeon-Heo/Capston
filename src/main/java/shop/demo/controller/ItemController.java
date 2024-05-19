package shop.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.demo.dto.ItemDTO;
import shop.demo.domain.Category;
import shop.demo.service.ItemService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품 등록
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody ItemDTO itemDTO) {
        itemService.saveItem(itemDTO);
        return ResponseEntity.ok("상품이 추가되었습니다.");
    }

    // 상품 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> itemList = itemService.itemList();
        return ResponseEntity.ok(itemList);
    }

    // 특정 상품 조회
    @GetMapping("/get/{itemId}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable Long itemId) {
        ItemDTO itemDTO = itemService.findOne(itemId);
        if (itemDTO != null) {
            return ResponseEntity.ok(itemDTO);
        } else {
            return ResponseEntity.notFound().build(); // 상품이 존재하지 않는 경우 404 상태 코드를 반환
        }
    }

    // 카테고리별 상품 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemDTO>> getItemsByCategory(@PathVariable Category category) {
        List<ItemDTO> items = itemService.getItemsByCategory(category);
        return ResponseEntity.ok(items);
    }

    // 상품 업데이트
    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable Long itemId, @RequestBody ItemDTO newItemDTO) {
        itemService.updateItem(itemId, newItemDTO);
        return ResponseEntity.ok("상품이 업데이트되었습니다.");
    }

    // 상품 검색
    @GetMapping("/search")
    public ResponseEntity<List<ItemDTO>> searchItems(@RequestParam String keyword) {
        // 검색어 URL 디코딩
        String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        // 검색어를 포함하는 상품 목록 조회
        List<ItemDTO> searchResults = itemService.searchItems(decodedKeyword);
        return ResponseEntity.ok(searchResults);
    }

    // 상품 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        if (itemService.deleteItem(itemId)) {
            return ResponseEntity.ok("상품이 삭제되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 이미지 경로 설정
    @PostMapping("/setImagePath/{itemId}")
    public ResponseEntity<String> setImagePath(@PathVariable Long itemId, @RequestParam("imagePath") String imagePath) {
        itemService.setImagePath(itemId, imagePath);
        return ResponseEntity.ok("이미지 경로가 설정되었습니다.");
    }
}

