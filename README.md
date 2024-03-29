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

### JOINROOM


```java

/* Create join message */ 
String message = new JoinRoomMessage(room_name).toString();

/* Interpret join message */
Message received_message = Message.parse(message);

if(received_message instanceof JoinRoomMessage){
    String room_name = ((JoinRoomMessage) received_message).getRoomName();
}

```


### CREATEROOM


```java

/* Create create message */ 
String message = new CreateRoomMessage(room_name).toString();

/* Interpret create message */
Message received_message = Message.parse(message);

if(received_message instanceof CreateRoomMessage){
    String room_name = ((CreateRoomMessage) received_message).getRoomName();
}

```


### PLAY


```java

/* Create play message */ 
String message = new PlayMessage(name,country,capital,brand,animal,tvShow).toString();

/* Interpret play message */
Message received_message = Message.parse(message);

if(received_message instanceof PlayMessage){
    name = (PlayMessage) received_message).getName();
    country = (PlayMessage) received_message).getCountry();
    capital = ((PlayMessage) received_message).getCapital();
    brand = ((PlayMessage) received_message).getBrand();
    animal = ((PlayMessage) received_message).getAnimal();
    tvShow = ((PlayMessage) received_message).getTvShow();
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

### LISTROOMS

```java

/* Create listrooms response */ 
String response = new ListRoomsResponse(result, rooms).toString();

/* Interpret login message */
Response received_response = Response.parse(response);

if(received_response instanceof ListRoomsResponse){
  String result = ((ListRoomsResponse) received_response).getResult();
  ArrayList<String> rooms = ((ListRoomsResponse) received_response).getRooms();
}

```

### JOINROOM

```java

/* Create join response */ 
String response = new JoinRoomResponse(result).toString();

/* Interpret join message */
Response received_response = Response.parse(response);

if(received_response instanceof JoinRoomResponse){
  String result = ((JoinRoomResponse) received_response).getResult();
}

```

### CREATEROOM

```java

/* Create create response */ 
String response = new CreateRoomResponse(result).toString();

/* Interpret create message */
Response received_response = Response.parse(response);

if(received_response instanceof CreateRoomResponse){
  String result = ((CreateRoomResponse) received_response).getResult();
}

```



