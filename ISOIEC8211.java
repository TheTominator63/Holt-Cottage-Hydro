import java.sql.ResultSet;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ISOIEC8211 {
    public static void main(String[] args) {
        //database access blueprint
        //dbC db = new dbC("localhost","hch","postgres","postgresql","5432");

        //db.do_sql_nr("insert into catalogues(catalogue_id, catalogue_length) values(0, 16)");

        //ResultSet r = db.do_sql("select * from catalogues");
        //try
        //{
        //    while (r.next())
        //    {
        //        int id = r.getInt("catalogue_id");
        //        int msg = r.getInt("catalogue_length");
        //        System.out.println("id=" + id + " length=" + msg);
        //    }
        //}
        //catch (Exception e)
        //{
        //    e.printStackTrace();
        //}

        System.out.println("Hello world!");
        String filename = "dat/US4CN22M.000";
        try {
            BinaryFileInteraction binaryFile1 = new BinaryFileInteraction(filename);
            while (binaryFile1.getFileData().length() != binaryFile1.getLengthTraversed())
            {
                try
                {
                    binaryFile1.getRecordsFromString(binaryFile1.getRecordLength(binaryFile1.getLengthTraversed()));
                    // Test Statement
                    // System.out.println("Offset =>" + binaryFile1.getLengthTraversed());
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    System.out.println("It's all finished");
                    binaryFile1.uploadToDb();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}