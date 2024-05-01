package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.Item;
import shop.demo.domain.Category;
import shop.demo.dto.ItemDTO;
import shop.demo.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    //상품등록
    @Transactional
    public void saveItem(ItemDTO itemDTO) {
        Item item = convertToItem(itemDTO);
        itemRepository.save(item);
    }

    //전체 상품목록 조회
    @Transactional
    public List<ItemDTO> itemList() {
        List<Item> items = itemRepository.findAll();
        return convertToDTOList(items);
    }

    //특정 상품 조회
    public ItemDTO findOne(Long itemId) {
        Item item = itemRepository.findItemById(itemId);
        return item != null ? convertToDTO(item) : null;
    }

    //카테고리별 상품 조회
    public List<ItemDTO> getItemsByCategory(Category category) {
        List<Item> items = itemRepository.findByCategory(category);
        return convertToDTOList(items);
    }

    //상품 업데이트
    @Transactional
    public void updateItem(Long itemId, ItemDTO newItemDTO) {
        Item existingItem = itemRepository.findItemById(itemId);
        if (existingItem != null) {
            existingItem.setName(newItemDTO.getName());
            existingItem.setPrice(newItemDTO.getPrice());
            existingItem.setStockQuantity(newItemDTO.getStockQuantity());
            existingItem.setCategory(newItemDTO.getCategory());
        }
    }

    //상품 삭제
    @Transactional
    public boolean deleteItem(Long itemId) {
        Item item = itemRepository.findItemById(itemId);
        if (item != null) {
            itemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }

    // ItemDTO를 Item으로 변환하는 메서드
    private Item convertToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setStockQuantity(itemDTO.getStockQuantity());
        item.setCategory(itemDTO.getCategory());
        return item;
    }

    // Item을 ItemDTO로 변환하는 메서드
    private ItemDTO convertToDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setStockQuantity(item.getStockQuantity());
        dto.setCategory(item.getCategory());
        return dto;
    }

    // Item 리스트를 ItemDTO 리스트로 변환하는 메서드
    private List<ItemDTO> convertToDTOList(List<Item> items) {
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}