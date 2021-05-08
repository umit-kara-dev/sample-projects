package com.delivery.service.book.service;

import java.security.MessageDigest;

import javax.annotation.PostConstruct; 

import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.salt.FixedSaltGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.delivery.service.book.exception.ResultCodeEnum;
import com.delivery.service.book.exception.RigException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EncryptService {

	private StringEncryptor encryptor;

	@Value("${salt.key}")
	private String saltKey;

	@Value("${hash.salt.key}")
	private String hashSalt;

	@Value("${jasypt.encryptor.password}")
	private String jasyptPass;

	public StringEncryptor getEncryptor() {
		return encryptor;
	}

	public void setEncryptor(StringEncryptor encryptor) {
		this.encryptor = encryptor;
	}

	/**
	 * main encryptor of project. using PBEWithMD5AndTripleDES algorithm
	 * @return
	 */
	@Bean(name = "rigEncryptor")
	public PooledPBEStringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(jasyptPass);
		config.setAlgorithm("PBEWithMD5AndTripleDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGenerator(getGenerator());
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		return encryptor;
	}

	@PostConstruct
	public void initEncryptor() {
		setEncryptor(stringEncryptor());
	}

	public String encrypt(String text) {
		return getEncryptor().encrypt(text);
	}

	public String decrypt(String text) {
		return getEncryptor().decrypt(text);
	}

	public FixedSaltGenerator getGenerator() {
		return new StringFixedSaltGenerator(saltKey);
	}

	public String hash(String text) throws RigException {
		return hashWithSalt(text, hashSalt);
	}

	/**
	 * use salt and SHA-512 to hash messages
	 * @param text
	 * @param salt
	 * @return
	 * @throws RigException
	 */
	public String hashWithSalt(String text, String salt) throws RigException {
		try {
			String hashedStr = null;
			if (text != null) {
				MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
				sha512.update(salt.getBytes());
				byte[] passBytes = text.getBytes();
				byte[] passHash = sha512.digest(passBytes);
				hashedStr = Base64.encodeBase64String(passHash);
			}
			return hashedStr;
		} catch (Exception e) {
			log.error("hashing error = {}", e);
			throw new RigException(ResultCodeEnum.SYSTEM_ERROR);
		}
	}

	public String encryptAndHash(String text) throws RigException {
		String encryptedText = encrypt(text);
		return hash(encryptedText);
	}

}
