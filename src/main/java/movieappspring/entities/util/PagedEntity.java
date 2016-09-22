package movieappspring.entities.util;

import java.util.List;

/**
 * This class is used for convenient pagination process.
 * <p>
 * Class for storing List of any provided entity for each page and
 * correspondent number of records from database.
 */
public class PagedEntity {

    /**
     * Entities to be displayed in "paged" format
     */
    private List<?> entity;

    /**
     * Quantity of records in database
     */
    private Integer numberOfRecords;

    public List<?> getEntity() {
        return entity;
    }

    public void setEntity(List<?> entity) {
        this.entity = entity;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }


}
