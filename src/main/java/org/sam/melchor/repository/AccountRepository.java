package org.sam.melchor.repository;

import org.sam.melchor.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AccountRepository extends JpaRepository<Account, Long> {

    Boolean existsByEmail(String email);

    Optional<Account> findByEmailAndPassword(String email, String password);

    Optional<Account> findByEmail(String writer);
}
