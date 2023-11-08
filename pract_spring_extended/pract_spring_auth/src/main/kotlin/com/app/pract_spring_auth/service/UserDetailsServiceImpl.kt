package com.app.pract_spring_auth.service

import com.app.pract_spring_auth.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    val userRepository: UserRepository
): UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val foundUser = username?.let {
            userRepository.findByUsername(it).orElseThrow {
                throw UsernameNotFoundException("User with username $username not found")
            }
        } ?: throw UsernameNotFoundException("The username can't be null")

        return foundUser.toDetails()
    }
}