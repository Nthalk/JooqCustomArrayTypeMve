/*
 * This file is generated by jOOQ.
 */
package example.udt.records;


import example.udt.Tag;
import example.udt.interfaces.ITag;

import org.jooq.impl.UDTRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class TagRecord extends UDTRecordImpl<TagRecord> implements ITag {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>tag.name</code>.
     */
    @Override
    public void setName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>tag.name</code>.
     */
    @Override
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>tag.value</code>.
     */
    @Override
    public void setValue(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>tag.value</code>.
     */
    @Override
    public String getValue() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(ITag from) {
        setName(from.getName());
        setValue(from.getValue());
        resetChangedOnNotNull();
    }

    @Override
    public <E extends ITag> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TagRecord
     */
    public TagRecord() {
        super(Tag.TAG);
    }

    /**
     * Create a detached, initialised TagRecord
     */
    public TagRecord(String name, String value) {
        super(Tag.TAG);

        setName(name);
        setValue(value);
        resetChangedOnNotNull();
    }
}
