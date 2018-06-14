package com.pythonteam.handlers;

import com.pythonteam.dao.FireDao;
import com.pythonteam.database.Database;

public class FireHandler {

    public boolean add(String token) {
        return Database.getJdbi().withExtension(FireDao.class, dao -> dao.add(token));
    }
}
