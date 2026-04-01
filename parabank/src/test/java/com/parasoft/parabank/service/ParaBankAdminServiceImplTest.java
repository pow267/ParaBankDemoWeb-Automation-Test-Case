package com.parasoft.parabank.service;

import static org.junit.Assert.assertEquals;

import java.util.Objects;

import jakarta.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.parasoft.parabank.dao.AdminDao;
import com.parasoft.parabank.domain.logic.AdminManager;
import com.parasoft.parabank.test.util.AbstractAdminOperationsTest;

/**
 * @req PAR-24
 *
 */
public class ParaBankAdminServiceImplTest extends AbstractAdminOperationsTest {

    @Resource(name = "adminDao")
    private AdminDao adminDao;

    @Resource(name = "adminManager")
    private AdminManager adminManager;

    protected void assertParameter(final String name, final String value) {
        assertEquals(value, adminDao.getParameter(name));
    }

    @Test
    @Rollback
    public void testCleanDatabase() {
        assertEquals(12212, (int) Objects.requireNonNull(Objects.requireNonNull(getJdbcTemplate()).queryForObject("SELECT id FROM Customer WHERE id = 12212", Integer.class)));
        adminManager.cleanDB();
        assertEquals(0, (int) Objects.requireNonNull(Objects.requireNonNull(getJdbcTemplate()).queryForObject("SELECT count(*) FROM Customer", Integer.class)));
    }
}
