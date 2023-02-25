import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BinaryFileInteraction {
    private String filename = "filename.000";
    private byte[] allTheBytes;
    private String fileData;
    private ArrayList<aRecord> alltheRecords = new ArrayList<>();

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
    }

    public int getRecordLength(int start) {
        //Returns the first 5 characters of the start point specified, used to get the length of the record from its leader
        String recordLengthString = "";
        if (start + 5 < fileData.length()) {
            for (int i = 0 + start; i < 5 + start; i++) {
                recordLengthString += fileData.charAt(i);
            }

            try {
                int recordLength = Integer.valueOf(recordLengthString);
                return recordLength;
            } catch (NumberFormatException ee) {
                System.out.println("Ouch, that's not a number");
                return -1;
            }
        } else {
            System.out.println("File is too short");
            return -1;
        }
    }

    public void getRecordsFromString(int recordLength) {
        //TODO- get catalog info in aRecord constructor
        String recordData = fileData.substring(lengthTraversed, recordLength + lengthTraversed);
        aRecord rec = new aRecord(recordLength, recordData);
        alltheRecords.add(rec);
        lengthTraversed += recordLength;
    }

}
