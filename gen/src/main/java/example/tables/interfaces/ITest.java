/*
 * This file is generated by jOOQ.
 */
package example.tables.interfaces;


import example.udt.interfaces.ITag;

import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public interface ITest extends Serializable {

    /**
     * Setter for <code>test.id</code>.
     */
    public void setId(Integer value);

    /**
     * Getter for <code>test.id</code>.
     */
    public Integer getId();

    /**
     * Setter for <code>test.tags</code>.
     */
    public void setTags(ITag[] value);

    /**
     * Getter for <code>test.tags</code>.
     */
    public ITag[] getTags();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common
     * interface ITest
     */
    public void from(ITest from);

    /**
     * Copy data into another generated Record/POJO implementing the common
     * interface ITest
     */
    public <E extends ITest> E into(E into);
}