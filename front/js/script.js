'use strict';

let url = "http://localhost:3001/ws-api";
let stompClient = null;
let username = null;

function uuid() {
    return ('10000000-1000-4000-8000-100000000000').replace(/[018]/g, c => (
        c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16)
    );
}

function connectNewUser() {
    username = document.getElementById("username").value
    if (username.length >= 2) {
        document.getElementById("form-username").style.display = "none";
        document.getElementById("form-message").style.display = "block";
        connectWs()
    }
}

function connectWs() {
    let socket = new SockJS(url);
    stompClient = Stomp.over(socket);
    stompClient.connect(
        {},
        connectionSuccess
    );
}

function connectionSuccess() {
    stompClient.subscribe(
        "/topic/messages",
        receivedMessage
    );
    let userInfos = {
        user_id: 0,
        uuid: uuid(),
        userName: username,
        type: "USER",
        status: 1,
    }
    stompClient.send(
        "/app/broadcast.new",
        {},
        JSON.stringify(userInfos)
    )
}

function sendMessage() {
    let messageContent = document.getElementById('chat-message').value.trim();
    let messageInfos = {
        userName: username,
        sender: username,
        receiver: "ALL",
        content: messageContent,
        type : 'CHAT'
    }
    if (messageContent && stompClient) {
        stompClient.send(
            "/app/broadcast.message",
            {}, JSON.stringify(messageInfos)
        );
    }
    document.getElementById('chat-message').value = "";
}

function receivedMessage(data) {
    let msg = JSON.parse(data.body);
    if (msg.type === "USER") {
        filterUser(msg)
    } else if (msg.type === "CHAT") {
        filterMessage(msg)
    }
}

function filterUser(user) {
    const currentDate = new Date();
    const currentHour = currentDate.getHours();
    const currentMinute = currentDate.getMinutes();
    if (user.status) {
        createHtmlElement("span", "user-on", user.userName);
        createHtmlElement("div", "time", `Connected at: ${currentHour}:${currentMinute}`);
    } else {
        createHtmlElement("span", "user-off", user.userName);
        createHtmlElement("div", "time", `Leave at: ${currentHour}:${currentMinute}`);
    }
}

function filterMessage(user) {
    if (user.userName === username) {
        createHtmlElement("div", "message", user.content);
    } else {
        createHtmlElement("div", "users", user.userName);
        createHtmlElement("div", "messages", user.content);
    }
}

function createHtmlElement(type, className, content) {
    let htmlEl= document.createElement(type);
    htmlEl.classList.add(className);
    htmlEl.innerText = content;
    document.getElementById("list-messages").appendChild(htmlEl);
}
