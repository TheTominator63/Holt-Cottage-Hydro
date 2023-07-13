import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DDR {
    public aRecord getRecWithDDR() {
        return recWithDDR;
    }

    public void setRecWithDDR(aRecord recWithDDR) {
        this.recWithDDR = recWithDDR;
    }

    private aRecord recWithDDR;

    public String getDataDDR() {
        return dataDDR;
    }

    public void setDataDDR(String dataDDR) {
        this.dataDDR = dataDDR;
    }

    private String dataDDR;

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public ArrayList<String> getSubfields() {
        return subfields;
    }

    public void setSubfields(ArrayList<String> subfields) {
        this.subfields = subfields;
    }

    private ArrayList<String> fields = new ArrayList<String>();
    private ArrayList<String> subfields = new ArrayList<String>();

    public byte[] getBytesDDR() {
        return bytesDDR;
    }

    public void setBytesDDR(byte[] bytesDDR) {
        this.bytesDDR = bytesDDR;
    }

    private byte[] bytesDDR;

    public ArrayList<String> getPairs() {
        return pairs;
    }

    public void setPairs(ArrayList<String> pairs) {
        this.pairs = pairs;
    }

    private ArrayList<String> pairs = new ArrayList<String>();

    public DDR (aRecord record) {
        recWithDDR = record;
        dataDDR = recWithDDR.getRecordData();
        dataDDR = dataDDR.substring(recWithDDR.getLeaderAndCatalogueLength(), recWithDDR.getRecordLength());
        bytesDDR = dataDDR.getBytes(StandardCharsets.UTF_8);
        ArrayList<Integer> startAndEndBytes = new ArrayList<Integer>();

        for (int i = 0; i < bytesDDR.length; i++) {
            switch (bytesDDR[i]) {
                case 31:
                    startAndEndBytes.add(i);
                    startAndEndBytes.add(31);
                    break;
                case 30:
                    startAndEndBytes.add(i);
                    startAndEndBytes.add(30);
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; i < startAndEndBytes.size(); i += 2) {
            switch (startAndEndBytes.get(i+1)) {
                case 31:
                    if (i == 0) {
                        byte[] byteBuffer = new byte[startAndEndBytes.get(i)];
                        System.arraycopy(bytesDDR, 0, byteBuffer, 0, startAndEndBytes.get(i));
                        String buffer = new String(byteBuffer, StandardCharsets.UTF_8);
                        fields.add(buffer);
                    } else {
                        byte[] byteBuffer = new byte[startAndEndBytes.get(i) - startAndEndBytes.get(i - 2)];
                        System.arraycopy(bytesDDR, startAndEndBytes.get(i-2), byteBuffer, 0, startAndEndBytes.get(i) - startAndEndBytes.get(i - 2));
                        String buffer = new String(byteBuffer, StandardCharsets.UTF_8);
                        if (startAndEndBytes.get(i) - startAndEndBytes.get(i - 2) == 1) {
                            break;
                        } else {
                            fields.add(buffer);
                        }

                    }
                    break;
                case 30:
                    byte[] byteBuffer = new byte[startAndEndBytes.get(i) - startAndEndBytes.get(i - 2)];
                    System.arraycopy(bytesDDR, startAndEndBytes.get(i-2), byteBuffer, 0, startAndEndBytes.get(i) - startAndEndBytes.get(i - 2));
                    String buffer = new String(byteBuffer, StandardCharsets.UTF_8);
                    subfields.add(buffer);
                    break;
            }
        }
    }

    public void matchPairs() {
        //To Be Done...
    }

}
