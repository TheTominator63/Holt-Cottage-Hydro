import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class aRecord {
    public int getRecordLength() {
        return recordLength;
    }

    public String getRecordData() {
        return recordData;
    }

    public ArrayList<CatalogueEntry> getCatalogue() {
        return catalogue;
    }

    private int recordLength;
    private String recordData;
    private byte[] recbytes;
    private byte[] catalogueData;
    private int leaderAndCatalogueLength;
    private ArrayList<CatalogueEntry> catalogue = new ArrayList<>();
    private int catalogueEntryLength;
    private int catalogueEntryOffset;
    private int catalogueEntryLabel;


    public aRecord(int recLen, String recData)
    {
        recordLength = recLen;
        recordData = recData;
    }

    public aRecord(int recLen, byte[] buffer)
    {
        recordLength = recLen;
        recbytes = buffer;
        byte[] lenBuffer = new byte[5];
        byte[] infoBuffer = new byte[4];
        recordData = new String(recbytes, StandardCharsets.UTF_8);

        // all the caatalogue stuff goes here.... :)
        //

        // use arraycopy to copy bytes to a buffer
        // then convert them to another string.

        //Extracts the length of the Leader and the Catalogue from index 12-16 of the record.
        System.arraycopy(recbytes, 12, lenBuffer, 0, 5);
        String stringBuffer = new String(lenBuffer, StandardCharsets.UTF_8);
        try {
            leaderAndCatalogueLength = Integer.valueOf(stringBuffer);
        } catch (NumberFormatException ee) {
            System.out.println("Errr, that's not a number");
            this.leaderAndCatalogueLength = -1;
        }


        //Extracts the length, label and offset of each catalogue entry from index 20-23 of the record
        System.arraycopy(recbytes, 20, infoBuffer, 0, 4);
        stringBuffer = new String(infoBuffer, StandardCharsets.UTF_8);
        try {
            catalogueEntryLength = Character.getNumericValue(stringBuffer.charAt(0));
            catalogueEntryOffset = Character.getNumericValue(stringBuffer.charAt(1));
            catalogueEntryLabel  = Character.getNumericValue(stringBuffer.charAt(3));
        } catch (NumberFormatException ee) {
            System.out.println("That isn't a number");
            catalogueEntryLength = catalogueEntryOffset = catalogueEntryLabel = -1;
        }
        //Extracts the catalogue's data from the record bytes
        buffer = new byte[leaderAndCatalogueLength - 24];
        System.arraycopy(recbytes, 24, buffer, 0, leaderAndCatalogueLength - 24);
        catalogueData = buffer;
        int sizeOfEntry = catalogueEntryLength + catalogueEntryOffset + catalogueEntryLabel;

        for (int i = 0; i < catalogueData.length - 1; i += (sizeOfEntry)) {
            buffer = new byte[sizeOfEntry];
            System.arraycopy(catalogueData, 0 + i, buffer, 0, sizeOfEntry);
            CatalogueEntry c = new CatalogueEntry(catalogueEntryLabel, catalogueEntryOffset, catalogueEntryLength, buffer);
            catalogue.add(c);
        }

    }

}
