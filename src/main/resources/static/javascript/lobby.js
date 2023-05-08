hostname = document.body.getAttribute('data-hostname')
console.log(hostname)
const socket = new WebSocket(`ws://${hostname}:8080/chat`)

// Send a message
// socket.send('This is a test message!')

// Receive messages
socket.onmessage = function(event) {
    // Log received message to console so that you know it worked
    console.log('message received!')
    // Parse the json data from the message
    const message = JSON.parse(event.data)
    // Display message in window
    const messageViewport = document.getElementById('message-viewport')
    const incomingMessage = document.createElement('p');
    incomingMessage.textContent = message.message
    messageViewport.appendChild(incomingMessage)
};
