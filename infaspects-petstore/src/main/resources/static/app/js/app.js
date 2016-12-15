angular.module('PetStoreApp',[
	'PetStoreApp.controllers',
    'PetStoreApp.services',
    'ngRoute'
]).config(['$routeProvider', function ($routeProvider) {
    console.log("inside router config");
    $routeProvider
        .when("/app/pet", {templateUrl: "partials/petStore.html", controller: "petStoreController"})
        .when("/app/pet/:id", {templateUrl: "partials/pet.html", controller: "petController"})
        .otherwise({redirectTo: '/index.html'});
}]);