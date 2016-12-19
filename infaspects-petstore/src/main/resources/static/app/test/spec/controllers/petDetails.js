describe('Controller: petDetailsController', function () {
  beforeEach(module('PetStoreApp'));

  var petDetailsController, scope;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend, $routeParams) {
      scope = $rootScope.$new();
      routeParams = $routeParams;
      routeParams.id = "603";
      httpMock = $httpBackend;


      httpMock.expect("http://localhost:8080/pet/603").respond({
        "id": 603,
        "category": "Feline",
        "petName": "Purrfect",
        "photoUrl": ["cat", "cat_1", "cat_2", "cat_3"],
        "tags": [{"id": 496, "tagName": "Toronto"}, {"id": 497, "tagName": "Ontario"}],
        "status": "Available"
      });

      petDetailsController = $controller('petDetailsController', {
        $scope: scope
      });

      httpMock.flush();
    })
  );

/*  it('should retrieve the pets 603', function () {
    expect(scope.pet.petName).toBe("Purrfect");
    expect(scope.pet.category).toBe("Feline")
    expect(scope.pet.photoUrl[0]).toBe("cat");
    expect(scope.pet.photoUrl[1]).toBe("cat_1");
    expect(scope.pet.photoUrl[2]).toBe("cat_2");
    expect(scope.pet.photoUrl[3]).toBe("cat_3");
    expect(scope.pet.tags[0].tagName).toBe("Toronto");
    expect(scope.pet.tags[1].tagName).toBe("Ontario");
    expect(scope.pet.status).toBe("Available");
  });*/
});
