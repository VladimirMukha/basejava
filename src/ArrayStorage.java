import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int element;
    Resume[] storage = new Resume[10000];

    void clear() {
        //  Arrays.fill(storage,0,element,null);
        element = 0;
    }

    void save(Resume r) {
        if (r.uuid != null) {
            storage[element] = r;
            element++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i].uuid = storage[i + 1].uuid;
                element--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, element);
    }

    int size() {
        return element;
    }
}
