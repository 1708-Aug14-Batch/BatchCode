/*
 * AngularJs app
 */

var app = angular.module('myApp',[]);
//var app = angular.module('myC', [])
/*[] tells angular to create a project
with the name passed in
Not providing the [] tells angular
one already exist*/

app.controller('myCtrl', function($scope){
//	$scope.hello = "This is binded to my scope hello";
	$scope.fn = "noon";
	$scope.ln = "noob";
//	$scope.fullName = function(){
//		return $scope.fn +" " +$scope.ln;
		
	//}
	
})