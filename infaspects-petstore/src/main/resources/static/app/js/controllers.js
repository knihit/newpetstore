angular.module('PetStoreApp.controllers', [])
    .controller('petStoreController', function ($scope, petStoreAPIService) {
        $scope.petStore = [];

        petStoreAPIService.getPets().success(function (data) {
            $scope.petStore = data;
        });
    });