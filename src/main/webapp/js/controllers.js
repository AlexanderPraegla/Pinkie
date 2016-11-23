'use strict';

/* Controllers */


var controllerApp = angular.module('pinkie.controllers', []);


controllerApp.run(function ($rootScope, $templateCache) {
	$rootScope.$on('$viewContentLoaded', function () {
		$templateCache.removeAll();
	});
});

// create the controller and inject Angular's $scope
controllerApp.controller('mainController', ['$scope', 'UserFactory', function ($scope, UserFactory) {
	$scope.message = 'Everyone come and see how good I look!';
	UserFactory.getUsers(function (response) {
		$scope.users = response;
	})
}]);


controllerApp.controller('aboutController', function ($scope) {
	$scope.message = 'Look! I am an about page.';
});

controllerApp.controller('contactController', function ($scope) {
	$scope.message = 'Contact us! JK. This is just a demo.';
});
