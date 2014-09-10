var ImageGallery = function() {
};

// Call this to register for push notifications. Content of [options] depends on whether we are working with APNS (iOS) or GCM (Android)
ImageGallery.prototype.echo = function(successCallback, errorCallback, options) {
    if (errorCallback == null) { errorCallback = function() {}}

    if (typeof errorCallback != "function")  {
        console.log("imageGallery.echo failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("imageGallery.echo failure: success callback parameter must be a function");
        return
    }

    cordova.exec(successCallback, errorCallback, "Gallery", "echo", []);
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.imageGallery) {
    window.plugins.imageGallery = new ImageGallery();
}

if (typeof module != 'undefined' && module.exports) {
  module.exports = ImageGallery;
}
