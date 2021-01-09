package org.sam.melchor.config.security;

import lombok.Getter;
import org.sam.melchor.domain.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

@Getter
public class MemberAccount extends User {

    private final Account account;

    public MemberAccount(Account account) {
        super(account.getEmail(), account.getPassword(), account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority((role.getName().name()))
                ).collect(Collectors.toList()));

        this.account = account;
    }
}
