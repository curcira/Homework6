
import java.util.ArrayList;
import java.util.List;

public class Content implements Searchable {

    private String content;

    /**
     * Constructor that initializes the content instance variable with the
     * deep-copy of the given String.
     * 
     * @param input String make a deep copy of and create a Content object
     */
    public Content(String input) {

        this.content = new String(input);
    }

    /**
     * Copy constructor.
     * 
     * @param other Content object
     */
    public Content(Content other) {
        this(other.getContent());
    }

    /**
     * Getter method.
     * 
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * Checks if the content instance variable contains the given key and
     * returns a boolean value.
     */
    public boolean contains(String key) {
        return content.contains(key);
    }

    /**
     * Finds all occurrences of the given key in the content instance variable
     * and returns their indices as an array of integers.
     */
    @Override
    public Integer[] indexOf(String key) {

        List<Integer> locs = new ArrayList<>();

        int startIndex = 0;

        String lowercaseContent = content.toLowerCase();
        String lowercaseKey = key.toLowerCase();

        while (startIndex < lowercaseContent.length()) {
            int index = lowercaseContent.indexOf(lowercaseKey, startIndex);

            if (index == -1) {
                break;
            }

            locs.add(index);
            startIndex = index + 1;
        }

        return locs.toArray(new Integer[locs.size()]);
    }

    /**
     * Returns as object which is a deep copy of this Content object.
     */
    @Override
    public Object clone() {
        return new Content(this);
    }

    /**
     * Returns a String representation of the object with a specific format.
     */
    @Override
    public String toString() {
        return String.format("Content: %s%n", content);
    }

}
