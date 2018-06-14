package com.pythonteam.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FireDao {

    @SqlUpdate("INSERT INTO firetokens(token) VALUES (:token);")
    boolean add(@Bind("token") String token);

}
