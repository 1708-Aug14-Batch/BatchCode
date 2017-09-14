/**
 * 
 */

var app = angular.module("todoApp", []);

app.controller("todoCtrl", function($scope) {
	$scope.username = "";
	$scope.items = [ {
			action : "Complete Project 1",
			done : false
		}, {
			action : "Watch favorite movie",
			done : false
		}, {
			action : "Practice programming",
			done : false
		}
	];

	$scope.addNewItemTop = function(newAction) {
		$scope.items.unshift({
			action : newAction,
			done : false
		});
	}
	$scope.addNewItemBottom = function(newAction) {
		$scope.items.push({
			action : newAction,
			done : false
		});
	}
	
	$scope.countIncomplete = function() {
		var result = 0;
		
		angular.forEach($scope.items, function(item) {
			if (item.done==false)
				result++;
		})
		
		return result;
	}
});

app.filter("checkedItems", function() {
	return function(items, showComplete) {
		var resultArray = [];
		
		angular.forEach(items, function(item) {
			if(item.done==false || showComplete == true) {
				resultArray.push(item);
			}
		})
		
		return resultArray;
	}
})