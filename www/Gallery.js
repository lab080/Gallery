// Returns a jQuery or AngularJS deferred object, or pass a success and fail callbacks if you don't want to use jQuery or AngularJS
var imageGallery = function (success, fail) {
  // 5th param is NOT optional. must be at least empty array
  cordova.exec(success, fail, "gallery", "echo", []);
  return dfr;
};

module.exports = imageGallery;
