/*
 * This file is generated by jOOQ.
 */
package example.udt;


import example.udt.records.TagRecord;

import org.jooq.UDTField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UDTImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tag extends UDTImpl<TagRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>tag</code>
     */
    public static final Tag TAG = new Tag();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TagRecord> getRecordType() {
        return TagRecord.class;
    }

    /**
     * The attribute <code>tag.name</code>.
     */
    public static final UDTField<TagRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB, TAG, "");

    /**
     * The attribute <code>tag.value</code>.
     */
    public static final UDTField<TagRecord, String> VALUE = createField(DSL.name("value"), SQLDataType.CLOB, TAG, "");

    /**
     * No further instances allowed
     */
    private Tag() {
        super(DSL.name("tag"), null, null, false);
    }
}
