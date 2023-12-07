import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Class that handles file operations with random access, enabling the storage
 * and retrieval of joke data in a serialized form.
 * 
 * @author rachelcurci
 */
public class FileManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private RandomAccessFile raf;
    private int secSize;

    /**
     * Constructor that initializes the FileManager object with a file at the
     * given path for reading and writing, using RandomAccessFile class, and
     * sets the given size into the secSize for subsequent read/write
     * operations.
     * 
     * @param path String - path of file.
     * @param size int - for secSize for future operations.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public FileManager(String path, int size) throws Exception {
        this.raf = new RandomAccessFile(path, "rw");
        this.secSize = size;
    }

    /**
     * Getter method.
     * 
     * @return secSize from the FileManager object.
     */
    public int getSecSize() {
        return secSize;
    }

    /**
     * Calculates the total number of data sections in the file.
     * 
     * @return number of sections in the file.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public int size() throws Exception {
        int sections = (int) (raf.length() / secSize);
        return sections;
    }

    /**
     * Writes a given byte array value into the specified section in the file.
     * 
     * @param index section of file specified to write the array
     * @param data  byte array of data to add
     * @throws Exception for IOException and FileNotFoundException.
     */
    public void write(int index, byte[] data) throws Exception {
        raf.seek(index * secSize);
        raf.write(data);
    }

    /**
     * Reads data from a specified section.
     * 
     * @param index specified section to read data from.
     * @return byte array of the read data.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public byte[] read(int index) throws Exception {
        raf.seek(index * secSize);
        byte[] data = new byte[secSize];
        int bytesRead = raf.read(data);

        if (bytesRead < secSize) {
            data = Arrays.copyOf(data, bytesRead);
        }

        return data;
    }

    /**
     * Swaps data between two indices i and j.
     * 
     * @param i indices one.
     * @param j indices two.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public void swap(int i, int j) throws Exception {
        byte[] dataI = read(i);
        byte[] dataJ = read(j);

        write(j, dataI);
        write(i, dataJ);
    }

    /**
     * Searches for the first occurrence of the given byte array target.
     * 
     * @param target byte array to find in the file.
     * @return int of the index. Returns -1 if target is not found in the file.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public int indexOf(byte[] target) throws Exception {
        int sections = size();

        for (int i = 0; i < sections; i++) {
            byte[] currentData = read(i);
            if (Arrays.equals(currentData, target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the file has a specific byte array target by utilizing
     * indexOf(target).
     * 
     * @param target byte array of interest.
     * @return true if array is found, false if not.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public boolean contains(byte[] target) throws Exception {
        return indexOf(target) != -1;
    }

}
