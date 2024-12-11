/*
 * This file is generated by jOOQ.
 */
package example;


import example.tables.Test;
import example.tables.records.TestRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<TestRecord> TEST_PKEY = Internal.createUniqueKey(Test.TEST, DSL.name("test_pkey"), new TableField[] { Test.TEST.ID }, true);
}
