/**
 * Created by Vladimir on 30.10.2016.
 */
(function () {
    'use strict';

    angular
        .module('truckCompanyApp')
        .controller('AdmincompanyTruckController', AdmincompanyTruckController);

    AdmincompanyTruckController.$inject = ['$stateParams', '$state', 'Company', 'Upload', '$http'];

    function AdmincompanyTruckController($stateParams, $state, Company, Upload, $http) {
        var vm = this;

        vm.load = load;
        vm.update = update;
        vm.truck = {};
        vm.error = false;
        vm.messageError = '';

        vm.load($stateParams.id);


        function load(id) {
            console.log('AdmincompanyTruckController');
            $http({
                method: 'GET',
                url: '/api/trucks/' + id,
            }).then(function successCallback(response) {
                vm.error = false;
                vm.truck = response.data;
                console.log(vm.truck);
            }, function errorCallback(response) {
                vm.error = true;
                vm.messageError = "Problem with connection."
                console.log("ERROR GET STORAGE")
            });
        }

        function update(){
            console.log("UPDATE TRUCK")
            $http({
                method: 'PUT',
                url: '/api/trucks',
                data: vm.truck
            }).then(function successCallback(response) {
                vm.error = false;
                vm.truck = response.data;
                console.log(vm.truck);
                $state.go('admincompany.trucks');
            }, function errorCallback(response) {
                vm.error = true;
                vm.messageError = "Problem with update."
                console.log("ERROR GET STORAGE")
            });
        }


    }
})();
