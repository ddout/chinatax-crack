(function() {
	var keySize = 128/32;
	var iterationCount = 100;

	var generateKey = function(salt, passPhrase) {
		var key = CryptoJS.PBKDF2(passPhrase, CryptoJS.enc.Hex.parse(salt), {
			keySize : keySize,
			iterations : iterationCount
		});
		return key;
	}

	var encrypt = function(salt, iv, passPhrase, plainText) {
		var key = generateKey(salt, passPhrase);
		var encrypted = CryptoJS.AES.encrypt(plainText, key, {
			iv : CryptoJS.enc.Hex.parse(iv)
		});
		return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
	};

	var decrypt = function(salt, iv, passPhrase, cipherText) {
		var key = generateKey(salt, passPhrase);
		var cipherParams = CryptoJS.lib.CipherParams.create({
			ciphertext : CryptoJS.enc.Base64.parse(cipherText)
		});
		var decrypted = CryptoJS.AES.decrypt(cipherParams, key, {
			iv : CryptoJS.enc.Hex.parse(iv)
		});
		return decrypted.toString(CryptoJS.enc.Utf8);
	}
	
	return {
		"jmbz" : decrypt(jsonData_key8, jsonData_key7, jsonData_key9, jsonData_key4),
		"jmsort" : decrypt(jsonData_key8, jsonData_key7, jsonData_key9, jsonData_key10)
	}
})();