package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import shop.demo.domain.Item;
import shop.demo.domain.Category;
import shop.demo.domain.ItemStatus;
import shop.demo.dto.ItemDTO;
import shop.demo.repository.ItemRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


//상품 조회, 업데이트, 삭제와 관련된 비즈니스 로직 구현
//ItemDTO와 Item엔티티 간의 변환을 담당하는 메서드를 제공
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
    public List<ItemDTO> getItemsByCategory (Category category) {
        List<Item> items = itemRepository.findByCategory(category);
        //조회된 상품 목록 중에서 재고가 있는 상품만을 필터링하여 새로운 리스트에 저장
        List<Item> availableItems = items.stream()
                .filter(item -> item.getStockQuantity() > 0)
                .collect(Collectors.toList());
        return convertToDTOList(availableItems);
    }

    //상품 정보 업데이트
    /*@Transactional
    public void updateItem(Long itemId, ItemDTO newItemDTO) {
        Item existingItem = itemRepository.findItemById(itemId);

        if (existingItem != null && existingItem.getStockQuantity() != newItemDTO.getStockQuantity()) {
            existingItem.setStockQuantity(newItemDTO.getStockQuantity());
        } else {
            throw new IllegalArgumentException("상품 업데이트에 실패했습니다. 수량만 변경 가능합니다.");
        }
    }*/

    //상품 정보 업데이트
    @Transactional
    public void updateItem(Long itemId, ItemDTO newItemDTO) {
        Item existingItem = itemRepository.findItemById(itemId);

        if (existingItem != null) {
            int newStockQuantity = newItemDTO.getStockQuantity();

            //재고량이 0인 경우 품절 상태로 설정
            if (newStockQuantity == 0) {
                existingItem.setStockQuantity(0);
                existingItem.setItemStatus(ItemStatus.SOLD_OUT);
            } else {
                //재고량이 0보다 큰 경우 상품 상태를 재고 있음으로 설정
                existingItem.setStockQuantity(newStockQuantity);
                existingItem.setItemStatus(ItemStatus.AVAILABLE);
            }
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

    //상품 이미지 관리
    public String uploadImage(MultipartFile file) throws IOException {
        //업로드된 파일 이름에서 공백 제거
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //파일명에 UUID 추가하여 중복 방지
        fileName = UUID.randomUUID() + "_" + fileName;

        String uploadDir = "src/main/resources/static/images";

        //업로드될 디렉토리가 없는 경우 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //파일 저장
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        try (OutputStream outputStream = new FileOutputStream(filePath.toString())) {
            outputStream.write(file.getBytes());
        }

        //이미지 URL 반환
        return "/images/" + fileName;
    }

    //ItemDTO를 Item으로 변환하는 메서드
    private Item convertToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setStockQuantity(itemDTO.getStockQuantity());
        item.setCategory(itemDTO.getCategory());
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
        return dto;
    }

    //Item 리스트를 ItemDTO 리스트로 변환하는 메서드
    private List<ItemDTO> convertToDTOList(List<Item> items) {
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}