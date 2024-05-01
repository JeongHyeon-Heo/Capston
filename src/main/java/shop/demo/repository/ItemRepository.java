package shop.demo.repository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.demo.domain.Item;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository{
    private final EntityManager entityManager;

    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item); //새로운 아이템을 추가시 persist 사용
        } else {
            entityManager.merge(item); //이미 존재하는 아이템 업데이트시 merge 사용
        }
    }

    public Item findItemById(Long id){
        return entityManager.find(Item.class, id);
    }

    public List<Item> findAll(){
        return entityManager.createQuery("select i from Item i", Item.class).getResultList();
    }

    public void deleteById(Long id) {
        Item item = findItemById(id);
        if (item != null) {
            entityManager.remove(item);
        }
    }
}