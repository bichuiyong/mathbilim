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
        String sortField = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;

        Sort.Direction direction = Sort.Direction.DESC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        int pageIndex = Math.max(page - 1, 0);

        return PageRequest.of(pageIndex, size, Sort.by(direction, sortField));
    }

    public static <E, D> Page<D> getPage(Supplier<Page<E>> supplier,
                                         Function<E, D> mapper,
                                         String notFoundMessage) {
        Page<E> page = supplier.get();
        if(page == null) throw new IllegalArgumentException();
        if (page.isEmpty()) {
            return Page.empty(page.getPageable());
        }
        return page.map(mapper);
    }


    public static  <E, D> Page<D> getPage(Supplier<Page<E>> supplier, Function<E, D> mapper) {
        return getPage(supplier, mapper, "Данные не найдены");
    }
}
