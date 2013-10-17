/**
 * @version Sep 21, 2013
 * @param <Key>
 * @param <E>
 */
public interface DictionaryInterface<Key, E> {
    /**
     * Reinitialize dictionary.
     */
    public void clear();

    /**
     * Insert a record.
     *
     * @param key
     * @param element
     */
    public void insert(Key key, E element);

    /**
     * Remove and return a record.
     *
     * @param key
     *            Key of record to be removed.
     * @return The matching record; otherwise return null.
     */
    public E remove(Key key);

    /**
     * @return Remove and return a random record.
     */
    public E removeRandomElement();

    /**
     * @param key
     * @return An record with key value matching parameter key.
     */
    public E find(Key key);

    /**
     * @return Number of records in dictionary.
     */
    public int size();
}
