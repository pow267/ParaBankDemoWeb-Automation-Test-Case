package com.parasoft.bookstore;

import java.sql.SQLException;

/**
 * BookStoreMemoryDB
 */
public class BookStoreMemoryDB extends DB {
    private static BookStoreMemoryDB db = null;

    /**
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public BookStoreMemoryDB()
        throws SQLException,
            InstantiationException,
            IllegalAccessException,
            ClassNotFoundException,
            java.lang.reflect.InvocationTargetException,
            java.lang.NoSuchMethodException
    {
        super();
        // RJ Auto-generated constructor stub
    }

    public static BookStoreMemoryDB getDBInstance()
        throws SQLException,
            InstantiationException,
            IllegalAccessException,
            ClassNotFoundException,
            java.lang.reflect.InvocationTargetException,
            java.lang.NoSuchMethodException
    {
        if (db == null) {
            db = new BookStoreMemoryDB();
        }
        return db;
    }
}