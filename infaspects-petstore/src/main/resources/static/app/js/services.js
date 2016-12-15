angular.module('PetStoreApp.services',[])
	.factory('petStoreAPIService', function($http){
		console.debug("retrieving details of all pets in petstore")
		var petStoreAPIService = {};

		petStoreAPIService.getPets = function() {
			return $http({
				method: 'GET',
				url: 'http://localhost:8080/pet'
			});
		}

        petStoreAPIService.deletePet = function(id) {
			console.debug("Making service call to delete pet ",id);
			return $http({
				method: 'DELETE',
				url: 'http://localhost:8080/pet/'+id
			})
		}

		petStoreAPIService.getPetDetails = function(id) {
			console.debug("Retrieving details of pet ", id);
			return $http({
				method: 'GET',
				url: 'http://localhost:8080/pet/'+id
			})
		}
		return petStoreAPIService;
	});