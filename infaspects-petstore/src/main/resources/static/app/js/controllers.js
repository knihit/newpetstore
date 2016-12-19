'use strict';

angular.module('PetStoreApp.controllers', [])
  .controller('petStoreController', function ($location, $scope, petStoreAPIService) {
    console.debug("petStoreController");
    $scope.petStore = [];
    // $scope.searchIDFilter = function(pet) {
    //     var keyword = new RegExp($scope.petFilter, 'i');
    //     return !$scope.petFilter || keyword.test(pet.petName) || keyword.test(pet.category);
    // };

    petStoreAPIService.getPets().then(function successCallback(response) {
      console.debug("petStoreController->getPets, "+response.data);
      $scope.petStore = response.data;
    }, function errorCallback(response) {
      console.debug("petStoreController->getPets failed "+response.data);
      }
    );

    $scope.deletePet = function (pet) {
      console.debug("petStoreController->deletePet ", pet.id);
      petStoreAPIService.deletePet(pet.id).then(function successCallback() {
        var index = -1;
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
  .controller('petDetailsController', function ($routeParams, $scope, petStoreAPIService) {
    var petId = $routeParams.petId;
    $scope.pet = null;

    console.debug("petStoreController->petDetails ", petId);
    petStoreAPIService.getPetDetails(petId).then(function successCallback(response) {
      $scope.pet = response.data;
    });
  })
  .controller('petAddController', function ($http, $location, $scope, petStoreAPIService) {
    console.debug("petAddController->addPet")

    $scope.categoryDropDown = [];
    $scope.statusDropDown = [];
    $scope.photoCheckBox = [];

    console.debug("petAddController->loading dropdowns")
    $http.get('config/dropdown.json').then(function successCallback(response) {
      console.debug("Dropdown values ", response.data);
      $scope.categoryDropDown = response.data.categoryDropDown;
      $scope.statusDropDown = response.data.statusDropDown;
      $scope.photoCheckBox = response.data.petImages;
    });

    $scope.selection = [];
    $scope.toggleSelection = function (petImageName) {
      var index = $scope.selection.indexOf(petImageName);

      if (index > -1) {
        $scope.selection.splice(index, 1);
      } else {
        $scope.selection.push(petImageName);
      }
    }

    $scope.addPet = function () {
      console.debug("petAddController->add");

      var petTags = $scope.pet.tags.split(',');
      var petTagsJSON = new Array();
      for (var i = 0; i < petTags.length; ++i) {
        var petTagJSON = new Object();
        petTagJSON.tagName = petTags[i];
        petTagsJSON.push(petTagJSON);
      }

      var petToAdd = {
        petName: $scope.pet.petName,
        category: $scope.pet.selectedCategory.value,
        tags: petTagsJSON,
        status: $scope.pet.selectedStatus.value,
        photoUrl: $scope.selection
      }

      console.debug("petAddController->form values->" + petToAdd);

      petStoreAPIService.addPet(petToAdd);
      $location.path('/pet/');
    };

    $scope.cancelAdd = function () {
      $location.path('/pet/');
    }
  });
