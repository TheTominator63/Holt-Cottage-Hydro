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

    public aRecord(int length, int offset, int label, int HeaderAndCatalogue)
    {
        this.reclen = 0;
        this.bytes.clear();
        this.fieldWidthLength = length;
        this.fieldWidthOffset = offset;
        this.fieldWidthLabel = label;
        this.sizeOfEntry = length + offset + label;
        this.sizeOfHeaderAndCatalogue = HeaderAndCatalogue;
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
    private int sizeOfHeaderAndCatalogue;

    public int getNumberOfCatalogueEntries() {
        return numberOfCatalogueEntries;
    }

    public void setNumberOfCatalogueEntries(int numberOfCatalogueEntries) {
        this.numberOfCatalogueEntries = numberOfCatalogueEntries;
    }

    private int numberOfCatalogueEntries = 0;

    private ArrayList<CatalogueEntry> catalogue = new ArrayList<>();
    //Function to get the total number of catalogue entries.
    // It gets the size of an individual entry, then divides it by the length of the catalogue
//    public int getCatalogueEntriesNum() {
//        String lengthFromString = "";
//        int lengthOfCatalogue = 0;
//        for (int i = 12; i < 17; i++) {
//            lengthFromString += String.valueOf(getRecordData().charAt(i));
//        }
//        lengthOfCatalogue = Integer.valueOf(lengthFromString);
//        return lengthOfCatalogue - 24/sizeOfEntry;
//    }

    public void getCatalogueData()
    {

        this.numberOfCatalogueEntries = (sizeOfHeaderAndCatalogue-24-1)/(sizeOfEntry);

        for (int i=0;i<this.numberOfCatalogueEntries;i++)
        {
            /*
            fill this in....
             */

            CatalogueEntry c = new CatalogueEntry();
            c.setLabel("");
            c.setLength(0);
            c.setOffset(0);

            this.catalogue.add(c);
        }

        for (int i = 24; i < sizeOfHeaderAndCatalogue - 24; i += (sizeOfHeaderAndCatalogue - 24)/sizeOfEntry)
        {
           System.out.println("not finished");
        }
    }


    /*
            this.reclen = 0;
        this.bytes.clear();
        this.fieldWidthLength = length;
        this.fieldWidthOffset = offset;
        this.fieldWidthLabel = label;
        this.sizeOfEntry = length + offset + label;
        this.sizeOfHeaderAndCatalogue = HeaderAndCatalogue;
     */
    @Override
    public String toString()
    {
        return "Bytes=" + this.getReclen() + " fieldWidthLabel=" +
                this.fieldWidthLabel + " fieldWidthLength=" +
                fieldWidthLength +
                " fieldWidthOffset=" +
                fieldWidthOffset +
                " sizeofHeaderAndCatalogue=" + sizeOfHeaderAndCatalogue +
                " number of catalogue entries=" + numberOfCatalogueEntries;
    }

}
