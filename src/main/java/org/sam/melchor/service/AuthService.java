package org.sam.melchor.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Role;
import org.sam.melchor.domain.RoleName;
import org.sam.melchor.exception.AppException;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.RoleRepository;
import org.sam.melchor.security.JwtTokenProvider;
import org.sam.melchor.web.payload.AccountDto;
import org.sam.melchor.web.payload.AuthDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AccountRepository accounts;
    private final RoleRepository roles;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public AccountDto.SummaryResponse signUp(AuthDto.@Valid SignUpRequest request) {
        Account account = Account.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Role userRole = roles.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        account.setRoles(Collections.singleton(userRole));

        Account savedAccount = accounts.save(account);
        return modelMapper.map(savedAccount, AccountDto.SummaryResponse.class);
    }

    public String signIn(AuthDto.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generationToken(authentication);
    }
}
