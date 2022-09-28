package hello.itemservice.domain.item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RegionRepository {

	private final Map<String, String> regions = new LinkedHashMap<>() {
		{
			put("SEOUL", "서울");
			put("BUSAN", "부산");
			put("JEJU", "제주");
		}
	};

	public Map<String, String> getAll() {
		return Collections.unmodifiableMap(regions);
	}

}
