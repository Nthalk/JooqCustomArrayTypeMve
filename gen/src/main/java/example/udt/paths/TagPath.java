/*
 * This file is generated by jOOQ.
 */
package example.udt.paths;


import example.udt.Tag;
import example.udt.records.TagRecord;

import org.jooq.Binding;
import org.jooq.Comment;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.RecordQualifier;
import org.jooq.UDTField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UDTPathTableFieldImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class TagPath<R extends Record, T> extends UDTPathTableFieldImpl<R, TagRecord, T> {

    private static final long serialVersionUID = 1L;

    /**
     * The attribute <code>tag.name</code>.
     */
    public final UDTField<TagRecord, String> NAME = Internal.createUDTPathField(DSL.name("name"), SQLDataType.CLOB, this, "", UDTField.class);

    /**
     * The attribute <code>tag.value</code>.
     */
    public final UDTField<TagRecord, String> VALUE = Internal.createUDTPathField(DSL.name("value"), SQLDataType.CLOB, this, "", UDTField.class);

    public TagPath(Name name, DataType<T> type, RecordQualifier<R> qualifier, Comment comment, Binding<?, T> binding) {
        super(name, type, qualifier, Tag.TAG, comment, binding);
    }
}
