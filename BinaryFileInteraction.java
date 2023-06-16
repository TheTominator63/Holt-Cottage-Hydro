import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.sql.ResultSet;

public class BinaryFileInteraction {
    private String filename = "filename.000";
    private byte[] allTheBytes;
    private String fileData;
    private ArrayList<aRecord> alltheRecords = new ArrayList<>();
    public byte[] recordbuffer;

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public ArrayList<aRecord> getAlltheRecords() {
        return alltheRecords;
    }

    public void setAlltheRecords(ArrayList<aRecord> alltheRecords) {
        this.alltheRecords = alltheRecords;
    }

    public int getLengthTraversed() {
        return lengthTraversed;
    }

    public void setLengthTraversed(int lengthTraversed) {
        this.lengthTraversed = lengthTraversed;
    }

    private int lengthTraversed = 0;

    public BinaryFileInteraction(String fname) throws FileNotFoundException, IOException {
        //Gets filename from main class and puts the file's data into a byte array
        filename = fname;
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        allTheBytes = new byte[(int)file.length()];
        fileInputStream.read(allTheBytes);
        fileInputStream.close();

        //Converts raw bytes into a string of characters
        fileData = new String(allTheBytes, StandardCharsets.UTF_8);
        System.out.println("length allthebyytes=" + allTheBytes.length + " lengthstring=" + fileData.length());
//        for (int i=0;i<allTheBytes.length;i++)
//        {
//            byte x = allTheBytes[i];
//            String s2 = fileData.substring(i,i+1);
//            byte []b = s2.getBytes();
//            if (b.length>1)
//            {
//                System.out.println("Er...");
//            }
//            System.out.println("i=" + i + " b=" + allTheBytes[i] + " s=" + fileData.substring(i,i+1));
//        }
    }

    public int getRecordLength(int start)
    {
        //Returns the first 5 characters of the start point specified, used to get the length of the record from its leader
        String recordLengthString = "";
        if (start + 5 < allTheBytes.length)
        {
            for (int i = 0 + start; i < 5 + start; i++)
            {
                byte x = allTheBytes[i];
            recordLengthString += (char) allTheBytes[i];
            }

            try
            {
                int recordLength = Integer.valueOf(recordLengthString);
                return recordLength;
            }
            catch (NumberFormatException ee)
            {
                System.out.println("Ouch, that's not a number");
                return -1;
            }
        } else {
            System.out.println("File is too short");
            return -1;
        }
    }

    public void getRecordsFromString(int recordLength)
    {
        //Test Statement
        //System.out.println("RECLEN=" + recordLength + " LengthTraversed=" + lengthTraversed);
        recordbuffer = new byte[recordLength];
        System.arraycopy(allTheBytes,lengthTraversed, recordbuffer,0, recordLength);

        String recordData = fileData.substring(lengthTraversed, recordLength + lengthTraversed);

        //aRecord rec = new aRecord(recordLength, recordData);
        aRecord rec2 = new aRecord(recordLength, recordbuffer);

        alltheRecords.add(rec2);
        lengthTraversed += recordLength;
    }
    public void uploadToDb() {
        String recId;
        String catId;
        String recLength;
        String catLength;
        int entryId = 0;
        String entryLength;
        String entryOffset;
        String entryLabel;
        ArrayList<String[]> catalogueEntries = new ArrayList<String[]>();
        int entryCount = 0;

        dbC db = new dbC("localhost","hch","postgres","postgresql","5432");
        db.do_sql_nr("delete from catalogue_entries where entry_id >= 0");
        db.do_sql_nr("delete from catalogues where catalogue_id >= 0");
        db.do_sql_nr("delete from records where record_id >= 0");
        for (int i = 0; i < alltheRecords.size(); i++) {
            recId = Integer.toString(i);
            catId = Integer.toString(i);
            aRecord record = alltheRecords.get(i);
            String recData = record.getRecordData();
            recLength = Integer.toString(record.getRecordLength());
            catLength = Integer.toString(record.getCatalogue().size());
            db.do_sql_nr("insert into records(record_id, record_length, catalogue_id, record_data) values("+ recId + ", " + recLength + ", " + catId + ", \'" + "recData" + "\')");
            //String[] lengths = new String[record.getCatalogue().size()];
            //String[] offsets = new String[record.getCatalogue().size()];
            //String[] labels  = new String[record.getCatalogue().size()];
            String lengths = "'{";
            String offsets = "'{";
            String labels  = "'{";

            for (int catI = 0; catI < record.getCatalogue().size(); catI++) {
                CatalogueEntry entry1 = record.getCatalogue().get(catI);
                lengths += Integer.toString(entry1.getLength()) + ", ";
                offsets += Integer.toString(entry1.getOffset()) + ", ";
                labels += "\"" + entry1.getLabel() + "\"" + ", ";
                entryLength = Integer.toString(entry1.getLength());
                entryOffset = Integer.toString(entry1.getOffset());
                entryLabel = entry1.getLabel();
                //catalogueEntries.add(new String[]{entryLength, entryOffset, entryLabel});

            }
            lengths = lengths.substring(0, lengths.length() - 2);
            offsets = offsets.substring(0, offsets.length() - 2);
            labels = labels.substring(0, labels.length() - 2);
            lengths += "}'";
            offsets += "}'";
            labels  += "}'";



            db.do_sql_nr("insert into catalogues(catalogue_id, record_id, catalogue_length, entry_lengths, entry_offsets, entry_labels) values("+ catId + ", " + recId + ", " + catLength + ", " + lengths + ", " + offsets + ", " + labels + ")");

            for (int catI = 0; catI < record.getCatalogue().size(); catI++) {
                CatalogueEntry entry2 = record.getCatalogue().get(catI);
                entryLength = Integer.toString(entry2.getLength());
                entryOffset = Integer.toString(entry2.getOffset());
                entryLabel = entry2.getLabel();
                db.do_sql_nr("insert into catalogue_entries(entry_id, catalogue_id, entry_length, entry_offset, entry_label) values("+ entryId + ", " + catId + ", " + entryLength + ", " + entryOffset + ", \'" + entryLabel + "\')");
                entryId++;

            }
        }
    }

    public void printCatalogueTotals() {
        dbC db = new dbC("localhost","hch","postgres","postgresql","5432");
        ResultSet tot = db.do_sql("select count(*) tot, entry_labels from catalogues group by entry_labels order by tot desc");
        try
        {
            while (tot.next())
            {
                int total = tot.getInt("tot");
                String label = tot.getString("entry_labels");
                System.out.println("total- " + total + "\nlabels- " + label);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void printRecordsFromDb() {
        dbC db = new dbC("localhost","hch","postgres","postgresql","5432");
        ResultSet r = db.do_sql("select * from records");
        try
        {
            while (r.next())
            {
                int recId = r.getInt("record_id");
                int msg = r.getInt("record_length");
                //String recData = r.getString("record_data");
                System.out.println("id=" + recId + "\nlength=" + msg);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
