package com.pythonteam.dao;

import com.pythonteam.models.Token;
import com.pythonteam.models.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.ArrayList;

public interface TokenDao {

    @SqlQuery("select users.id as id, token, username, name from tokens join users on tokens.userid = users.id;")
    @RegisterBeanMapper(User.class)
    ArrayList<User> list();

    @SqlQuery("select * from tokens where userid = :userid")
    @RegisterBeanMapper(Token.class)
    Token findOne(@Bind("userid") int userid);

    @SqlUpdate("INSERT INTO tokens(token, userid ) VALUES (:token, :userid);")
    boolean create(@Bind("token") String token, @Bind("userid") int userid);

    @SqlUpdate("delete from tokens where userid = :id and token = :token")
    boolean delete(@Bind("id") int id, @Bind("token") String token);

    @SqlQuery("select userid from tokens where token = :token")
    @RegisterBeanMapper(Token.class)
    Token findByToken(@Bind("token") String token);
}
