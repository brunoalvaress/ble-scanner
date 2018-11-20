# Bluetooth Low Energy Scanner

Este aplicativo foi desenvolvido como parte do Trabalho de Conclusão do curso de Engenharia de Telecomunicações. O principal objetivo é obter valores RSSI de dispositivos que utilizam a tecnologia Bluetooth Low Energy, e enviar esses valores para um banco de dados no Firebase. Para utilizar os valores salvos no banco para realizar cálculos estatísticos, gerar gráficos e boxplots, pode-se usar o script desenvolvido em Python por mim e publicado neste link https://github.com/brunoalvaress/rssi-calculator.

# Tecnologias Usadas

- Android Studio 3.1
- Biblioteca BLE Android
- Firebase Realtime Database

# Requisitos

- Celular com o sistema operacional Android compatível com a tecnologia BLE
- Dispositivo que utilize a tecnologia Bluetooth Low Energy

# Instalação

1. É necessário realizar o clone do projeto para a pasta desejada, utilizando o comando:  **git clone https://github.com/brunoalvaress/ble-scanner.git**
2. No link https://firebase.google.com é necessário criar uma conta, após isso, efetuar o login e criar um banco de dados utilizando a ferramenta Realtime Database
3. Configure o código do projeto clonado para que reconheça o banco de dados criado anteriormente

# Utilização 

Para obter a potência de transmissão é necessário realizar a medição a 1 metro de distância do dispositivo BLE escolhido, após isso pode-se medir distâncias até 5 metros. O aplicativo vem configurado para realizar todas essas medições ao simples clique do botão desejado.


