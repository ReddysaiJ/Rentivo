package com.rental.demo.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public String generateOTP(String key) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        otpStorage.put(key, otp);
        return otp;
    }

    public boolean validateOTP(String key, String otp) {
        String storedOtp = otpStorage.get(key);
        return storedOtp != null && storedOtp.equals(otp);
    }
}
