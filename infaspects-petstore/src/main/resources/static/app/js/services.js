angular.module('PetStoreApp.services',[])
	.factory('petStoreAPIService', function($http){
		var petStoreAPIService = {};

		petStoreAPIService.getPets = function() {
			return $http({
				method: 'GET',
				url: 'http://localhost:8080/pet'
			});
		}

        petStoreAPIService.deletePets = function(id) {
			$http({
				method: 'DELETE',
				url: 'http://localhost:8080/pet/:id'
			})
		}

		petStoreAPIService.getPetDetails = function(id) {
			$http({
				method: 'GET',
				url: 'http://localhost:8080/pet/:id'
			})
		}
		return petStoreAPIService;
	});