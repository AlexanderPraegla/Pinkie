'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('app', ['ngRoute', 'pinkie.filters', 'pinkie.services', 'pinkie.directives', 'pinkie.controllers']);

// configure our routes
app.config(function ($routeProvider) {
	$routeProvider

	// route for the home page
		.when('/', {
			templateUrl: 'pages/home.html',
			controller: 'mainController'
		})

		// route for the about page
		.when('/about', {
			templateUrl: 'pages/about.html',
			controller: 'aboutController'
		})

		// route for the contact page
		.when('/contact', {
			templateUrl: 'pages/contact.html',
			controller: 'contactController'
		});

});
