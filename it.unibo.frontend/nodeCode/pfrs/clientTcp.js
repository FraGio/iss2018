const net = require('net')

const SEPARATOR = ";"

const client = new Client({ip: readIpFromArguments(), port: readPortNumberFromArguments()})

const msg =  `{ "type": "moveForward",  "arg": 1000 }`
const msg1 = `{ "type": "moveBackward", "arg": 500 }`

setTimeout( function(){ client.send( msg ); } , 10);
setTimeout( function(){ client.send( msg1 ); } , 1500);
//client.send( msg );
//client.send( msg1 );

function Client({ port, ip }) {
    const self = this

    let clientSocket
    const outQueue = []

    connectTo(port, ip)
    
    function connectTo(port, ip) {
        const client = new net.Socket()
        clientSocket = client

        client.connect({ port, ip }, () => console.log(`\tConnecting...`) )

        client.on('connect', () => {
            console.log(`\tConnected`)
            flushOutQueue()
        })

        client.on('data', message => {
            String(message)
                    .split(SEPARATOR)
                    .map( string => string.trim() )
                    .filter( string => string.length !== 0  )
                    .map( JSON.parse )
                    .forEach( message => console.log(message) )
        })
        
        client.on('close', () =>  console.log(`\tConnection closed`) )
        client.on('error', () => console.log(`\tConnection error`) )
    }

    this.send = function(message) {
        if(!clientSocket.connecting)
            clientSocket.write(SEPARATOR +message +SEPARATOR)
        else {
            console.log(`\tSocket not created, message added to queue`)
            outQueue.push(message)
        }
    }

    this.finish = function() {
        if(clientSocket.connecting)
            clientSocket.on('connect', clientSocket.end )
        else
            clientSocket.end()
    }

    function flushOutQueue() {
        while(outQueue.length !== 0) {
            const data = outQueue.shift()
            self.send(data)
        }
    }
}

function readIpFromArguments() {
    const ipAddress = String(process.argv[2])
    if(!ipAddress) {
        console.error("This script expects 2 arguments: server ip address and port number.")
        process.exit()
    }

    return ipAddress
}

function readPortNumberFromArguments() {
    const port = Number(process.argv[3])
    if(!port || port < 0 || port >= 65536) {
        console.error("This script expects a valid port number (>= 0 and < 65536) as argument.")
        process.exit()
    }

    return port
}