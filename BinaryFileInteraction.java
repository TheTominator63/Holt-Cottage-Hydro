import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main object.
 */
public class BinaryFileInteraction
{
    private String filename = "filename.000";
    private ArrayList<Character> fileData = new ArrayList<>();
    private ArrayList<String> recordsData = new ArrayList<>();
    private ArrayList<Integer> recordlengths = new ArrayList<>();

    public ArrayList<aRecord> getAllrecords() {
        return allrecords;
    }

    private ArrayList<aRecord> allrecords = new ArrayList<>();


    public void addRecordLength(int i)
    {
        recordlengths.add(i);
    }

    private int lengthTraversed = 0;

    public String getFilename() {
        return filename;
    }

    public ArrayList<Character> getFileData() {
        return fileData;
    }

    public ArrayList<String> getRecordsData() {
        return recordsData;
    }

    public int getLengthTraversed() {
        return lengthTraversed;
    }



    /**
     * constructor
     * @param fname - external file to open.
     */
    public BinaryFileInteraction(String fname) throws FileNotFoundException,IOException
    {

        //TODO: maybe use File to byte [] to speed things up....
        filename = fname;
        File file = new File((filename));
        FileInputStream fileInputStream = new FileInputStream(file);

        int charIsInt;
        char charToStore;
        while ((charIsInt = fileInputStream.read()) != -1)
        {
            charToStore = (char) charIsInt;
            fileData.add(charToStore);
        }
        fileInputStream.close();
        //return(fileData);
    }

    public int getRecordLength(int start)
    {

        // check that start+5 not greater than fileData...?

        String recordLengthString = "";
        for (int i = 0 + start; i < 5 + start; i++)
        {
            recordLengthString += (fileData.get(i)).toString();
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
    }

    public void getRecordData(int recordLength)
    {
        String recordData = "";
        int catalogLength = 0;
        int catalogOffset = 0;
        int catalogLabel = 0;
        String transferringChars;
        for (int i = 0 + lengthTraversed; i < recordLength; i++)
        {
            recordData = recordData + (fileData.get(i).toString());
            if (i - lengthTraversed == 12) {
                transferringChars = String.valueOf(fileData.get(i));
                transferringChars += String.valueOf(fileData.get(i + 1));
                transferringChars += String.valueOf(fileData.get(i + 2));
                transferringChars += String.valueOf(fileData.get(i + 3));
                transferringChars += String.valueOf(fileData.get(i + 4));
                int HeaderAndCatalogLength = Integer.valueOf(transferringChars);
            } else if (i - lengthTraversed == 20) {
                transferringChars = String.valueOf(fileData.get(i));
                catalogLength = Integer.valueOf(transferringChars);
                transferringChars = String.valueOf(fileData.get(i + 1));
                catalogOffset = Integer.valueOf(transferringChars);
                transferringChars = String.valueOf(fileData.get(i + 3));
                catalogLabel = Integer.valueOf(transferringChars);
            }
            System.out.println(recordData.length());
        }
        recordsData.add(recordData);
        System.out.println(recordData.length());
        recordlengths.add(recordLength);
        lengthTraversed += recordLength;

        // process this record...
        aRecord rec = new aRecord(catalogLength, catalogOffset, catalogLabel);
        rec.setReclen(recordLength);
        rec.setRecordData(recordData);
        rec.getCatalogEntriesNum();
        // setup record entries....

        // set the bytes
        ArrayList<Byte> thebytes = new ArrayList<>();
        rec.setBytes(thebytes);

        // set the information from the leader
        // - done in the constructor
        // get the catalogue entries

        allrecords.add(rec);
    }

    // Testing code just to check if the program works, not required now.
//    public void printRecordLength() {
//        String recordLength = "";
//        int recordIndex = 0;
//        for (recordIndex=0; recordIndex < 5; recordIndex++) {
//            recordLength.concat(String.valueOf(fileData.get(recordIndex)));
//        }
//        System.out.println(recordLength);
//    }

}
