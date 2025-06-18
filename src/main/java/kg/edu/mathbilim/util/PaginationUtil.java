package kg.edu.mathbilim.util;

import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class PaginationUtil {

    public Pageable createPageableWithSort(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy.toLowerCase()));
    }

    public static   <E, D> Page<D> getPage(Supplier<Page<E>> supplier,
                                   Function<E, D> mapper,
                                   String notFoundMessage) {
        Page<E> page = supplier.get();
        if (page.isEmpty()) {
            throw new FileNotFoundException(notFoundMessage);
        }
        return page.map(mapper);
    }

    public static  <E, D> Page<D> getPage(Supplier<Page<E>> supplier, Function<E, D> mapper) {
        return getPage(supplier, mapper, "Данные не найдены");
    }
}
