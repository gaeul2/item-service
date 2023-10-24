package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    /** 멀티스레드 환경에서 store에 동시에 접근하게 되면 해시맵사용하면 X
     * itemRepository가 싱글톤으로 생성되고, store도 static이므로
     * 동시에 여러스레드가 접근함 HashMap -> ConcurrentHashMap<>()을 사용하는것을 권장
     *
     * sequence도 long으로 사용하면 동시에 접근했을시 값이 꼬일 수 있음.
     * 그럴때는 atomic long? 등등 다른걸 사용해야 한다고 함.
     */
    private static final Map<Long, Item> store = new HashMap<>(); //실제로는 hashMap안씀. static 사용했다는것에 주의
    private static long sequence = 0L; //static

    //저장
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    //아이디로 찾기
    public Item findById(Long id){
        return store.get(id);
    }

    //전체조회
    public List<Item> findAll(){
        return new ArrayList<>(store.values()); //개간단..
    }

    //수정
    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    //초기화 -> test에서 사용하려고 만듬
    public void clearStore(){
        store.clear();
    }
}
