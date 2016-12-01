(function () {
    'use strict';

    angular
        .module('truckCompanyApp')
        .controller('DriverRoutelistController', DriverRoutelistController);

    DriverRoutelistController.$inject = ['$stateParams', 'Goods1', 'Waybill', 'Checkpoint', 'RouteList', '$http', '$location'];

    function DriverRoutelistController($stateParams, Goods1, Waybill, Checkpoint, RouteList, $http, $location) {
        var vm = this;
        vm.routeList = {};
        vm.checkpoints = [];
        vm.goods1 = [];
        vm.checkpointNames = [];
        vm.imageWaypoints = [];
        vm.waybills = Waybill.query(function () {
            angular.forEach(vm.waybills, function (value) {
                console.log("imageWaypoints: " + vm.imageWaypoints);
                RouteList.get({id: value.routeList.id}, function (result) {
                    vm.routeList = result;
                    console.log(vm.routeList);
                });
                vm.checkpoints = Checkpoint.query({id: value.routeList.id}, function () {
                    var i = 0;
                    angular.forEach(vm.checkpoints, function (value) {
                        vm.checkpointNames[i] = {location: value.name, stopover: true};
                        vm.imageWaypoints[i] = {position: vm.checkpoints[i].name,
                            image: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'};
                        i++;
                        // {location: 'ozarichi', stopover: true}
                    });
                });
                console.log("WaybillID " + value.id);
                vm.goods1 = Goods1.query({id: value.id});


            });
        });


        vm.markDate = markDate;
        vm.travelMode = 'DRIVING';
        function markDate(id) {
            for (var i = 0; vm.checkpoints.length; i++) {
                if (vm.checkpoints[i].id == id) {
                    if (i == 0 || vm.checkpoints[i - 1].checkDate) {
                        var index = i;
                        $http({
                            method: 'GET',
                            url: '/api/checkpoint_mark_date/' + id,
                        }).then(function successCallback(response) {
                            console.log("date changed");
                            var today = new Date();
                            var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
                            var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
                            var dateTime = date + ' ' + time;
                            for (var j in vm.checkpoints) {
                                if (vm.checkpoints[j].id == id) {
                                    vm.checkpoints[j].checkDate = dateTime;
                                    vm.imageWaypoints[j] = {position: vm.checkpoints[j].name,
                                        image: 'http://inspire.ecoachmanager.com/images/32x32/accept_item.png'};
                                }
                            }
                            checkLastCheckpoint(index);

                        });
                    } else {
                        window.alert("Mark previous checkpoint");
                    }

                }
            }


            function checkLastCheckpoint(index) {
                if ((index + 1) === vm.checkpoints.length) {
                    for (var j in vm.waybills) {


                        if(vm.waybills[j] != true) {
                            vm.waybills[j].state = "DELIVERED";
                        }
                        $http({
                            method: 'PUT',
                            url: '/api/waybills',
                            data: vm.waybills[j]
                        })
                    }

                    $location.path('/driver/complete'); // path not hash
                }
            }
        }
        vm.update = function () {
            for (var i in vm.goods1) {

                if(vm.goods1[i] != true) {
                    vm.goods1[i].state = "DELIVERED";
                }
            }
                $http({
                    method: 'PUT',
                    url: '/api/goods',
                    data: vm.goods1
                }).then(function successCallback(response) {
                    console.log("date changed");

                });

            vm.routeList.state = "DELIVERED";
            vm.routeList.arrivalDate = Date.now();
            $http({
                method: 'PUT',
                url: '/api/routelists',
                data: vm.routeList
            });
            $location.path('/driver/routelist');
        };

        // vm.click1 = function (name) {
        //
        // }
    }
})();
