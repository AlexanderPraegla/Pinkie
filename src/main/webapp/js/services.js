'use strict';

/* Services */

var services = angular.module('pinkie.services', ['ngRoute', 'ngResource']);

services.factory('UserFactory', function ($resource) {
	return $resource('/rest/users', {}, {
		query: {
			method: 'GET',
			params: {},
			isArray: false
		}
	})
});
