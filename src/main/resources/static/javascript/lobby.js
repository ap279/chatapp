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
    const messageViewport = document.getElementById('message-viewport');
    const incomingMessage = document.createElement('div');
    incomingMessage.setAttribute('class', 'incoming-message')

    const usernameBox = document.createElement('div')
    usernameBox.textContent = message.username

    const messageTextBox = document.createElement('div')
    const messageText = document.createElement('p')
    messageText.textContent = message.message
    messageTextBox.appendChild(messageText)

    incomingMessage.appendChild(usernameBox)
    incomingMessage.appendChild(messageTextBox)

    messageViewport.appendChild(incomingMessage)
};
