/**
 * Test controller
 */

var controllers = angular.module('PetStoreApp.controllers', [])
	.controller('PetApplicationSepcController', function($scope) {
		$scope.msg = 'Test is successful';
	});