<h2>Cadastro de UFs</h2>
<br>
<p>Aplicação de cadastro de Unidades Federativas.
As Unidades Federativas apresentam código, sigla, descrição e data de criação
e de modificação.</p>
<br>
<h4>1 - Tecnologias utilizadas no desenvolvimento:</h4>
<p>Desenvolvida em Java EE 7, com front-end em Angular 1.7. 
Também foi utilizado o CSS do Bootstrap e um criado para alguns ajustes pontuais;</p>
<br>
<h4>2 - Banco de dados:</h4>
<p>O banco de dados utilizado para o teste da aplicação é o MySQL.
Para o mapeamento da tabela e persistência no banco de dados foi utilizado o Hibernate.
Os scripts para criação/exclusão do esquema e tabela criados está anexado ao projeto, no caminho:
<b>Outros Códigos-fonte >> SQL</b>;</p>
<br>
<h4>3 - Servidores de aplicação utilizados nos testes:</h4>
<p>Para efetuar os testes deste CRUD foram utilizados os servidores Glassfish v.5 e 
Apache Tomcat v.8.5.45. Em <b>Outros Códigos-fonte >> tomcat</b> foram anexados 3 arquivos, caso seja utilizado o Tomcat:</p>
<p>(1) apache-tomcat-8.5.45.zip: binários do Apache Tomcat;</p>
<p>(2) config.sh: script para configurar o usuário do Tomcat (tomcat). É utilizado em conjunto
com o deploy.sh;</p>
<p>(3) deploy: script para implantação do compilado da aplicação (WAR). Se for utilizar este arquivo,
então é necessário executar antes o config.sh. Também é necessário editar esse arquivo, modificando o valor
da variável tomcatHome para o local onde foi extraído o apache-tomcat-8.5.45;</p>
<p>Observação: em Outros Códigos-fonte >> SQL há o arquivo mysql-connector-java-5.1.29.jar. Caso seja utilizado o Glassfish,
então copiar este arquivo para a pasta glassfish >> lib;</p>
<br>
<h4>4 - Testes unitários:</h4>
<p>Foram criadas duas classes para os testes unitários. Uma delas para o teste de regras de negócio do CRUD e
outra para o testes das requisições criadas em REST. Ambas utilizando o Mockito para bloquear algumas operações</p>
