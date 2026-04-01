package com.parasoft.parabank.test.util;

import static org.junit.Assert.assertEquals;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Utility class for validating integer counts in the database.</DD>
 * <DT>Date:</DT>
 * <DD>Oct 7, 2015</DD>
 * </DL>
 *
 * @author nrapo - Nick Rapoport
 *
 */
public class IntQuery {
    Logger log = LoggerFactory.getLogger(IntQuery.class);

    private final int expected;

    private final String query;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>IntQuery Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Oct 7, 2015</DD>
     * </DL>
     *
     * @param aExpected the expected result count
     * @param aQuery the SQL query to execute
     */
    public IntQuery(final int aExpected, final String aQuery) {
        expected = aExpected;
        query = aQuery;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Validates that the query returns the expected count.</DD>
     * <DT>Date:</DT>
     * <DD>Oct 7, 2015</DD>
     * </DL>
     *
     * @param aTemplate the JdbcTemplate to use for execution
     */
    public void validate(final JdbcTemplate aTemplate) {
        final Number number = aTemplate.queryForObject(Objects.requireNonNull(query), Integer.class);
        final int result = number != null ? number.intValue() : 0;
        log.debug("Testing: {}, expect {}", query, result);
        assertEquals(expected, result);
    }
}
