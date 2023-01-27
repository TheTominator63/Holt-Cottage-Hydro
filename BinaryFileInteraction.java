import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BinaryFileInteraction {
    String filename = "filename.000";
    ArrayList<Character> fileData = new ArrayList<Character>();
    ArrayList<String> records = new ArrayList<String>();
    int lengthTraversed = 0;

    public BinaryFileInteraction(String fname) {
        filename = fname;
        File file = new File((filename));
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int charIsInt;
            char charToStore;
            while ((charIsInt = fileInputStream.read()) != -1) {
                charToStore = (char) charIsInt;
                fileData.add(charToStore);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Oh no");
        } catch (IOException e) {
            System.out.println("Oooh no");
        }
        //return(fileData);
    }

    public int getRecordLength(int start) {
        String recordLengthString = "";
        for (int i = 0 + start; i < 5 + start; i++) {
            recordLengthString = recordLengthString + (fileData.get(i)).toString();
        }
        int recordLength = Integer.valueOf(recordLengthString);
        return recordLength;
    }

    public void getRecordData(int recordLength) {
        String recordData = "";
        for (int i = 0 + lengthTraversed; i < recordLength; i++) {
            recordData = recordData + (fileData.get(i).toString());
        }
        records.add(recordData);
        lengthTraversed += recordLength;
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
