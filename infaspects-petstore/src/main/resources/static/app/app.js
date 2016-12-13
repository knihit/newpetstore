'use strict';

angular.module('PetStoreApp', ['ngRoute'])
	.config(['routeProvider'], function($routeProvider){
		$routeProvider
			.when('/', {
				templateUrl:'pet/pet.html',
				controller:'PetController'
			})
			.otherwise({redirectTo: '/pet'});
});