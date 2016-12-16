'use strict';

angular.module('PetStoreApp.controllers', [])
    .controller('petStoreController', function ($scope, petStoreAPIService, $location) {
        console.debug("petStoreController->getPets")
        $scope.petStore = [];
        // $scope.searchIDFilter = function(pet) {
        //     var keyword = new RegExp($scope.petFilter, 'i');
        //     return !$scope.petFilter || keyword.test(pet.petName) || keyword.test(pet.category);
        // };

        console.debug("petStoreController->getPets")
        petStoreAPIService.getPets().success(function (data) {
            $scope.petStore = data;
        });

        $scope.deletePet = function(pet) {
            console.debug("petStoreController->deletePet ",pet.id);
            petStoreAPIService.deletePet(pet.id).success(function () {
                var index=-1;
                var petArr = eval($scope.petStore);

                for (var i = 0; i < petArr.length; ++i) {
                    if (petArr[i].id == pet.id) {
                        index = i;
                        break;
                    }
                }

                if (index > -1) {
                    console.debug("Splicing index ", index);
                    $scope.petStore.splice(index, 1); //update the petStore JSON
                }

                $location.path('/pet');
            });
        };
    })
    .controller ('petDetailsController', function($scope, $routeParams, petStoreAPIService){
        var petId = $routeParams.petId;
        $scope.pet = null;

        console.debug("petStoreController->petDetails ", petId);
        petStoreAPIService.getPetDetails(petId).success(function(data) {
           $scope.pet = data;
        });
    })
    .controller ('petAddController', function($scope, petStoreAPIService){
        console.debug("petAddController->addPet")
    });