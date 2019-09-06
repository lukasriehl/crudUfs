//Controller
var CrudUfApp = angular.module("CrudUfApp", [])
        .value('urlBase', 'http://localhost:8080/crudUFs/rest/')
        .service('UfService', function ($http, urlBase) {
            this.listarUfs = function () {
                return $http({
                    method: 'GET',
                    url: urlBase + 'ufs/'
                });
            };

            this.criar = function (uf) {
                return $http({
                    method: 'POST',
                    url: urlBase + 'ufs/',
                    data: uf
                });
            };

            this.atualizar = function (uf) {
                return $http({
                    method: 'PUT',
                    url: urlBase + 'ufs/' + uf.id + '/',
                    data: uf
                });
            };

            this.excluir = function (id) {
                return $http({
                    method: 'DELETE',
                    url: urlBase + 'ufs/' + id + '/'
                });
            };
        })
        .controller("UFsController", function ($scope, UfService) {
            var self = this;

            self.ufs = [];
            self.uf = undefined;

            self.nova = function () {
                self.uf = {};
            };

            self.salvar = function () {
                var msgErro = undefined;
                var msgSucesso = undefined;
                var salvar = undefined;

                if (self.uf.id) {
                    salvar = UfService.atualizar(self.uf);
                    msgErro = 'Ocorreu um erro ao atualizar a UF!';
                    msgSucesso = 'UF atualizada com sucesso!';
                } else {
                    salvar = UfService.criar(self.uf);
                    msgErro = 'Ocorreu um erro ao salvar a UF!';
                    msgSucesso = 'UF cadastrada com sucesso!';
                }

                salvar.then(function successCallback(response) {
                    self.exibeMensagem(msgSucesso);
                    self.atualizaForm();
                }, function errorCallback(response) {
                    self.exibeMensagem(msgErro);
                });
            };

            self.limpar = function () {
                self.atualizaForm();
            };

            self.alterar = function (uf) {
                self.uf = uf;
            };

            self.excluir = function (uf) {
                UfService.excluir(uf.id).then(function successCallback(response) {
                    self.exibeMensagem("UF excluida com sucesso!");
                    self.atualizaForm();
                }, function errorCallback(response) {
                    self.exibeMensagem("Ocorreu um erro ao excluir a UF!");
                });
            };

            self.listarUfs = function () {
                UfService.listarUfs()
                        .then(function successCallback(response) {
                            self.ufs = response.data;
                            self.uf = undefined;
                        }, function errorCallback(response) {
                            self.exibeMensagem("Ocorreu um erro ao exibir a listagem de UFs!");
                        });
            };

            self.exibeMensagem = function (msg) {
                alert(msg);
            };

            self.resetaValoresForm = function () {
                $scope.crudUfForm.$setPristine();
                $scope.crudUfForm.$setUntouched();

                self.uf = {};
            };

            self.atualizaForm = function () {
                self.listarUfs();
                self.resetaValoresForm();
            };

            self.listarUfs();
        });

CrudUfApp.filter('cmdate', ['$filter', function ($filter) {
        return function (input) {
            if(input === undefined || input === null || input === ''){
                return '';
            }

            var dataConvertida = new Date(input[0], input[1] - 1, input[2],
                    input[3], input[4], input[5]);

            return $filter('date')(dataConvertida, 'dd/MM/yyyy HH:mm:ss');
        };
    }]);