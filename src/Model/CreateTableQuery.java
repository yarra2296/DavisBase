package Model;

import common.Constants;
import common.Utils;
import parser.UpdateStatement;
import storage.StorageManager;

import java.util.ArrayList;
import java.util.List;

public class CreateTableQuery extends BaseQuery implements IQuery {
    public String tableName;
    public ArrayList<Column> columns;
    public boolean hasPrimaryKey;

    public CreateTableQuery(String tableName, ArrayList<Column> columns, boolean hasPrimaryKey){
        this.tableName = tableName;
        this.columns = columns;
        this.hasPrimaryKey = hasPrimaryKey;
    }

    @Override
    public Result ExecuteQuery() {
        /*TODO : replace with actual logic*/
        Result result = new Result(1);
        return result;
    }

    @Override
    public boolean ValidateQuery() {
        /*TODO : replace with actual logic*/
        if (StorageManager.defaultDatabaseExists()) {
            // Database exists.
            if (StorageManager.checkTableExists(Utils.getUserDatabasePath(Constants.DEFAULT_USER_DATABASE), tableName)) {
                // Table already exists.
                Utils.printError("\nTable " + tableName + " already exists.");
                return false;
            }
            else {
                // Create new table.
                StorageManager storageManager = new StorageManager();
                boolean status = storageManager.createTable(Utils.getUserDatabasePath(Constants.DEFAULT_USER_DATABASE), tableName + Constants.DEFAULT_FILE_EXTENSION);
                if (!status) {
                    Utils.printError("Failed to create table " + tableName);
                    return false;
                }
                else {
                    Utils.printMessage("Table " + tableName + " successfully created.");
                    List<String> columnNameList = new ArrayList<>();
                    List<String> columnDataTypeList = new ArrayList<>();
                    List<String> columnKeyConstraintList = new ArrayList<>();
                    List<String> columnNullConstraintList = new ArrayList<>();

                    for (int i = 0; i < columns.size(); i++) {

                        /*  1. Add column name.
                            2. Add column data type.
                            3. Add column key constraint.
                            4. Add column null constraint.
                         */

                        Column column = columns.get(i);
                        columnNameList.add(column.name);
                        columnDataTypeList.add(column.type.toString());

                        // Set the primary key constraint.
                        if (hasPrimaryKey && i == 0) {
                            columnKeyConstraintList.add(Constants.PRIMARY_KEY_PRESENT);
                        }
                        else {
                            columnKeyConstraintList.add(Constants.CONSTRAINT_ABSENT);
                        }

                        // Set the NULL constraints.
                        if (column.isNull) {
                           columnNullConstraintList.add(null);
                        }
                        else {
                            columnNullConstraintList.add(Constants.CONSTRAINT_ABSENT);
                        }
                    }

                    UpdateStatement statement = new UpdateStatement();
                    int startingRowId = statement.updateSystemTablesTable(tableName, 5);
                    boolean systemTableUpdateStatus = statement.updateSystemColumnsTable(tableName, startingRowId, columnNameList, columnDataTypeList, columnKeyConstraintList, columnNullConstraintList);
                    if (systemTableUpdateStatus) {
                        Utils.printMessage("System table successfully updated.");
                    }
                }
            }
        }
        else {
            // Database does not exist.
            Utils.printMissingDefaultDatabaseError();
            return false;
        }

        return true;
    }
}