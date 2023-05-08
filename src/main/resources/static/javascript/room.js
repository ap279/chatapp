// Get username from data attribute in html
username = document.body.getAttribute('data-username');
hostname = document.body.getAttribute('data-hostname');
port = document.body.getAttribute('data-port');
room = document.body.getAttribute('data-room');

console.log(hostname);
console.log(port);
const socket = new WebSocket(`ws://${hostname}:${port}/chat/${room}`);
const displaySocket = new WebSocket(`ws://${hostname}:${port}/display/${room}`)

console.log(room)

// socket.addEventListener('open', () => {
//     // Handle new joiners here
// })

// Receive messages
socket.addEventListener('message', (event) => {
    // Log received message to console so that you know it worked
    console.log('message received!');
    // Parse the json data from the message
    const message = JSON.parse(event.data);
    this.displayMessage(message)
})

function displayMessage(message) {
    // Display message in window
    const messageViewport = document.getElementById('message-viewport');
    const incomingMessage = document.createElement('div');
    incomingMessage.setAttribute('class', 'incoming-message');

    const usernameBox = document.createElement('div');
    usernameBox.textContent = message.username;

    const messageTextBox = document.createElement('div');
    const messageText = document.createElement('p');
    messageText.textContent = message.message;
    messageTextBox.appendChild(messageText);

    incomingMessage.appendChild(usernameBox);
    incomingMessage.appendChild(messageTextBox);

    messageViewport.appendChild(incomingMessage);
}

// Get the form element for sending a new message
const newMessageForm = document.getElementById('new-message-form');
// Add event listener to the form to listen for a submission
newMessageForm.addEventListener('submit', (e) => {
    // Prevent default form submission behavior
    e.preventDefault();
    // Create form data object to handle message submission
    // You could use query selectors to do this but that's a lot of boilerplate
    // Form data objects are kind of like dictionaries so let's use ththis approach instead
    let formData = new FormData(newMessageForm)
    // Extract message from form data object with the get method
    const newMessage = formData.get("message")
    // Send new message to the socket
    // We will make username dynamic later but for now we'll just hard code it into our object
    // Remember that it must be an object because it's being json deserialized in the code above
    const newMessagePayload = {
        username: username,
        message: newMessage
    }
    console.log(newMessage)
    // Send payload but make sure to convert it to a string!
    socket.send(JSON.stringify(newMessagePayload))
    displaySocket.send(JSON.stringify(newMessagePayload))
    // Reset form to allow new input
    newMessageForm.reset()
})

// Handle /roll XXX and /flip result from server in the received message
displaySocket.addEventListener('message', (e) => {
    e.preventDefault();
    const messageData = e.data;
    const jsonData = JSON.parse(messageData);
    changeDisplay(jsonData);
})

// newMessageForm.addEventListener('submit', (e) => {
//     const newMessagePayload = {
//         username: username,
//         message: form
//     }
// })

function changeDisplay(jsonMessage) {
    const displayDiv = document.getElementById('display-div');

    // Display message in message viewport and roll area depending on result
    if (jsonMessage['flip_result'] !== 'null') {
        displayMessage(jsonMessage['flip_result'])
        displayDiv.textContent = jsonMessage['flip_result']
    } else if (jsonMessage['roll_result'] !== 'null') {
        displayMessage(jsonMessage['roll_result'])
        displayDiv.textContent = jsonMessage['roll_result']
    }

    // Output to the console for verification
    console.log(jsonMessage)

}

