package org.sam.melchor.service;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public void update(Account account, String email) {

    }
}
