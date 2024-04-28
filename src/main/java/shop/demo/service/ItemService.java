package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.Item;
import shop.demo.domain.Category;
import shop.demo.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    //상품등록
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //전체 상품목록 조회
    @Transactional
    public List<Item> itemList(){
        return itemRepository.findAll();
    }

    //특정 상품 조회
    public Item findOne(Long itemId){
        return itemRepository.findItemById(itemId);
    }

    //카테고리별 상품 조회
    public List<Item> getItemsByCategory(Category category) {
        return itemRepository.findByCategory(category);
    }

    //상품 업데이트
    @Transactional
    public void updateItem(Long itemId, Item newItem) {
        Item existingItem = findOne(itemId);
        if (existingItem != null) {
            existingItem.setName(newItem.getName());
            existingItem.setPrice(newItem.getPrice());
            existingItem.setStockQuantity(newItem.getStockQuantity());
            itemRepository.save(existingItem);
        }
    }

    //상품 삭제
    @Transactional
    public boolean deleteItem(Long itemId) {
        Item item = findOne(itemId);
        if (item != null) {
            itemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }

}