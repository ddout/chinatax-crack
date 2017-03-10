(function() {
	return {
		"iv" : CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex),
		"salt" : CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex)
	}
})();