var PWEConfig = {};

(function($) {
	'use strict';
	
	var DEFAULT = {
		mapIsShow : true
	};
	
	PWEConfig = {
		_LOCAL : (function() {
			var config = window.localStorage.getItem("PWE_Config");
			if(!config) {
				config = DEFAULT;
			} else {
				config = $.extend({}, DEFAULT, JSON.parse(config));
			}
			window.localStorage.setItem("PWE_Config", JSON.stringify(config));
			return config;
		})(),
		getLocalConfig : function(key) {
			return this._LOCAL[key];
		},
		setLocalConfig : function(key, value) {
			this._LOCAL[key] = value;
			window.localStorage.setItem("PWE_Config", JSON.stringify(this._LOCAL));
		}
	};
})(jQuery);