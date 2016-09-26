package diogo.tablegenerator.processor;

/**
 * Created by diogojayme on 8/4/16.
 */
public class ColumnMetadata {
    private int size;
    private String columnName;
    private boolean primaryKey;
    private Class<?> fieldType;
    private String foreignKeyId;
    private String referencesTable;
    private boolean foreignKey;
    private Object value;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public String getForeignKeyId() {
        return foreignKeyId;
    }

    public void setForeignKeyId(String foreignKeyId) {
        this.foreignKeyId = foreignKeyId;
    }

    public String getReferencesTable() {
        return referencesTable;
    }

    public void setReferencesTable(String referencesTable) {
        this.referencesTable = referencesTable;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
