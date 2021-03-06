package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.config.security.AuthUser;
import org.sam.melchor.domain.Account;
import org.sam.melchor.service.AccountService;
import org.sam.melchor.web.payload.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/check_email/{email}")
    public ResponseEntity<?> searchEmail(@PathVariable String email) {
        if (accountService.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyAccount(
            @Valid @RequestBody AccountDto.UpdateRequest request,
            @PathVariable Long id) {
        AccountDto.UpdateResponse accountDto = accountService.update(id, request);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/me")
    @Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
    public ResponseEntity<?> getAuthUser(@AuthUser Account account) {
        AccountDto.SummaryResponse userSummary = accountService.getAccountSummary(account);
        return ResponseEntity.ok(userSummary);
    }

}
