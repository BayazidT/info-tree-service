package com.trbtree.infotree.modules.civic.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class dbCheck {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void check() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            System.out.println("DB connected: " + conn.getCatalog());
        }
    }
}