angular.module('PetStoreApp.controllers', [])
    .controller('petStoreController', function ($scope, petStoreAPIService) {
        console.debug("Retrieving all pet information in pet store")
        $scope.petStore = [];
        // $scope.searchIDFilter = function(pet) {
        //     var keyword = new RegExp($scope.petFilter, 'i');
        //     return !$scope.petFilter || keyword.test(pet.petName) || keyword.test(pet.category);
        // };

        petStoreAPIService.getPets().success(function (data) {
            $scope.petStore = data;
        });
    })
    .controller('deletePetController',function(petStoreAPIService) {
        var deletePet = function($scope) {
            console.debug("calling pet store service to delete", id);
            petStoreAPIService.deletePet($scope.id);
        }
    });