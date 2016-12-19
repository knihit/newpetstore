describe('Infaspects PetStore App', function() {
  it('should have a title', function() {
    browser.get('http://localhost:8080/app/index.html');

    expect(browser.getTitle()).toEqual('Infaspects Pet Store');
  });
});
