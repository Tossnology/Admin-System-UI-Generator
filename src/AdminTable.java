import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminTable {
    private String htmlId;
    private String tableName;
    private List<String[]> properties;
    private List<AdminTable> subTables;
    private List<String> operations;
    private boolean editEnable;
    private boolean deleteEnable;

    public String getHtmlId() {
        return htmlId;
    }

    public void setHtmlId(String htmlId) {
        this.htmlId = htmlId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String[]> getProperties() {
        return properties;
    }

    public void setProperties(List<String[]> properties) {
        this.properties = properties;
    }

    public List<AdminTable> getSubTables() {
        return subTables;
    }

    public void setSubTables(List<AdminTable> subTables) {
        this.subTables = subTables;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public AdminTable(String tableName, List<String[]> properties, List<AdminTable> subTables, List<String> operations, boolean editEnable, boolean deleteEnable) {
        this.tableName = tableName;
        this.properties = properties;
        this.subTables = subTables;
        this.operations = operations;
        this.editEnable = editEnable;
        this.deleteEnable = deleteEnable;
    }

    public AdminTable(String htmlId, String tableName, List<String[]> properties, List<AdminTable> subTables, List<String> operations, boolean editEnable, boolean deleteEnable) {
        this.htmlId = htmlId;
        this.tableName = tableName;
        this.properties = properties;
        this.subTables = subTables;
        this.operations = operations;
        this.editEnable = editEnable;
        this.deleteEnable = deleteEnable;
    }

    public AdminTable() {
    }

    public boolean isEditEnable() {
        return editEnable;
    }

    public void setEditEnable(boolean editEnable) {
        this.editEnable = editEnable;
    }

    public boolean isDeleteEnable() {
        return deleteEnable;
    }

    public void setDeleteEnable(boolean deleteEnable) {
        this.deleteEnable = deleteEnable;
    }
}
