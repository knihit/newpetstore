'use strict';

angular.module('PetStoreApp.services', [])
  .factory('petStoreAPIService', function ($http) {
    var endPointUrl = null;

    console.debug("retrieving details of all pets in petstore")
    var petStoreAPI = {};

    petStoreAPI.getPets = function () {
      console.debug("petStoreAPIService->getPets");
      return $http({
        method: 'GET',
        url: 'http://localhost:8080/pet'
      });
    }

    petStoreAPI.deletePet = function (id) {
      console.debug("Making service call to delete pet ", id);
      var url = 'http://localhost:8080/pet/' + id;
      return $http.delete(url);
    }

    petStoreAPI.getPetDetails = function (id) {
      console.debug("Retrieving details of pet ", id);
      return $http({
        method: 'GET',
        url: 'http://localhost:8080/pet/' + id
      })
    }

    petStoreAPI.addPet = function(petToAdd) {
      return $http.post('http://localhost:8080/pet/', petToAdd);
    }
    return petStoreAPI;
  });
