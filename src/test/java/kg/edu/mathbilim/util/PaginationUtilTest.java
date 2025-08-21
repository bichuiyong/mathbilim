package kg.edu.mathbilim.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationUtilTest {

    @Test
    void createPageableWithSort_handlesNulls() {
        Pageable pageable = PaginationUtil.createPageableWithSort(1, 10, null, null);
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        Sort.Order order = pageable.getSort().getOrderFor("id");
        assertNotNull(order);
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }

    @Test
    void createPageableWithSort_usesAscendingWhenRequested() {
        Pageable pageable = PaginationUtil.createPageableWithSort(2, 5, "name", "asc");
        assertEquals(1, pageable.getPageNumber());
        Sort.Order order = pageable.getSort().getOrderFor("name");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }
}
