package storage.model;

import common.Constants;
import common.Utils;
import datatypes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dakle on 9/4/17.
 */
public class DataRecord {

    private List<Object> columnValueList;

    private short size;

    private int rowId;

    public DataRecord() {
        size = 0;
        columnValueList = new ArrayList<>();
    }

    public List<Object> getColumnValueList() {
        return columnValueList;
    }

    public short getSize() {
        return size;
    }

    public void setSize(short size) {
        this.size = size;
    }

    public short getHeaderSize() {
        return (short)(Short.BYTES + Integer.BYTES);
    }

    public void populateSize() {
        this.size = (short) (this.columnValueList.size() + 1);
        for(Object object: columnValueList) {
            if(object.getClass().equals(DT_TinyInt.class)) {
                this.size += DT_TinyInt.BYTES;
            }
            else if(object.getClass().equals(DT_SmallInt.class)) {
                this.size += DT_SmallInt.BYTES;
            }
            else if(object.getClass().equals(DT_Int.class)) {
                this.size += DT_Int.BYTES;
            }
            else if(object.getClass().equals(DT_BigInt.class)) {
                this.size += DT_BigInt.BYTES;
            }
            else if(object.getClass().equals(DT_Real.class)) {
                this.size += DT_Real.BYTES;
            }
            else if(object.getClass().equals(DT_Double.class)) {
                this.size += DT_Double.BYTES;
            }
            else if(object.getClass().equals(DT_DateTime.class)) {
                size += DT_DateTime.BYTES;
            }
            else if(object.getClass().equals(DT_Date.class)) {
                this.size += DT_Date.BYTES;
            }
            else if(object.getClass().equals(DT_Text.class)) {
                this.size += ((DT_Text) object).getSize();
            }
        }
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public byte[] getSerialTypeCodes() {
        byte[] serialTypeCodes = new byte[columnValueList.size()];
        byte index = 0;
        for(Object object: columnValueList) {
            switch (Utils.resolveClass(object)) {
                case Constants.TINYINT:
                    serialTypeCodes[index++] = ((DT_TinyInt) object).getSerialCode();
                    break;

                case Constants.SMALLINT:
                    serialTypeCodes[index++] = ((DT_SmallInt) object).getSerialCode();
                    break;

                case Constants.INT:
                    serialTypeCodes[index++] = ((DT_Int) object).getSerialCode();
                    break;

                case Constants.BIGINT:
                    serialTypeCodes[index++] = ((DT_BigInt) object).getSerialCode();
                    break;

                case Constants.REAL:
                    serialTypeCodes[index++] = ((DT_Real) object).getSerialCode();
                    break;

                case Constants.DOUBLE:
                    serialTypeCodes[index++] = ((DT_Double) object).getSerialCode();
                    break;

                case Constants.DATETIME:
                    serialTypeCodes[index++] = ((DT_DateTime) object).getSerialCode();
                    break;

                case Constants.DATE:
                    serialTypeCodes[index++] = ((DT_Date) object).getSerialCode();
                    break;

                case Constants.TEXT:
                    serialTypeCodes[index++] = ((DT_Text) object).getSerialCode();
                    break;
            }
        }
        return serialTypeCodes;
    }
}