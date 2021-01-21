package com.epam.esm.model.dao.impl;

import java.util.HashMap;

class SqlJpqlMap extends HashMap<String, String> {

    private static final SqlJpqlMap INSTANCE;

    static {
        INSTANCE = new SqlJpqlMap();
        INSTANCE.put("price", "g.price");
        INSTANCE.put("name", "g.name");
        INSTANCE.put("description", "g.description");
        INSTANCE.put("create_date", "g.createDate");
        INSTANCE.put("last_update_date", "g.lastUpdateDate");
        INSTANCE.put("duration", "g.duration");
    }

    static SqlJpqlMap getInstance() {
        return INSTANCE;
    }
}
