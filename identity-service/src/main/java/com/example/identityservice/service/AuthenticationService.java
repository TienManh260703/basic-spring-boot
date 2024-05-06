package com.example.identityservice.service;

import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.dto.response.AuthenticationResponse;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;
    @NonFinal // Ko inject vào contructer
    protected final String SIGNER_KEY = "rBBLzYFFLy0wIV1QIsWei0htU8u9dG6mMSxchJMVh2pCP97e9sDqokmN4qJTChZM";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)); // tìm || ko có đẩy lỗi qua exception xử lý với enumKey
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUserName());
        return  AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(String username) {
        // KEY : rBBLzYFFLy0wIV1QIsWei0htU8u9dG6mMSxchJMVh2pCP97e9sDqokmN4qJTChZM
        // create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // Xây dựng payload
        JWTClaimsSet jwtClaimNames = new JWTClaimsSet.Builder()
                .subject(username)// Đại diện cho USER đang đăng nhập
                .issuer("manh")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .claim("customClaim", "Custom")// thời hạn tồn tại
                .build();
        Payload payload = new Payload(jwtClaimNames.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);// Chuyền vào header và payload
        // build xong token
        // Ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
