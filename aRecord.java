/*
   DOCO:  
*/

import java.util.ArrayList;

public class aRecord
{
    public int getReclen() {
        return reclen;
    }

    public void setReclen(int reclen) {
        this.reclen = reclen;
    }

    public ArrayList<Byte> getBytes() {
        return bytes;
    }

    public void setBytes(ArrayList<Byte> bytes) {
        this.bytes = bytes;
    }

    int reclen = 0;
    ArrayList<Byte> bytes = new ArrayList<>();

    public aRecord(int length, int offset, int label, int HeaderAndCatalog)
    {
        this.reclen = 0;
        this.bytes.clear();
        this.fieldWidthLength = length;
        this.fieldWidthOffset = offset;
        this.fieldWidthLabel = label;
        this.sizeOfEntry = length + offset + label;
        this.sizeOfHeaderAndCatalog = HeaderAndCatalog;
    }

    private int dataOffset;

    public String getRecordData() {
        return recordData;
    }

    public void setRecordData(String recordData) {
        this.recordData = recordData;
    }

    private String recordData;

    public int getDataOffset() {
        return dataOffset;
    }

    public void setDataOffset(int dataOffset) {
        this.dataOffset = dataOffset;
    }

    public int getFieldWidthLabel() {
        return fieldWidthLabel;
    }

    public void setFieldWidthLabel(int fieldWidthLabel) {
        this.fieldWidthLabel = fieldWidthLabel;
    }

    public int getFieldWidthOffset() {
        return fieldWidthOffset;
    }

    public void setFieldWidthOffset(int fieldWidthOffset) {
        this.fieldWidthOffset = fieldWidthOffset;
    }

    public int getFieldWidthLength() {
        return fieldWidthLength;
    }

    public void setFieldWidthLength(int fieldWidthLength) {
        this.fieldWidthLength = fieldWidthLength;
    }

    public ArrayList<CatalogueEntry> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(ArrayList<CatalogueEntry> catalogue) {
        this.catalogue = catalogue;
    }

    public int getSizeOfEntry() {
        return sizeOfEntry;
    }

    public void setSizeOfEntry(int sizeOfEntry) {
        this.sizeOfEntry = sizeOfEntry;
    }

    private int fieldWidthLabel;
    private int fieldWidthOffset;
    private int fieldWidthLength;
    private int sizeOfEntry;
    private int sizeOfHeaderAndCatalog;

    private ArrayList<CatalogueEntry> catalogue = new ArrayList<>();
    //Function to get the total number of catalog entries.
    // It gets the size of an individual entry, then divides it by the length of the catalog
//    public int getCatalogEntriesNum() {
//        String lengthFromString = "";
//        int lengthOfCatalog = 0;
//        for (int i = 12; i < 17; i++) {
//            lengthFromString += String.valueOf(getRecordData().charAt(i));
//        }
//        lengthOfCatalog = Integer.valueOf(lengthFromString);
//        return lengthOfCatalog - 24/sizeOfEntry;
//    }

    public void getCatalogueData() {
        for (int i = 24; i < sizeOfHeaderAndCatalog - 24; i += (sizeOfHeaderAndCatalog - 24)/sizeOfEntry) {
           System.out.println("not finished");
        }
    }

}
