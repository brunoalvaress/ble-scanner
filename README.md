# Bluetooth Low Energy Scanner

Este aplicativo foi desenvolvido para a monografia do curso de Engenharia de Telecomunicações. O foco principal é obter valores RSS de 
dispositivos que utilizam a tecnologia Bluetooth Low Energy, e enviar esses valores para um banco de dados no Firebase. 

# Tecnologias Usadas

- Android Studio 3.1
- Firebase Realtime Database

# Instalação

1. É necessário realizar o clone do projeto para a pasta desejada, utilizando o comando: git clone https://github.com/brunoalvaress/ble-scanner.git
2. No link https://firebase.google.com é necessário criar uma conta, após isso, efetuar o login e ciar um banco de dados utilizando a ferramente Realtime Database

# Utilização 

Para obter a potência de transmissão é necessário realizar a medição a 1 metro de distância, após isso pode-se medir distâncias até 5 metros. Para utilizar os valores salvos no banco para realizar cálculos estatísticos, gerar gráficos e boxplots, pode-se usar o script desenvolvido em Python por mim e publicado neste link https://github.com/brunoalvaress/rssi-calculator.
