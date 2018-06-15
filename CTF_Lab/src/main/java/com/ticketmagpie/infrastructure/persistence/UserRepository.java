package com.ticketmagpie.infrastructure.persistence;

import static java.lang.String.format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ticketmagpie.User;

@Component
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User get(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", (rs, rowNum) -> toUser(rs), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User checkUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> toUser(rs), username, password);
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> toUser(rs));
    }

    public void delete(String username) {
        jdbcTemplate.update("DELETE FROM users WHERE username=?", username);
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)", user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    private User toUser(ResultSet rs) throws SQLException {
        return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("role"));
    }
}
