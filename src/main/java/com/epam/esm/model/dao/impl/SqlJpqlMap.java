package com.epam.esm.model.dao.impl;

import java.util.HashMap;

/**
 * Class represents HashMap, where key is gift_certificate table column name,
 * and value is GiftCertificate entity field name.
 * Map is used in building jpql queries (exactly in order part of query)
 */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    static SqlJpqlMap getInstance() {
        return INSTANCE;
    }
}
