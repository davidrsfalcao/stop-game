# Messages

### LOGIN

```java

/* Create login message */ 
String message = new LoginMessage(username, password).toString();

/* Interpret login message */
Message received_message = Message.parse(message);

if(received_message instanceof LoginMessage){
  String username = ((LoginMessage) received_message).getUsername();
  String password = ((LoginMessage) received_message).getPassword();
}

```

### REGISTER

```java

/* Create register message */ 
String message = new RegisterMessage(username, password).toString();

/* Interpret login message */
Message received_message = Message.parse(message);

if(received_message instanceof RegisterMessage){
  String username = ((RegisterMessage) received_message).getUsername();
  String password = ((RegisterMessage) received_message).getPassword();
}

```

### LISTROOMS

```java

/* Create listrooms message */ 
String message = new ListRoomsMessage().toString();

/* Interpret login message */
Message received_message = Message.parse(message);

if(received_message instanceof ListRoomsMessage){
    //
}

```

# Responses

### LOGIN

```java

/* Create login response */ 
String response = new LoginResponse(result, certeficate).toString();

/* Interpret login message */
Response received_response = Response.parse(response);

if(received_response instanceof LoginResponse){
  String result = ((LoginResponse) received_response).getResult();
  String certificate = ((LoginResponse) received_response).getCertificate();
}

```

### REGISTER

```java

/* Create register response */ 
String response = new RegisterResponse(result, certeficate).toString();

/* Interpret login message */
Response received_response = Response.parse(response);

if(received_response instanceof RegisterResponse){
  String result = ((RegisterResponse) received_response).getResult();
  String certificate = ((RegisterResponse) received_response).getCertificate();
}

```

...
