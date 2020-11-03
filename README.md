# Desafio Técnico

Este projeto foi implementado como um desafio técnico para uma vaga de desenvolvedor Java, para mais detalhes acesse [Desafo Técnico](https://github.com/softplan/softplayer-java-apply "Seleção Dev Java")

Para este projeto foram desenvolvidas duas soluções, 1 backend Java e 1 frontend Angular, ambos rodando em um ambiente docker.

### Backend Java

Para o backend Java foi utilizado 
* Java 11;
* Framework spring-boot, com spring-data, spring-validators e spring-security com autentiação oauth2 com jwt;
* Flyway para controle e versionamento dos scripts do banco de dados;
* Banco de dados postgres;
* Biblioteca lombok
* Grrovy para consultas SQL
* E JUnit e Spring Boot Starter Test para os testes de integração.

No desenvolvimento da aplicação foi utilizado o modelo de maturidade de Richardson e conceitos de clean code e clean architecture para que o resultado fosse uma boa aplicação, com um bom código e de fácil manutenção.

### Frontend Angular

Para o frontend end foi utilizado
* Angular 6;
* E as bibliotecas bootstrap, ng-bootstrap e ngx-mask;

### Docker Compose

A aplicação roda em um ambiente docker com 4 vms, uma java, uma angular, uma postgres e um nginx para comunicação entre back e front.

Para iniciar o ambiente basta baixar o projeto, acessar a pasta docker-compose e usar o comando
```
docker-compose up -d
``` 
Ou se preferir start.sh já configurado
```
./start.sh
``` 

### A aplicação

Após subir os serviços docker para acessar a aplicação basta acessar no navegador http://localhost:81 e acessar com o usuário `admin` e senha `admin@123`

![alt text](https://github.com/gilsomanfredi/desafio-tecnico-softplan/blob/imagens/Login.png "Tela de Login")

Após o login será redirecionado para a tela de listagem, e por ela também poderá visualizar as duas versões do cadastro de pessoas da, como solicitado no desafio.

![alt text](https://github.com/gilsomanfredi/desafio-tecnico-softplan/blob/imagens/Cadastro.png "Tela de Listagem")

### Testes de Integração

A aplicação está sendo testada com testes de integração utilizando o Spring Boot Tests e Junit, atualmente com 61 casos de teste que resultam em uma convergência de 100% das classes testadas e 95% das linhas de código.

![alt text](https://github.com/gilsomanfredi/desafio-tecnico-softplan/blob/imagens/Convergencia%20dos%20testes%20de%20integra%C3%A7%C3%A3o.png "Convergencia dos testes de integração")

![alt text](https://github.com/gilsomanfredi/desafio-tecnico-softplan/blob/imagens/Testes.png "Testes de integração")

