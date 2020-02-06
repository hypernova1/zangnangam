package org.sam.melchor.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.payload.UserSummary;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.security.AuthUser;
import org.sam.melchor.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("/check_email/{email}")
    public ResponseEntity<Boolean> searchEmail(@PathVariable String email) {
        if (accountRepository.existsByEmail(email)) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Account> modifyAccount(@Valid @RequestBody Account account,
                                                 @PathVariable String email) {

        account.setEmail(email);
        Account savedAccount = accountRepository.save(account);

        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping("/me")
    @Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
    public ResponseEntity<UserSummary> getAuthUser(@AuthUser UserPrincipal authUser) {
        UserSummary userSummary = new UserSummary(authUser.getId(), authUser.getEmail(), authUser.getUsername());
        return ResponseEntity.ok(userSummary);
    }

}
