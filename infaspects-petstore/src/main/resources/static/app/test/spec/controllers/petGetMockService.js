describe('Controller: petStoreController', function () {
  var petStoreController, scope;

  beforeEach(module('PetStoreApp'), function($provide){
    petStoreAPIService = {
      fn: function () {
      }
    }

    spyOn(petStoreAPIService, 'fn').and.returnValue({
      "id": 602,
      "category": "Canine",
      "petName": "Buster",
      "photoUrl": ["dog", "dog_1", "dog_2"],
      "tags": [{"id": 496, "tagName": "Toronto"}, {"id": 497, "tagName": "Ontario"}],
      "status": "Sold"
    }, {
      "id": 603,
      "category": "Feline",
      "petName": "Purrfect",
      "photoUrl": ["cat", "cat_1", "cat_2", "cat_3"],
      "tags": [{"id": 496, "tagName": "Toronto"}, {"id": 497, "tagName": "Ontario"}],
      "status": "Available"
    }, {
      "id": 604,
      "category": "Feline",
      "petName": "King!",
      "photoUrl": ["Lion", "Lion_1", "Lion_2", "Lion_3"],
      "tags": [{"id": 496, "tagName": "Toronto"}, {"id": 497, "tagName": "Ontario"}],
      "status": "Pending"
    });

    $provide.value('petStoreAPIService', petStoreAPIService);

  });

  beforeEach(inject(function ($controller, $rootScope,  petStoreAPIService) {
    scope = $rootScope.$new();
    petStoreController = $controller('petStoreController', {$scope: scope, petStoreAPIService: petStoreAPIService});

  }));

  it('should return a list with three pets', function () {
    expect(scope.petStore.length).toBe(3);
  });

/*  it('should retrieve the pets in the petstore', function () {
    expect(scope.petStore[0].petName).toBe("Buster");
    expect(scope.petStore[0].category).toBe("Canine")
    expect(scope.petStore[1].petName).toBe("Purrfect");
    expect(scope.petStore[1].category).toBe("Feline")
    expect(scope.petStore[2].petName).toBe("King!");
    expect(scope.petStore[2].category).toBe("Feline");
  });*/
});
