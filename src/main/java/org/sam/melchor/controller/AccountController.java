package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.payload.LoginRequest;
import org.sam.melchor.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AccountController {

    private AccountRepository accountRepository;

    @GetMapping("/check/{email}")
    public ResponseEntity<Boolean> searchEmail(@PathVariable String email) {
        if (accountRepository.existsByEmail(email)) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody Account account) {
        accountRepository.save(account);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/signin")
    public ResponseEntity<Boolean> signin(@Valid @RequestBody LoginRequest loginRequest) {

        Boolean result = accountRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Account> modifyAccount(@Valid @RequestBody Account account) {

        accountRepository.save(account);

        return ResponseEntity.ok(account);
    }

}
