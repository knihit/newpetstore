angular.module('PetStoreApp.services',[])
	.factory('petStoreAPIService', function($http){
		var petStoreAPIService = {};

		petStoreAPIService.getPets = function() {
			return $http({
				method: 'JSONP',
				url: 'http://localhost:8090/pet'
			});
		}

		return petStoreAPIService;

	});