'use strict';

angular.module('pet',['ngRoute']).config(['$routeProvider',function($routeProvider){
    $routeProvider
        .when('/pet', {
            templateUrl:'pet/pet.html',
            controller:'PetController'
    })
}]).controller('PetController',['$scope','$http','$location',function($scope,$http,$location){
    $http.get('http://localhost:8090/pet/').success(function(data){
        $scope.petStoreData = data;
    });
}]);