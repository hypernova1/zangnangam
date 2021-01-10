package org.sam.melchor.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sam.melchor.domain.Account;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.web.payload.AccountDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accounts;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return accounts.existsByEmail(email);
    }

    @Transactional
    public AccountDto.UpdateResponse update(Long id, AccountDto.@Valid UpdateRequest request) {
        Account account = accounts.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        account.update(request);
        return modelMapper.map(account, AccountDto.UpdateResponse.class);
    }

    public AccountDto.SummaryResponse getAccountSummary(Account account) {
        AccountDto.SummaryResponse userSummary = modelMapper.map(account, AccountDto.SummaryResponse.class);
        userSummary.setRole(account.isAdmin() ? "admin" : "user");
        return userSummary;
    }
}
