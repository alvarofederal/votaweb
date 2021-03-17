# VOTAWEB

## API para VOTACÃO de Pautas por Associados

#### CONFIGURACÃO HEROKU
A API de back-end do Desafio Técnico Back End
se encontra na URL https://votaweb.herokuapp.com/

#### URL'S DA API
API's com SWAGGER no Heroku: https://votaweb.herokuapp.com/swagger-ui.html

API's com SWAGGER localhost: https://localhost:8080/swagger-ui.html

#### URL'S DA DAS FUNCIONALIDADES DA API
########################SESSÕES########################
https://votaweb.herokuapp.com/api/v1/sessoes 

https://votaweb.herokuapp.com/api/v1/sessoes/{id} 

https://votaweb.herokuapp.com/api/v1/sessoes/nova-sessao 

https://votaweb.herokuapp.com/api/v1/sessoes/nova-sessao/{tempoSessao} 


https://votaweb.herokuapp.com/api/v2/sessoes 

https://votaweb.herokuapp.com/api/v2/sessoes/{id} 


########################VOTAÇÕES#######################
https://votaweb.herokuapp.com/api/v1/votacoes 

https://votaweb.herokuapp.com/api/v1/votacoes/{id} 


#######################ASSOCIADOS#######################
https://votaweb.herokuapp.com/api/v1/associados 

https://votaweb.herokuapp.com/api/v1/associados/associado 

https://votaweb.herokuapp.com/api/v1/associados/{id} 


###########################PAUTAS#######################
https://votaweb.herokuapp.com/api/v1/pautas 

https://votaweb.herokuapp.com/api/v1/pautas/pauta 

https://votaweb.herokuapp.com/api/v1/pautas/{id} 


##########################VOTO##########################
https://votaweb.herokuapp.com/api/v1/votacoes/pauta/{pautaId}/associado/{associadoId}/votar/{voto} 



#### DESCRIÇÃO DO DESAFIO E INSTRUÇÕES PARA A EXECUÇÃO 
Essa API é responsável para efetuar Votos de Associados válidos em uma Pauta.

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução backend para gerenciar essas sessões de votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

#### Cadastrar uma nova pauta 
Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default) 

Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta) 

Contabilizar os votos e dar o resultado da votação na pauta 


#### Tarefa Bônus 1 - Integração com sistemas externos 
Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar 
GET https://user-info.herokuapp.com/users/{cpf} 

Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos; 

Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação Exemplos de retorno do serviço.  


#### Tarefa Bônus 2 - Mensageria e filas 
Classificação da informação: Uso Interno O resultado da votação precisa ser informado para o restante da plataforma, isso deve ser feito preferencialmente através de mensageria. Quando a sessão de votação fechar, poste uma mensagem com o resultado da votação. 


#### Tarefa Bônus 3 - Performance 
Imagine que sua aplicação possa ser usada em cenários que existam centenas de milhares de votos. Ela deve se comportar de maneira performática nesses cenários; 
Testes de performance são uma boa maneira de garantir e observar como sua aplicação se comporta. 


#### Tarefa Bônus 4 - Versionamento da API 
Como você versionaria a API da sua aplicação? Que estratégia usar? 

Tecnologias utilizadas para o desenvolvimento desta API. 
Java 8, Springboot, Maven, Swagger, JUnit, Hibernate, MySql, Github, Heroku

#### Tarefa Bônus 1 - Integração com sistemas externos 
Foi criada uma integração com o sistema https://user-info.herokuapp.com/users/{cpf} para validar os CPF's ao cadastrar Associados; 

#### Tarefa Bônus 2 - Mensageria e filas 
Sem desenvolvimento. 

#### Tarefa Bônus 3 - Performance 
Sem atuação. 

#### Tarefa Bônus 4 - Versionamento da API 
Existirem várias formas de versionamento, foi utilizado para o desenvolvimento dessa API, a escrita da versão na URL. 

/api/v1

/api/v2 


#### Tarefa Bônus 5 - Notas de exe - 
Para utilização da API, é importante seguir alguns passos: 

-Gravar uma pauta; 

-Gravar quantos associados desejam participar da votação; 

-Deverá agora, abrir uma sessão, a qual vai durar por default 1 minuto, ou durar o tempo que o administradores deseja. 

Para executar localmente basta criar um database no mysql, apontar as credencias corretas, e acessar pelo postmam ou pelo swagger 


