package com.samuel.ebankingenterpriseapp.payload.response;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {


    private String message;

    @JsonIgnore
    private Object object;

    @JsonIgnore
    private List<?> objectList;


    @JsonAnyGetter
    public Map<String, Object> dynamicFields() {
        Map<String, Object> map = new HashMap<>();
        if (object != null) {
            String objectKey = object.getClass().getSimpleName().toLowerCase();
            map.put(objectKey, object);
        }
        if (objectList != null && !objectList.isEmpty()) {
            String listKey = objectList.get(0).getClass().getSimpleName().toLowerCase() + "List";
            map.put(listKey, objectList);
        }
        return map;
    }
}
