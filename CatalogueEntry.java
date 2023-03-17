
import java.nio.charset.StandardCharsets;

public class CatalogueEntry
{
    private String label;
    private int offset;
    private int length;
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public CatalogueEntry(int labSize, int offSize, int lenSize, byte[] entryInBytes) {
        String entryInString = new String(entryInBytes, StandardCharsets.UTF_8);
        label = entryInString.substring(0, labSize);
        try {
            offset = Integer.valueOf(entryInString.substring(labSize, labSize + offSize));
            length = Integer.valueOf(entryInString.substring(labSize + offSize, entryInString.length()));
        } catch (NumberFormatException ee) {
            System.out.println("Oh dear, we haven't got a number");
        }
    }

}
