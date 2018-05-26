import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class ListTest {

	public static void main(String[] args) {
		//
		// List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		//
		// Map<Object, Object> map = new HashMap<Object, Object>();
		//
		// Map<Object, Object> map1 = new HashMap<Object, Object>();
		// Map<Object, Object> map2 = new HashMap<Object, Object>();
		// Map<Object, Object> map3 = new HashMap<Object, Object>();
		//
		// map.put("number", "2.57");
		// map1.put("number", "3");
		// map2.put("number", "2.99");
		// map3.put("number", "2.78");
		// list.add(map);
		// list.add(map1);
		// list.add(map2);
		// list.add(map3);
		//
		// Collections.sort(list, new Comparator<Map<Object, Object>>() {
		//
		// public int compare(Map<Object, Object> o1, Map<Object, Object> o2) {
		//
		// int map1value = (int)Double.parseDouble((String) o1.get("number"))*100 ;
		// int map2value = (int)Double.parseDouble((String) o2.get("number"))*100;
		// return map1value - map2value;
		// }
		// });
		// System.out.println(list);

		String jsonStr = "{\"size\":\"7.5\",\"width\":\"M (B)\"}";

		System.out.println("无序遍历结果：");
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		System.out.println("-------------------");
		System.out.println("有序遍历结果：");
		LinkedHashMap<String, String> jsonMap = JSON.parseObject(jsonStr, new TypeReference<LinkedHashMap<String, String>>() {
		});
		for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

}