require("dotenv-safe").config();

const jwt = require('jsonwebtoken');
const http = require('http');
const express = require('express');
const httpProxy = require('express-http-proxy');
const logger = require('morgan');
const helmet = require('helmet');
const cors = require('cors');

const app = express();

//Microsserviços
const authServiceProxy = httpProxy('http://localhost:8080');
const contaServiceProxy = httpProxy('http://localhost:8081');
const clienteServiceProxy = httpProxy('http://localhost:8082');
const gerenteServiceProxy = httpProxy('http://localhost:8083');

//Sagas

//Gerente
app.get('/gerentes', gerenteServiceProxy);
app.get('/gerentes/:cpf', gerenteServiceProxy);
app.post('/gerentes', gerenteServiceProxy);
app.put('/gerentes/:cpf', gerenteServiceProxy);
app.delete('/gerentes/:cpf', gerenteServiceProxy);

app.use(cors({ origin: 'http://localhost:4200' }));

app.use(logger('dev'));
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

var server = http.createServer(app);

server.listen(3000, () => {
  console.log("API Gateway rodando na porta 3000");
});