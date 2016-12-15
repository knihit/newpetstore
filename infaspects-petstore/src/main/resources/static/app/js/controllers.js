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
    .controller('deletePetController',function($scope, petStoreAPIService) {
        $scope.deletePet = (function(pet) {
            console.debug("In controller to delete ",pet.id);
            petStoreAPIService.deletePet(pet.id);
                /*.success(function () {
                petStoreAPIService.getPets().success(function (data) {
                    $scope.petStore = data;
                });
            });*/
        });
    });