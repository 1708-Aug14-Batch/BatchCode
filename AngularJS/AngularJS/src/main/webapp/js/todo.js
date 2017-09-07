/*
*todo app
*/
var app =angular.module("todoApp", []);

app.controller("todoCtrl", function($scope) {
	$scope.username = "New User";
	
	$scope.items = [
	      {
		action: "Complete P1",
		done: false},
		{action: "watch GOT",
			done:false},
		{action: "Make Dinner",
			done:false}
		];
	$scope.addNewItem = function(action){
		$scope.items.push({
			action: action,
		done:false})
	};
	$scope.imcompleteCount = function(){
		var count = 0;
		angular.forEach($scope.items, function(item){
			if(!item.done){
				count++;
			}
		});
		
	}
});

app.filter("checkedItems", function(){
	return function(items, showComplete){
		var resultArr = [];
		angular.forEach(items, function(item) {
			if(item.done == false || showComplete == true){
				resultArr.push(item);
			}
			
		});
		
		return resultArr;
		
	} 
	
});