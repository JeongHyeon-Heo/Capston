package shop.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.demo.domain.Item;
import shop.demo.domain.Category;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    //새로운 아이템 저장하거나 이미 존재하는 아이템을 업데이트
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item); //새로운 아이템을 추가시 persist 사용
        } else {
            em.merge(item); //이미 존재하는 아이템 업데이트시 merge 사용
        }
    }

    //주어진 ID에 해당하는 아이템을 검색
    public Item findItemById(Long id) {
        return em.find(Item.class, id);
    }

    //모든 아이템 목록을 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    //주어진 카테고리에 해당하는 아이템 목록을 조회
    public List<Item> findByCategory(Category category) {
        return em.createQuery("select i from Item i where i.category = :category", Item.class)
                .setParameter("category", category)
                .getResultList();
    }

    //주어진 키워드를 포함하는 상품을 검색
    public List<Item> findByKeyword(String keyword) {
        return em.createQuery("select i from Item i where i.name LIKE :keyword", Item.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    //주어진 ID에 해당하는 아이템을 삭제
    public void deleteById(Long id) {
        Item item = findItemById(id);
        if (item != null) {
            em.remove(item);
        }
    }
}