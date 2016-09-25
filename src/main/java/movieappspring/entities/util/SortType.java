package movieappspring.entities.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class holding <code>ArrayList</code> with allowed sorting types.
 */
public class SortType {

    public static final List<String> SORT_TYPES = new ArrayList<>(
            Arrays.asList("id", "login", "username", "isadmin", "isbanned"));

}
