package com.bilibili.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//存储删除数据
@RestController
public class RestfulApi {
    private final Map<Integer, Map<String, Object>> dataMap;

    public RestfulApi() {
        dataMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", i);
            data.put("name", "name" + i);
            dataMap.put(i, data);
        }
    }

    @GetMapping("/objects/{id}")//标识get请求和接口路径（name of url:noun,plural
    public Map<String, Object> getData(@PathVariable Integer id) {
        return dataMap.get(id);

    }

    @DeleteMapping("/objects/{id}")
    public String deleteData(@PathVariable Integer id) {
        dataMap.remove(id);
        return "Delete success";
    }

    @PostMapping("/objects/")
    //RequestBody进行自动封装，把数据以json形式传进来
    //POST没有更新功能
    public String postData(@RequestBody Map<String, Object> data) {
        Integer[] idArray = dataMap.keySet().toArray(new Integer[0]);
        Arrays.sort(idArray);
        int nextID = idArray[idArray.length - 1] + 1;
        dataMap.put(nextID, data);
        return "post success!";

    }

    @PutMapping("/objects/")
    public String putData(@RequestBody Map<String, Object> data) {
        //determine whether the data exists
        Integer id = Integer.valueOf(String.valueOf(data.get("id")));//line 17
        Map<String, Object> containedData = dataMap.get(id);
        if (containedData == null) {
            Integer[] idArray = dataMap.keySet().toArray(new Integer[0]);
            Arrays.sort(idArray);
            int nextID = idArray[idArray.length - 1] + 1;
            dataMap.put(nextID, data);
        } else {
            dataMap.put(id,data);//如果存在，就更新
        }
        return "put success!";
    }


}
