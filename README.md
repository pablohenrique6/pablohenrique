# API RESTful Pior Filme do Golden Raspberry Awards.

API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

## Endpoint

```url
GET http://localhost:8080/produtor/premio/intervalos
```

retorno
```json
{
  "min": [
    {
      "producer": "Bo Derek",
      "interval": 6,
      "previousWin": 1984,
      "followingWin": 1990
    }
  ],
  "max": [
    {
      "producer": "Bo Derek",
      "interval": 6,
      "previousWin": 1984,
      "followingWin": 1990
    }
  ]
}
```

## Spring Boot

```bash
2.3.9.RELEASE 
```

##Java

```bash
 11
```

## Arquivo CSV

O Arquivo de dados CSV devera estar em classpath:movielist.csv.

## Instalação e execução spring boot

No diretório do projeto execute o seguinte comando

```bash
$ sudo docker build -t spring-boot:1.0
```

Run docker na porta 8080

```bash
$ sudo docker run -d -p 8080:8080 -t spring-boot:1.0
```