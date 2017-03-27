'use strict';

/* Services */

var services = angular.module('pinkie.services', ['ngRoute', 'ngResource']);

services.factory('UserFactory', function ($resource) {
	return $resource('/pinkie/rest/users', {}, {
		getUsers: {
			method: 'GET',
			params: {},
			isArray: true
		}
	})
});
