//Controller
angular.module("CrudUfApp", [])
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
        .controller("UFsController", function (UfService) {
            var self = this;

            self.usuario = 'Lukas';

            self.ufs = [];
            self.uf = undefined;
            
            self.exibeBotaoNovo = true;

            self.nova = function () {
                self.uf = {};
                self.exibeBotaoNovo = false;
            };

            self.salvar = function () {
                var msgErro = undefined;
                var salvar = undefined;

                if (self.uf.id) {
                    salvar = UfService.atualizar(self.uf);
                    msgErro = 'Ocorreu um erro ao atualizar a UF!';
                } else {
                    salvar = UfService.criar(self.uf);
                    msgErro = 'Ocorreu um erro ao salvar a UF!';
                }

                salvar.then(function successCallback(response) {
                    self.listarUfs();
                    self.exibeBotaoNovo = true;
                }, function errorCallback(response) {
                    self.exibeMensagemErro(msgErro);
                });
            };
            
            self.cancelar = function () {
                self.uf = undefined;
                self.exibeBotaoNovo = true;
            };

            self.alterar = function (uf) {
                this.uf = uf;
            };

            self.excluir = function (uf) {
                UfService.excluir(uf.id).then(function successCallback(response) {
                    self.listarUfs();
                }, function errorCallback(response) {
                    self.exibeMensagemErro("Ocorreu um erro ao excluir a UF!");
                });
            };

            self.listarUfs = function () {
                UfService.listarUfs()
                        .then(function successCallback(response) {
                            self.ufs = response.data;
                            self.uf = undefined;
                        }, function errorCallback(response) {
                            self.exibeMensagemErro("Ocorreu um erro ao exibir a listagem de UFs!");
                        });
            };

            self.exibeMensagemErro = function (msgErro) {
                alert(msgErro);
            };

            self.listarUfs();
        });