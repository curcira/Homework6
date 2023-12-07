
public class Joke extends Content
        implements Convertable, Comparable<Joke>, Cloneable {
    private String category;

    /**
     * Constructor that initializes the Category and Content instance variables
     * with the deep-copy of the given Strings.
     * 
     * @param category String of the category
     * @param content  String of the content
     */
    public Joke(String category, String content) {
        super(content);

        this.category = new String(category);
    }

    /**
     * Getter method.
     * 
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     * Method returns true if the result of comparing this object with another
     * using the compareTo() method is zero.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Joke)) {
            return false;
        }
        Joke otherJoke = (Joke) obj;
        return this.compareTo(otherJoke) == 0;
    }

    /**
     * If both objects have contents of the same length returns the result of
     * comparing the first 10 characters of these contents. If the contents of
     * both objects are of different lengths, returns the result of comparing
     * the length of the contents using the Interger.compare() method.
     */
    @Override
    public int compareTo(Joke otherJoke) {
        String thisContent = this.getContent();
        String otherContent = otherJoke.getContent();

        int thisLength = thisContent.length();
        int otherLength = otherContent.length();

        if (thisLength == otherLength) {
            String thisSubstring = thisContent.substring(0, 10);
            String otherSubstring = thisContent.substring(0, 10);
            return thisSubstring.compareTo(otherSubstring);
        }
        return Integer.compare(thisLength, otherLength);
    }

    /**
     * Returns a String representation of this object, with the a specific
     * format.
     */
    @Override
    public String toString() {
        int maxLength = 10;
        String content = getContent();

        return String.format("Category: %s%nJoke: %s%n", category,
                content.substring(0, maxLength));
    }

    /**
     * Converts joke into a byte array of a specified size.
     */
    @Override
    public byte[] getBytes(int byteSize) {

        if (byteSize == 0) {
            byteSize = 500;
        }

        byte[] result = new byte[byteSize];
        byte[] catBytes = category.getBytes();
        byte[] contentBytes = getContent().getBytes();

        int catLength = catBytes.length;
        int contentLength = contentBytes.length;

        int catCopyLength = 15;
        int contentCopyLength = byteSize - 15;

        if (catLength < 15) {
            catCopyLength = catLength;
        }

        if (contentLength < byteSize - 15) {
            contentCopyLength = contentLength;
        }

        System.arraycopy(catBytes, 0, result, 0, catCopyLength);
        System.arraycopy(contentBytes, 0, result, 15, contentCopyLength);

        return result;
    }

    /**
     * Returns an object which is a deep copy of this Joke object.
     */
    @Override
    public Object clone() {
        return new Joke(this.getCategory(), this.getContent());
    }

}
