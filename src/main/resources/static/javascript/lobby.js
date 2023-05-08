// Get username from data attribute in html
// username = document.body.getAttribute('data-username');
hostname = document.body.getAttribute('data-hostname');
port = document.body.getAttribute('data-port');
console.log(hostname);
console.log(port);
const socket = new WebSocket(`ws://${hostname}:${port}/chat`);

// socket.addEventListener('open', () => {
//     // Handle new joiners here
// })

// Receive messages
socket.addEventListener('message', (event) => {
    // Log received message to console so that you know it worked
    console.log('message received!');
    // Parse the json data from the message
    const message = JSON.parse(event.data);
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

    // Check for and display roll if it is valid
    this.roll(message.message)
})

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
        username: 'example_user',
        message: newMessage
    }
    console.log(newMessage)
    // Send payload but make sure to convert it to a string!
    socket.send(JSON.stringify(newMessagePayload))
    // Reset form to allow new input
    newMessageForm.reset()
})

// Function for handling roll functionality
function roll(message) {
    const rollNumber = this.checkForRoll(message);
    if (rollNumber != null) {
        // Ensure that it is within range
        const isInRange = this.checkRollRange(rollNumber)
        if (isInRange) {
            // Display message to users
            this.displayRoll(rollNumber)
        }
    }
}

// Check for regex match in roll
function checkForRoll(message) {
    const input = "/roll 123 is your lucky number";
    const pattern = /\/roll\s*(\d{3})\b/;
    const match = pattern.exec(input);
    if (match) {
        const rollString = match[1]; // "123"
        // Convert it to int
        return parseInt(rollString);
    } else {
        return null;
    }

}

// Ensure that it's within our range
function checkRollRange(rollNumber) {
    const lowerBound = 0
    const upperBound = 100
    return rollNumber >= lowerBound && rollNumber <= upperBound;
}

function displayRoll(rollNumber) {

}