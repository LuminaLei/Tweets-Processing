JustGage.prototype.refreshMax = function(val) {
	this.config.max = val;
	this.canvas.getById(this.config.id + "-txtmax").attr({
		"text" : val
	});

};

JustGage.prototype.refreshMin = function(val) {
	this.config.min = val;
	this.canvas.getById(this.config.id + "-txtmin").attr({
		"text" : val
	});
};

var percentColorsSentiment = [ "#ff0000", "#a9d70b", "#f9c802" ];