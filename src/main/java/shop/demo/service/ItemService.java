package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.Item;
import shop.demo.domain.Category;
import shop.demo.domain.ItemStatus;
import shop.demo.dto.ItemDTO;
import shop.demo.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

//상품 조회, 업데이트, 삭제와 관련된 비즈니스 로직 구현
//ItemDTO와 Item엔티티 간의 변환을 담당하는 메서드를 제공
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    //상품 등록
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
        //조회된 상품 목록 중에서 재고가 있는 상품만을 필터링하여 새로운 리스트에 저장
        List<Item> availableItems = items.stream()
                .filter(item -> item.getStockQuantity() > 0)
                .collect(Collectors.toList());
        return convertToDTOList(availableItems);
    }

    //상품 정보 업데이트
    @Transactional
    public void updateItem(Long itemId, ItemDTO newItemDTO) {
        Item existingItem = itemRepository.findItemById(itemId);

        if (existingItem != null) {
            int newStockQuantity = newItemDTO.getStockQuantity();
            if (newStockQuantity == 0) {
                existingItem.setStockQuantity(0);
                existingItem.setItemStatus(ItemStatus.SOLD_OUT);
            } else {
                existingItem.setStockQuantity(newStockQuantity);
                existingItem.setItemStatus(ItemStatus.AVAILABLE);
            }
            existingItem.setImagePath(newItemDTO.getImagePath()); //이미지 경로 업데이트
        } else {
            throw new IllegalArgumentException("상품 업데이트에 실패했습니다.");
        }
    }

    //상품 검색
    public List<ItemDTO> searchItems(String keyword) {
        // 검색 키워드를 포함하는 상품 목록 조회
        List<Item> items = itemRepository.findByKeyword(keyword);
        return convertToDTOList(items);
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

    //ItemDTO를 Item으로 변환하는 메서드
    private Item convertToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setStockQuantity(itemDTO.getStockQuantity());
        item.setCategory(itemDTO.getCategory());
        item.setImagePath(itemDTO.getImagePath()); //이미지 경로 설정
        return item;
    }

    //Item을 ItemDTO로 변환하는 메서드
    private ItemDTO convertToDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setStockQuantity(item.getStockQuantity());
        dto.setCategory(item.getCategory());
        dto.setImagePath(item.getImagePath()); // 이미지 경로 설정
        return dto;
    }

    //Item 리스트를 ItemDTO 리스트로 변환하는 메서드
    private List<ItemDTO> convertToDTOList(List<Item> items) {
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}