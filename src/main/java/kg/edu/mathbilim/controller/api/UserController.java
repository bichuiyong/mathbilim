package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.dto.UserEditByAdminDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restUser")
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUserPage(@RequestParam(required = false, defaultValue = "1") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false) String query,
                                                     @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                     @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(userService.getUserPage(query, page, size, sortBy, sortDirection));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ofNullable(userService.getDtoById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        userService.toggleUserBlocking(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserEditByAdminDto userDto, @PathVariable Long id) {
        userService.updateUser(userDto, id);
        return ResponseEntity.ok().build();
    }
}
