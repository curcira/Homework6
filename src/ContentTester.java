import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ContentTester {

    @Test(expected = NullPointerException.class)
    public void testConstructor() {

        Content content = new Content("Test Content");
        assertEquals("Test Content", content.getContent());

        new Content((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor2() {

        Content originalContent = new Content("Original Content");
        Content copiedContent = new Content(originalContent);
        assertEquals("Original Content", copiedContent.getContent());

        Content obj = null;
        new Content(obj);
    }

    @Test
    public void testContains() {
        Content content = new Content("This is a test content");

        assertTrue(content.contains("test"));

        assertFalse(content.contains("missing"));
    }

    @Test
    public void testIndexOf() {
        Content content = new Content(
                "This is a test content. This content is for testing.");

        Integer[] indices = content.indexOf("test");
        Integer[] expected = { 10, 44 };
        assertArrayEquals(expected, indices);

        Integer[] none = content.indexOf("none");
        Integer[] expected2 = { 2 };
        assertFalse(none.equals(expected2));
    }

    @Test
    public void testClone() {
        Content original = new Content("Test Content");
        Content clone = (Content) original.clone();

        assertEquals(original.getContent(), clone.getContent());
    }

    @Test
    public void testToString() {
        Content content = new Content("Test Content");

        assertEquals("Content: Test Content\n", content.toString());
    }
}
