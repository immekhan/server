package com.itob.service.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DummyFormDetailDTO {

    private Map<String, DummyFormDTO> dummyFormDTOMap = new HashMap<String, DummyFormDTO>();

    public boolean containsKey(Object key) {
        return dummyFormDTOMap.containsKey(key);
    }

    public DummyFormDTO put(String key, DummyFormDTO value) {
        return dummyFormDTOMap.put(key, value);
    }

    public Collection<DummyFormDTO> values() {
        return dummyFormDTOMap.values();
    }

    public DummyFormDTO get(Object key) {
        return dummyFormDTOMap.get(key);
    }
}
