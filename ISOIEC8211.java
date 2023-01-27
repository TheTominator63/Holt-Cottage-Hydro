
public class ISOIEC8211 {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        BinaryFileInteraction binaryFile1 = new BinaryFileInteraction("US4CN22M.000");
        System.out.println(binaryFile1.getRecordLength(0));
        while (binaryFile1.fileData.size() != binaryFile1.lengthTraversed) {
            binaryFile1.getRecordData(binaryFile1.getRecordLength(binaryFile1.lengthTraversed));
            //Test Statement below
            //System.out.println(binaryFile1.lengthTraversed);
        }
        for (int i = 0; i < binaryFile1.records.size(); i++) {
            System.out.println(binaryFile1.records.get(i));
        }
    }
}