'use strict';

angular.module('PetStoreApp', [
  'PetStoreApp.controllers',
  'PetStoreApp.services',
  'ngRoute'
]).config(function ($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: 'pet/petStore.html',
      controller: 'petStoreController'
    }).when('/pet', {
    templateUrl: 'pet/petStore.html',
    controller: 'petStoreController'
  }).when('/pet/:petId', {
    templateUrl: 'pet/petDetails.html',
    controller: 'petDetailsController'
  }).when('/add', {
    templateUrl: 'pet/petAdd.html',
    controller: 'petAddController'
  }).otherwise({redirectTo: '/pet'});
});
