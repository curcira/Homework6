import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JokeTester {

    @Test(expected = NullPointerException.class)
    public void testConstructorAndGetter() {
        String category = "Funny";
        String content = "Knock Knock";
        Joke joke = new Joke(category, content);

        assertEquals(category, joke.getCategory());
        assertEquals(content, joke.getContent());

        new Joke(null, "content");

    }

    @Test
    public void testEquals() {
        // Test with equal content
        Joke joke1 = new Joke("Category", "Knock Knock");
        Joke joke2 = new Joke("Category", "Knock Knock");
        assertTrue(joke1.equals(joke2));

        // Test with different content
        Joke joke3 = new Joke("Category", "DiffContent!");
        assertFalse(joke1.equals(joke3));

        assertFalse(joke1.equals(null));

        Content content = new Content("knock knock");
        assertFalse(joke1.equals(content));

    }

    @Test
    public void testCompareTo() {
        Joke joke1 = new Joke("Category", "Knock Knock");
        Joke joke2 = new Joke("Category", "DiffContent!");

        assertEquals(joke1.compareTo(joke2), -1);
        assertEquals(joke2.compareTo(joke1), 1);

        Joke joke3 = new Joke("DiffCat", "Knock Knock");
        assertEquals(joke1.compareTo(joke3), 0);

    }

    @Test
    public void testToString() {

        Joke joke = new Joke("Category", "Knock Knock!!");
        String expected = "Category: Category\nJoke: Knock Knock\n";
        assertFalse(expected == joke.toString());
    }

    @Test
    public void testGetBytes() {
        Joke joke = new Joke("Category", "Knock Knock");
        byte[] bytes = joke.getBytes(30);

        assertEquals(30, bytes.length);
        assertEquals("Category".getBytes()[0], bytes[0]);
        assertEquals("Knock Knock".getBytes()[0], bytes[15]);

        byte[] bytes2 = joke.getBytes(0);
        assertEquals(500, bytes2.length);
    }

    @Test
    public void testClone() {
        Joke original = new Joke("Category", "Content");
        Joke clone = (Joke) original.clone();

        assertEquals(original.getCategory(), clone.getCategory());
        assertEquals(original.getContent(), clone.getContent());
    }
}
