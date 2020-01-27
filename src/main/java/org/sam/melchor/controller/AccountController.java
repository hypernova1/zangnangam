package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.payload.LoginRequest;
import org.sam.melchor.payload.LoginResponse;
import org.sam.melchor.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
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
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<Account> result = accountRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        Account account = result.orElseThrow(() -> new AccountNotFoundException(loginRequest.getEmail()));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(account.getEmail());
        loginResponse.setName(account.getName());

        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping
    public ResponseEntity<Account> modifyAccount(@Valid @RequestBody Account account) {

        Account savedAccount = accountRepository.save(account);

        return ResponseEntity.ok(savedAccount);
    }

}
