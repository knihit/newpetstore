describe ('PetApplicationSepcController', function () {
	var scope, controller, httpBackend;
	
	beforeEach(module('petStore'));
	
	beforeEach(inject(function($rootScope, $controller, $httpBackend) {
		scope = $rootscope;
		controller = $controller;
		httpBackend = $httpBackend
	}));
	
	it('loads a test controller', function(){
		httpBackend.expectGET('/test').respond('[{"msg":"Test Status"}]');
		controller('PetApplicationSepcController', {'$scope': scope });
		httpBackend.flush();
		scope.$apply(); // probably not needed for this test
		expect(scope.msg).toEqual('Test is successful');
	});
});