'use strict';

angular.module('PetStoreApp.controllers', [])
    .controller('petStoreController', function ($location, $scope, petStoreAPIService) {
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
    .controller ('petAddController', function($http, $location, $scope, petStoreAPIService){
        console.debug("petAddController->addPet")

        $scope.categoryDropDown = [];
        $scope.statusDropDown = [];
        $scope.photoCheckBox = [];

        console.debug("petAddController->loading dropdowns")
        $http.get('config/dropdown.json').success(function(data){
            console.debug("Dropdown values ", data);
            $scope.categoryDropDown = data.categoryDropDown;
            $scope.statusDropDown = data.statusDropDown;
            $scope.photoCheckBox = data.petImages;
        });

        $scope.selection = [];
        $scope.toggleSelection = function(petImageName) {
            var index = $scope.selection.indexOf(petImageName);

            if (index > -1) {
                $scope.selection.splice(index, 1);
            } else {
                $scope.selection.push(petImageName);
            }
        }

        $scope.addPet = function(isValid){
            console.debug("petAddController->add");

            var petTags = $scope.pet.tags.split(',');
            var petTagsJSON = new Array();
            for (var i=0; i<petTags.length;++i) {
                var petTagJSON = new Object();
                petTagJSON.tagName = petTags[i];
                petTagsJSON.push(petTagJSON);
            }

            var petToAdd = {
                petName:$scope.pet.petName,
                category:$scope.pet.selectedCategory.value,
                tags:petTagsJSON,
                status:$scope.pet.selectedStatus.value,
                photoUrl:$scope.selection
            }

            console.debug("petAddController->form values->"+petToAdd);

            $http.post('http://localhost:8080/pet/', petToAdd).success(function() {
               console.log("petAddController->addPet->pet added");
               $location.path('/pet/');
            }).error(function(data, status, headers, config){
                console.debug("Add pet failed "+JSON.stringify({data:data}))
            });
        };

        $scope.cancelAdd = function() {
            $location.path('/pet/');
        }
    });