/**
 * AppService class acts as the central coordinator, managing the retrieval,
 * serialization, and storage of jokes into files, ensuring content is
 * categorized and no duplicated are stored, and the UI receives all data needed
 * for user interaction.
 * 
 * @author rachelcurci
 */
public class AppService extends FileManager {
    private static final long serialVersionUID = 1L;
    public static int index = 0;

    /**
     * Constructor, extends FileManager. Sets up the environment for managing
     * jokes.
     * 
     * @param path file path.
     * @param size size of the section for the jokes.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public AppService(String path, int size) throws Exception {
        super(path, size);
    }

    /**
     * Fetches a single joke from the given category and uses that to create a
     * Joke object with and returns it.
     * 
     * @param category category of joke that will be selected.
     * @return
     */
    public Joke getJoke(String category) {
        String jokeText = JokeFetcher.fetchJoke(category);

        return new Joke(category, jokeText);
    }

    /**
     * Gets a joke and stores in file while avoiding duplicate jokes.
     * 
     * @param category category of joke that will be stored.
     * @throws Exception for IOException and FileNotFoundException.
     */
    public void saveJoke(String category) throws Exception {
        Joke joke;
        byte[] jokeBytes;

        while (true) {
            joke = getJoke(category);
            jokeBytes = joke.getBytes(getSecSize());

            if (!contains(jokeBytes)) {
                break;
            }
        }
        write(index, jokeBytes);
        if (index == 10) {
            index = 0;
        } else {
            index += 1;
        }
    }
}
